package flow;

import cluster.Peer;
import cluster.Server;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import transport.NettyClient;
import transport.NettyServer;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;


public class ServerFlow {

    static Logger staticLogger = LoggerFactory.getLogger(ServerFlow.class);
    Logger logger = LoggerFactory.getLogger(ServerFlow.class);

    public static ChannelFuture OpenServerPort(Server s) {
        try {
            NettyServer nettyServer = new NettyServer(s.getPort());
            nettyServer.connectLoop();
            s.getChannelMap().put(s, nettyServer.getChannel());
            staticLogger.info("Initialized Server port");

            return nettyServer.getChannelFuture();
        } catch (Exception e) {

        }
        return null;
    }

    public static void connectToPeer(Server s, Peer peer) {
        int retries = 5;
        try {
            do {
                CompletableFuture<Boolean> retry = CompletableFuture.supplyAsync(() -> {
                    boolean retryRequest = true;
                    try {
                        NettyClient client = new NettyClient(peer.getPort());
                        client.connectLoop();
                        ChannelFuture channelFuture = client.getChannelFuture();
                        Thread.sleep(5000);
                        if (channelFuture.isSuccess()) {
                            retryRequest = false;
                            s.getPeerChannelMap().put(peer, channelFuture.channel());
                            staticLogger.info("Connected to peer " + peer.getPort());
                            // channelFuture.sync();
                        } else {
                            staticLogger.info("Unable to connect to peer " + peer.getPort());
                        }
                    } catch (Exception e) {
                    }
                    return retryRequest;
                });
                if (retry.get() == false)
                    if (retry.isDone())
                        break;
                staticLogger.info("Retry effort " + retries + " for peer " + peer.getPort());
            }
            while (retries-- > 0);
        } catch (Exception e) {

        }
    }
}
