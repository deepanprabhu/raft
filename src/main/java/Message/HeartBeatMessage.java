package Message;

public class HeartBeatMessage extends Message {
    private int term;

    public HeartBeatMessage(int term){
        this.term = term;
        this.messageType = MessageType.HEARTBEAT;
    }
}
