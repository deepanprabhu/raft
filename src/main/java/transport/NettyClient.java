package transport;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;

public class NettyClient {
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    int port;
    Channel channel;

    @Getter
    ChannelFuture channelFuture;

    public NettyClient(int port){
        this.port = port;
    }

    public void connectLoop() throws Exception {
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new NettyHandler());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect("127.0.0.1", port).sync(); // (5)
            this.channel = f.channel();
            this.channelFuture = f;
            //f.channel().closeFuture().sync();
        } finally {
        }
    }
    public void shutdown(){
        workerGroup.shutdownGracefully();
    }
}
