package datamodel;


public class PtoFEvent extends EntityEvent<ProcessEntity, FileEntity> {
    public static final String TYPE = "PtoF";

    public PtoFEvent() {
        super(TYPE);
    }

    public PtoFEvent(String startS, String startNs, ProcessEntity source, FileEntity sink,
                     String event, long size, long eventNo){
        super(TYPE, startS, startNs, source, sink, event, size, eventNo);
    }
}
