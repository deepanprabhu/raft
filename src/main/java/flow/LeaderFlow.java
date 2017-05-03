package flow;

import cluster.Peer;
import cluster.Server;

import java.util.List;

public class LeaderFlow {
    Server leader;
    public LeaderFlow(Server leader){
        this.leader = leader;
    }
    public void SendHeartBeatRPC(List<Peer> peers){
        for(Peer p : peers){
        }
    }
}
