package datamodel;


public class FtoPEvent extends EntityEvent<FileEntity, ProcessEntity> {
    public static final String TYPE = "FtoP";

    public FtoPEvent() {
        super(TYPE);
    }

    public FtoPEvent(String startS, String startNs,
                     FileEntity source, ProcessEntity sink, String event, long size, long eventNo){
        super(TYPE, startS, startNs, source, sink, event, size, eventNo);
    }
}
