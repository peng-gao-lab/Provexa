package datamodel;


public class ProcessEntity extends Entity {
    // pid's type is weird
    private String pid;
    private String userID;
    private String groupID;
    private String location;
    private String exeName;
    private String exePath;
    private String cmdLine;
    
    private long startNs;	//darpa event starttime is in Subject(Process)
    private String type;	//darpa subject could be a process or a thread
    private String parentUuid;	//add this for fivedirections

    public ProcessEntity(){}

    public ProcessEntity(long id, String pid, String uid, String groupID, String location,
                         String time1, String stime, String exeName, long uniqID, String exePath){
        super(id, time1,stime,uniqID);
        this.pid = pid;
        this.userID = uid;                                //can't get now
        this.groupID = groupID;                        //can't get now
        this.location = location;
        this.exeName = exeName;
        this.exePath = exePath;
    }

    public ProcessEntity(long id, String pid, String uid, String groupID, String location,
                         String time1, String stime, String exeName, long uniqID, String exePath, String cmdLine) {
        super(id, time1,stime,uniqID);
        this.pid = pid;
        this.userID = uid;                                //can't get now
        this.groupID = groupID;                        //can't get now
        this.location = location;
        this.exeName = exeName;
        this.exePath = exePath;
        this.cmdLine = cmdLine;
    }



    public ProcessEntity(String pid, String uid, String groupID, String location, String exeName,
                         long uniqID, String exePath, String host, long startNs, String type, String parentUuid){
		 super(uniqID, host);
		 this.pid = pid;
		 this.userID = uid;
		 this.groupID = groupID;                      
		 this.location = location;
		 this.exeName = exeName;
		 this.exePath = exePath;
		 this.startNs = startNs;
		 this.type = type;
		 this.parentUuid = parentUuid;
	}

    public ProcessEntity(String host, String pid, String userID, long uniqID, String exePath, String exeName){
        super(uniqID, host);
        if(pid.startsWith("=")){
            pid = pid.substring(1);
        }
        this.pid = pid;
        this.userID = userID;
        location = null;
        groupID = null;
        this.exePath = exePath;
        this.exeName = exeName;
    }

    public String getPid(){
        return pid;
    }

    public String getUid(){
        return userID;
    }

    public String getGroupID(){
        return groupID;
    }

    public String getLocation(){
        return location;
    }

    public String getName(){return exeName;}

    public String getCmdLine(){return cmdLine;}

    public void setCmdLine(String cmdLine) {
        this.cmdLine = cmdLine;
    }

    public String getExePath() {
		return exePath;
	}

	public String getPidAndName(){
        return pid+(exeName.equals("\\N")?cmdLine:exeName);
    }

    public long getStartNs() {
		return startNs;
	}

	public void setStartNs(long startNs) {
		this.startNs = startNs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParentUuid() {
		return parentUuid;
	}

	public void setParentUuid(String parentUuid) {
		this.parentUuid = parentUuid;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProcessEntity)) return false;

        ProcessEntity process = (ProcessEntity) o;

        if (pid != null ? !pid.equals(process.pid) : process.pid != null) return false;
        if (userID != null ? !userID.equals(process.userID) : process.userID != null) return false;
        return exeName != null ? exeName.equals(process.exeName) : process.exeName == null;
    }

    @Override
    public int hashCode() {
        int result = pid != null ? pid.hashCode() : 0;
        result = 31 * result + (userID != null ? userID.hashCode() : 0);
        result = 31 * result + (exeName != null ? exeName.hashCode() : 0);
        return result;
    }
}