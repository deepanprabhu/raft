package state;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by dpanprbu on 1/8/17.
 */
public class ServerSessionState {
    @Getter
    @Setter
    long currentTerm = 0;

    public synchronized void incrementTerm(){
        currentTerm++;
    }
}
