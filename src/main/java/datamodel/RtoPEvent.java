package datamodel;

public class RtoPEvent extends EntityEvent<RegistryEntity, ProcessEntity> {
    public static final String TYPE = "RtoP";

    public RtoPEvent() {
        super(TYPE);
    }

    public RtoPEvent(String startS, String startNs,
                     RegistryEntity source, ProcessEntity sink, String event, long size, long eventNo){
        super(TYPE, startS, startNs, source, sink, event, size, eventNo);
    }
}
