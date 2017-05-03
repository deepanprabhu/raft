package state;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by dpanprbu on 1/8/17.
 */
public class ServerSessionState {
    @Getter
    @Setter
    AtomicInteger currentTerm = new AtomicInteger(0);

    public int incrementTerm(){
        return currentTerm.incrementAndGet();
    }
}
