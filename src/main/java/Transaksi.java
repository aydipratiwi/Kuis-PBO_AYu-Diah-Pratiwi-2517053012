import java.io.Serializable;

public class Transaksi implements Serializable {

    private static final long serialVersionUID = 1L;

    private Pasien pasien;
    private LayananMedis layananMedis;
    private int jumlah;

    public Transaksi(Pasien pasien,
                     LayananMedis layananMedis,
                     int jumlah) {

        this.pasien = pasien;
        this.layananMedis = layananMedis;
        this.jumlah = jumlah;
    }

    public Pasien getPasien() {
        return pasien;
    }

    public LayananMedis getLayananMedis() {
        return layananMedis;
    }

    public int getJumlah() {
        return jumlah;
    }
}