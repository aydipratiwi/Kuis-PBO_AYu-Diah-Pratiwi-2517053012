import java.io.Serializable;

public class LayananMedis implements Serializable {

    private static final long serialVersionUID = 1L;

    private String kode;
    private String namaLayanan;
    private int kuota;

    public LayananMedis(String kode, String namaLayanan, int kuota) {
        this.kode = kode;
        this.namaLayanan = namaLayanan;
        this.kuota = kuota;
    }

    public String getKode() {
        return kode;
    }

    public String getNamaLayanan() {
        return namaLayanan;
    }

    public int getKuota() {
        return kuota;
    }
}