package datamodel;

public class EntityEvent<S extends Entity, T extends Entity> extends Event {
    protected S source;
    protected T sink;
    protected long size;
    protected String event;
    protected long dbID;

    public EntityEvent(String type) {
        this.type = type;
    }

    public EntityEvent(String type, String startS, String startNs, S source, T sink,
                       String event, long size, long eventNo){
        this.type = type;
        this.startS = startS;
        this.startNs = startNs;
        this.eventNo = eventNo;
        this.source = source;
        this.sink = sink;
        this.size = size;
        this.event = event;
    }

    public S getSource() {
        return source;
    }

    public void setSource(S source) {
        this.source = source;
    }

    public T getSink() {
        return sink;
    }

    public void setSink(T sink) {
        this.sink = sink;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void updateSize(long i) {
        size += i;
    }

    public long getDbID() {
        return dbID;
    }

    public void setDbID(long dbID) {
        this.dbID = dbID;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
