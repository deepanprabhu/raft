package cluster;

import lombok.Getter;
import lombok.Setter;
import state.ServerSessionState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    @Getter
    int port;

    @Getter
    @Setter
    List<Peer> peers;

    @Getter
    @Setter
    ServerState serverState;

    @Getter
    @Setter
    ServerSessionState serverSessionState;

    public static class Builder{
        private final int port;
        private ArrayList<Peer> peers;
        private ServerState serverState;
        private AtomicInteger term = new AtomicInteger(0);

        public Builder(int port){
            this.port = port;
        }
        public Builder withPeers(List<String> peers){
            this.peers = new ArrayList<Peer>();
            for(String peer : peers){
                Peer apeer = new Peer.Builder(Integer.parseInt(peer)).build();
                this.peers.add(apeer);
            }
            return this;
        }
        public Builder withState(ServerState serverState){
            this.serverState = serverState;
            return this;
        }
        public Builder withTerm(AtomicInteger term){
            this.term = term;
            return this;
        }
        public Server build(){
            return new Server(this);
        }
    }

    private Server(Builder builder){
        this.port = builder.port;
        this.peers = builder.peers;
        this.serverState = builder.serverState;

        //  Initialize Session State
        this.serverSessionState = new ServerSessionState();
        this.serverSessionState.setCurrentTerm(builder.term);
    }
}
