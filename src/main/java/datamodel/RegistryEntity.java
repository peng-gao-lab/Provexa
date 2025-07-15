package datamodel;

public class RegistryEntity extends Entity {
    private String path;

    public RegistryEntity(long id, String time1, String time2, String path, long uniqID){
        super(id, time1,time2,uniqID);
        this.path = path;
    }

    public RegistryEntity(String path, long uniqID, String host){
        super(uniqID, host);
        this.path = path;
    }

    public String getPath(){return path;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileEntity)) return false;

        RegistryEntity that = (RegistryEntity) o;
        return path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }
}
