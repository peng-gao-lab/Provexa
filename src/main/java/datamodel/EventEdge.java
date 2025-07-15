package datamodel;

import java.math.BigDecimal;
import config.MetaConfig;


public class EventEdge{
    private static final String CONFIGS_PATH = "cfg/postgres.properties";

    private EntityNode source;
    private EntityNode sink;
    public long id;
    public BigDecimal startTime;
    public BigDecimal endTime;
    private String type;
    private String event;
    private long size;
    private long startNs;
    private long endNs;
    private long eventNo;
    private String host; //darpa host uuid;

    public EventEdge(EntityNode source,EntityNode sink,long edgeid, String optype, long size,
                     BigDecimal startTime, BigDecimal endTime, String host) {
    	this.source = source;
    	this.sink = sink;
    	this.id = edgeid;
    	this.event = optype;
    	this.size = size;
    	this.startTime = startTime;
    	this.endTime = endTime;
    	this.host = host;
    }
    
    public EventEdge(EntityEvent<? extends Entity, ? extends Entity> e){
        source = new EntityNode(e.getSource());
        sink = new EntityNode(e.getSink());
        id = e.getDbID();
        startTime = new BigDecimal(e.getStart());
        endTime = new BigDecimal(e.getEnd());
        type = e.getType();
        event = e.getEvent();
        size = e.getSize();
        
        startNs = e.getStartNs();
        endNs = e.getEndNs();
        eventNo = e.getEventNo();
    }

    public EventEdge(EventEdge edge){
        this.source = edge.getSource();
        this.sink = edge.getSink();
        this.id = edge.getID();
        this.startTime = edge.getStartTime();
        this.endTime = edge.getEndTime();
        this.type = edge.getType();
        this.size = edge.getSize();

        startNs = edge.getStartNs();
        endNs = edge.getEndNs();
        eventNo = edge.getEventNo();
    }

    public EventEdge merge(EventEdge e2){
        this.endTime = e2.endTime;
        this.size += e2.size;
        this.endNs = e2.endNs;
        //if(this.getStartNs()==Long.parseLong("1570398796020") && this.getSource().getID()==241 && this.getSink().getID()==242)
        	//System.out.println(":::::::::::::::::::::::"+this.eventNo);
        return this;
    }

    public void printInfo(){
        System.out.println("id: "+this.id+" Source:"+this.source.getSignature()+" Target:"+this.getSink().getSignature()+" End time:"+
                this.endTime.toString()+" Size:"+ this.size);
    }

    public void setEdgeEvent(String event){
        this.event = event;
    }

    public long getID(){return id;}

//    public void setId(long id){
//        this.id = id;
//    }

    public EntityNode getSource(){return source;}

    public EntityNode getSink(){ return sink;}

    public BigDecimal getStartTime(){
        return startTime;
    }

    public BigDecimal getEndTime(){
        return endTime;
    }
        

    public long getStartNs() {
    	return startNs;
	}

	public void setStartNs(long startNs) {
		this.startNs = startNs;
	}

	public long getEndNs() {
		return endNs;
	}

	public void setEndNs(long endNs) {
		this.endNs = endNs;
	}

	public long getEventNo() {
		return eventNo;
	}

	public void setEventNo(long eventNo) {
		this.eventNo = eventNo;
	}

	public BigDecimal[] getInterval(){
        BigDecimal[] res = {startTime,endTime};
        return res;
    }

    public String getType(){
        return type;
    }

    public String getEvent(){return event;}

    public boolean eventIsNull(){
        return event == null;
    }

    public long getSize(){return size;}

    public BigDecimal getDuration(){
        return endTime.subtract(startTime);
    }

    public String getHost() {
        String default_host = MetaConfig.hostName;
        return (host == null || host.equals("")) ? default_host : host;
    }

	public void setHost(String host) {
		this.host = host;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventEdge)) return false;

        EventEdge eventEdge = (EventEdge) o;

        if (id != eventEdge.id) return false;
        if (!source.equals(eventEdge.source)) return false;
        if (!sink.equals(eventEdge.sink)) return false;
        if (!startTime.equals(eventEdge.startTime)) return false;
        return endTime.equals(eventEdge.endTime);
    }

    @Override
    public int hashCode() {
        int result = source.hashCode();
        result = 31 * result + sink.hashCode();
//        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + startTime.hashCode();
//        result = 31 * result + endTime.hashCode();
        return result;
    }


    @Override
    public String toString() {
        return "EventEdge{" +
                "source=" + source.getSignature() +
                ", sink=" + sink.getSignature() +
                ", id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", type='" + type + '\'' +
                ", event='" + event + '\'' +
                ", size=" + size +
                '}';
    }
}
