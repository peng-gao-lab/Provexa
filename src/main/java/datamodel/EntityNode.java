package datamodel;


public class EntityNode{
    private long ID;
    private FileEntity f;
    private NetworkEntity n;
    private ProcessEntity p;
    private RegistryEntity r;
    private String signature;

    public EntityNode(long id) {
        ID = id;
        signature = Long.toString(id);
    }

    public EntityNode(Entity e) {
        if (e instanceof FileEntity) {
            f = (FileEntity) e;
            signature = f.getPath();
        } else if (e instanceof ProcessEntity) {
            p = (ProcessEntity) e;
            signature = p.getPidAndName();
        } else if (e instanceof NetworkEntity) {
            n = (NetworkEntity) e;
            signature = n.getSrcAndDstIP();
        } else if(e instanceof RegistryEntity) {
            r = (RegistryEntity) e;
            signature = r.getPath();
        }
        ID = e.getUniqID();
    }

    public EntityNode(EntityNode n) {
        this.f = n.getF();
        this.n = n.getN();
        this.p = n.getP();
        this.r = n.getR();
        this.ID = n.getID();
        this.signature = n.getSignature();
    }

    public long getID(){return ID;}

    public FileEntity getF() {
		return f;
	}

	public void setF(FileEntity f) {
		this.f = f;
		signature = f.getPath();
	}

	public NetworkEntity getN() {
		return n;
	}

	public void setN(NetworkEntity n) {
		this.n = n;
        signature = n.getSrcAndDstIP();
	}

    public RegistryEntity getR() {
        return r;
    }

    public void setR(RegistryEntity r) {
        this.r = r;
        signature = r.getPath();
    }

	public ProcessEntity getP() {
		return p;
	}

	public void setP(ProcessEntity p) {
		this.p = p;
        signature = p.getPidAndName();
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getSignature() {
        return signature;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityNode)) return false;

        EntityNode that = (EntityNode) o;

        if (ID != that.ID) return false;
        if (f != null ? !f.equals(that.f) : that.f != null) return false;
        if (n != null ? !n.equals(that.n) : that.n != null) return false;
        if (p != null ? !p.equals(that.p) : that.p != null) return false;
        return signature.equals(that.signature);
    }

    @Override
    public int hashCode() {
        int result = (int) (ID ^ (ID >>> 32));
        result = 31 * result + (f != null ? f.hashCode() : 0);
        result = 31 * result + (n != null ? n.hashCode() : 0);
        result = 31 * result + (p != null ? p.hashCode() : 0);
        result = 31 * result + signature.hashCode();
        return result;
    }
    @Override
    public String toString(){
        return this.getID()+" "+this.getSignature();
    }
}
