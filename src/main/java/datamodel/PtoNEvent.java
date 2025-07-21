package datamodel;


public class PtoNEvent extends EntityEvent<ProcessEntity, NetworkEntity> {
    public static final String TYPE = "PtoN";

    public PtoNEvent() {
        super(TYPE);
    }

    public PtoNEvent(String startS, String startNs, ProcessEntity source, NetworkEntity sink,
                     String event, long size, long eventNo) {
        super(TYPE, startS, startNs, source, sink, event, size, eventNo);
    }

    public PtoNEvent(NtoPEvent a){
        this(a.getStart().split("\\.")[0],
                a.getStart().split("\\.")[1],
                a.getSink(),
                a.getSource(),
                a.getEvent(),
                a.getSize(),
                a.getEventNo());
    }
}
