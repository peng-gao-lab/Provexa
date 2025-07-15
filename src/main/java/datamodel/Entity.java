package datamodel;

import java.io.Serializable;
import config.MetaConfig;

/**
 * the design of timestap: timestap1 seconds from epoch  timestap2: microseconds... because the joda time accuracy
 * isn't enough and the format of output of sysdig file!
 */

public class Entity implements Serializable {
    private long id;                                     //event number
    private long timestamp1;
    private long timestamp2;
    private long uniqID;
    
    private String host; //darpa hostid;

    public Entity(){}

    public Entity(long id, long uniqID){
        this.id = id;
        this.uniqID =uniqID;
    }

    public Entity(long id, String time1, String time2, long uniqID){
        this.id = id;
        timestamp1 = Long.valueOf(time1);                    //seconds
        timestamp2 = Long.valueOf(time2);
        this.uniqID = uniqID;

    }
    
    public Entity(long uniqID, String host){

        this.uniqID = uniqID;
        this.host=host;
    }


    public long getID(){
        return id;
    }

    public void setId(int i){
        id = i;
    }

    public String getTimeStamp(){
        String s;
        s = timestamp1 + "." + timestamp2;
        return s;

    }

    public void setUniqID(long uniqID) {
		this.uniqID = uniqID;
	}

	public long getUniqID(){
        return uniqID;
    }

    public String getHost() {
        String default_host = MetaConfig.hostName;
        return (host == null || host.equals("")) ? default_host : host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}

