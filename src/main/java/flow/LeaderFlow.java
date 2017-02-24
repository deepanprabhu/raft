package flow;

import cluster.Server;

public class LeaderFlow {
    Server leader;
    public LeaderFlow(Server leader){
        this.leader = leader;
    }
}
