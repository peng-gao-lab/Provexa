package datamodel;


public class NetworkEntity extends Entity {
    private String srcAddress;
    private String dstAddress;
    private String srcPort;
    private String dstPort;

    public NetworkEntity(long id, String time1, String time2, String srcAddress,
                         String dstAddress, String sPort, String dPort, long uniqID){
        super(id, time1,time2,uniqID);
        this.srcAddress = srcAddress;
        this.dstAddress = dstAddress;
        this.srcPort = sPort;
        this.dstPort =dPort;
    }
    
    public NetworkEntity(String srcAddress,String dstAddress,String sPort, String dPort,long uniqID,String host){
		super(uniqID, host);
		this.srcAddress = srcAddress;
		this.dstAddress = dstAddress;
		this.srcPort = sPort;
		this.dstPort =dPort;
	}

    public NetworkEntity() {}

    public String getSrcAddress(){
        return srcAddress;
    }

    public String getDstAddress(){
        return dstAddress;
    }
    
    public String getSrcPort() {
    	return srcPort;
    }

    public String getDstPort() {
    	return dstPort;
    }
    public String getSrcAndDstIP(){ return srcAddress+":"+srcPort+"->"+dstAddress+":"+dstPort;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NetworkEntity)) return false;

        NetworkEntity that = (NetworkEntity) o;

        if (srcAddress != null ? !srcAddress.equals(that.srcAddress) : that.srcAddress != null) return false;
        if (dstAddress != null ? !dstAddress.equals(that.dstAddress) : that.dstAddress != null) return false;
        if (srcPort != null ? !srcPort.equals(that.srcPort) : that.srcPort != null) return false;
        return dstPort != null ? dstPort.equals(that.dstPort) : that.dstPort == null;
    }

    @Override
    public int hashCode() {
        int result = srcAddress != null ? srcAddress.hashCode() : 0;
        result = 31 * result + (dstAddress != null ? dstAddress.hashCode() : 0);
        result = 31 * result + (srcPort != null ? srcPort.hashCode() : 0);
        result = 31 * result + (dstPort != null ? dstPort.hashCode() : 0);
        return result;
    }
}
