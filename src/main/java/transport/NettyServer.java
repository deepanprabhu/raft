package transport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class NettyServer {
    Logger logger = LoggerFactory.getLogger(NettyServer.class);
    int port;

    @Getter
    Channel channel;

    @Getter
    ChannelFuture channelFuture;

    EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    public NettyServer(int port){
        this.port = port;
    }

    public void connectLoop() throws Exception{
        CompletableFuture.runAsync( () -> {
            try {
                ServerBootstrap b = new ServerBootstrap(); // (2)
                b.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class) // (3)
                        .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                            @Override
                            public void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline().addLast(new NettyHandler());
                            }
                        })
                        .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                        .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

                // Bind and start to accept incoming connections.
                ChannelFuture f = b.bind(port).sync(); // (7)


                this.channel = f.channel();
                this.channelFuture = f;
                f.channel().closeFuture().sync();
            }
            catch(Exception e) {
                logger.info("Exception in server thread");
            }finally {
                shutdown();
            }
        }).join();
    }
    public void shutdown(){
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
