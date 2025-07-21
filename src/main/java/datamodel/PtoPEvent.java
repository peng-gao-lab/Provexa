package datamodel;


public class PtoPEvent extends EntityEvent<ProcessEntity, ProcessEntity> {
    public static final String TYPE = "PtoP";

    public PtoPEvent() {
        super(TYPE);
    }

    public PtoPEvent(String startS, String startMs , ProcessEntity source, ProcessEntity sink,
                     String event, long eventNo){
        super(TYPE, startS, startMs, source, sink, event, 0, eventNo);
    }
}
