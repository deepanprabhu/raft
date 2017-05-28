package flow;

import Message.HeartBeatMessage;
import Message.SerializationUtils;
import cluster.Peer;
import cluster.Server;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LeaderFlow {

    public static void SendHeartBeatRPC(Server leader, List<Peer> peers) {
        for (Peer p : peers) {
            if( leader.getPeerChannelMap().containsKey(p) )
            {
                Channel channel = leader.getPeerChannelMap().get(p);
                channel.writeAndFlush(craftHeartBeatMessage(0));
            }
        }
    }
    private static ByteBuf craftHeartBeatMessage(int term){
        HeartBeatMessage h = new HeartBeatMessage(term);
        ByteBuf message = Unpooled.wrappedBuffer(SerializationUtils.serialize(h));
        return message;
    }
}
