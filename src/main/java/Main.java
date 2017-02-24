import cluster.Peer;
import cluster.Server;
import cluster.ServerState;
import flow.ServerFlow;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception{
        Logger logger = LoggerFactory.getLogger(Main.class);

        if(args.length < 2) {
            logger.error("Minimum 2 ports necessary");
            System.exit(0);
        }

        int port = Integer.parseInt(args[0]);
        Server s;
        if(port == 10000) {
            s = new Server.Builder(port)
                    .withPeers(Arrays.asList(Arrays.copyOfRange(args, 1, args.length)))
                    .withState(ServerState.LEADER)
                    .build();
            for(Peer p : s.getPeers())
                ServerFlow.connectToPeer(p);
        }
        else{
            s = new Server.Builder(port)
                    .withPeers(Arrays.asList(Arrays.copyOfRange(args, 1, args.length)))
                    .withState(ServerState.FOLLOWER)
                    .build();
        }
        ServerFlow.OpenServerPort(s).channel().closeFuture().sync();
    }
}
