package cluster;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by dpanprbu on 12/25/16.
 */
public class Peer {
    @Getter
    @Setter
    int port;
    public static class Builder{
        private final int port;
        public Builder(int port){
            this.port = port;
        }
        public Peer build(){
            return new Peer(this);
        }
    }
    private Peer(Builder builder){
        this.port = builder.port;
    }
}
