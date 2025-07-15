package datamodel;

import java.io.Serializable;
import java.math.BigDecimal;

public class Event implements Serializable {
    protected String type;
    protected String startS;
    protected String startNs;
    protected String endS;
    protected String endNs;
    protected long eventNo;

    public Event() {}

    public Event(String type, String startS, String startNs, long eventNo){
        this.type = type;
        this.startS = startS;
        this.startNs = startNs;
        this.eventNo = eventNo;
    }

    public String getType(){
        return type;
    }

    public void setStartTime(String str) {
        String[]times = str.split("\\.");
        startS = times[0];
        startNs = times[1];
    }

    public void setEndTime(String str) {
        String[]times = str.split("\\.");
        endS = times[0];
        endNs = times[1];
    }

    public String getInterval() {
        String s = startS + "." + startNs;
        String e = endS + "." + endNs;
        BigDecimal start = new BigDecimal(s);
        BigDecimal end = new BigDecimal(e);
        return end.subtract(start).toString();
    }

    public String getStart() {
        return startS + "."+ startNs;
    }

    public String getEnd() {return endS + "." + endNs;}

    public long getStartNs() {
    	return Long.parseLong(startS)*1000+Long.parseLong(startNs)/1000000;
    }

    public long getEndNs() {
    	return Long.parseLong(endS)*1000+Long.parseLong(endNs)/1000000;
    }

    public long getEventNo() {
		return eventNo;
	}

	public void setEventNo(long eventNo) {
		this.eventNo = eventNo;
	}

    @Override
    public String toString() {
        return "Event{" +
                "type='" + type + '\'' +
                ", startS='" + startS + '\'' +
                ", startNs='" + startNs + '\'' +
                ", endS='" + endS + '\'' +
                ", endNs='" + endNs + '\'' +
                ", eventNo=" + eventNo +
                '}';
    }

    @Override
    public Event clone(){
        return new Event(type,startS,startNs,eventNo);
    }
}
