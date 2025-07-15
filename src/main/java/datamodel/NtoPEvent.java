package datamodel;


public class NtoPEvent extends EntityEvent<NetworkEntity, ProcessEntity> {
    public static final String TYPE = "NtoP";

    public NtoPEvent() {
        super(TYPE);
    }

    public NtoPEvent(String startS, String startNs, NetworkEntity source, ProcessEntity sink,
                     String event, long size, long eventNo){
        super(TYPE, startS, startNs, source, sink, event, size, eventNo);
    }

    public NtoPEvent(PtoNEvent a){
        this(a.getStart().split("\\.")[0],
                a.getStart().split("\\.")[1],
                a.getSink(),
                a.getSource(),
                a.getEvent(),
                a.getSize(),
                a.getEventNo());
    }
}
