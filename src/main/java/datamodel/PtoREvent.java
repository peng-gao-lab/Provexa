package datamodel;

public class PtoREvent extends EntityEvent<ProcessEntity, RegistryEntity> {
    public static final String TYPE = "PtoR";
    public PtoREvent() {
        super(TYPE);
    }

    public PtoREvent(String startS, String startNs, ProcessEntity source, RegistryEntity sink,
                     String event, long size, long eventNo){
        super(TYPE, startS, startNs, source, sink, event, size, eventNo);
    }
}
