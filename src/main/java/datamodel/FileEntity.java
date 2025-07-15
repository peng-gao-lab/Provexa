package datamodel;


public class FileEntity extends Entity {
    private String user_ID;
    private  String group_ID;
    private String path;
    private String name;
    
    	
    public FileEntity(long id, String time1, String time2, String user_ID,
                      String group_ID, String path, long uniqID){
        super(id, time1,time2,uniqID);
        this.user_ID = user_ID;
        this.group_ID = group_ID;
        this.path = path;
    }

    public FileEntity(String user_ID, String group_ID, String path, long uniqID, String host){
	    super(uniqID, host);
	    this.user_ID = user_ID;
	    this.group_ID = group_ID;
	    this.path = path;
	}

    public FileEntity(long id, String time1, String time2, String user_ID,
                      String group_ID, String path, long uniqID, String name){
        super(id, time1,time2,uniqID);
        this.user_ID = user_ID;
        this.group_ID = group_ID;
        this.path = path;
        this.name = name;
    }

    public FileEntity(String user_ID, String group_ID, String path, long uniqID, String host, String name){
        super(uniqID, host);
        this.user_ID = user_ID;
        this.group_ID = group_ID;
        this.path = path;
        this.name = name;
    }

    public FileEntity() {}

    public String getUser_ID(){
        return user_ID;
    }

    public String getGroup_ID(){
        return group_ID;
    }

    public String getPath(){return path;}

    public String getName(){return name;}

    

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileEntity)) return false;

        FileEntity that = (FileEntity) o;

        if (user_ID != null ? !user_ID.equals(that.user_ID) : that.user_ID != null) return false;
        if (group_ID != null ? !group_ID.equals(that.group_ID) : that.group_ID != null) return false;
        return path.equals(that.path);
    }

    @Override
    public int hashCode() {
        int result = user_ID != null ? user_ID.hashCode() : 0;
        result = 31 * result + (group_ID != null ? group_ID.hashCode() : 0);
        result = 31 * result + path.hashCode();
        return result;
    }
}
