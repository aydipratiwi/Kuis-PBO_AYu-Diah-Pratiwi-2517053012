import java.io.Serializable;

public abstract class Aktor implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String nama;

    public Aktor(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public abstract String getJenisAktor();
}