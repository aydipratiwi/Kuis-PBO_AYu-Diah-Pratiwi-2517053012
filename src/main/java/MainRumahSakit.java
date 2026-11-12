public class MainRumahSakit {
    public static void main(String[] args) {
        SistemRumahSakit rs =
            new SistemRumahSakit("RS Sehat Selalu");

        rs.tambahPasien("ACT-01", "Cici");
        rs.tambahPasien("ACT-02", "Doni");

        rs.tambahLayananMedis("OBJ-A", "Rawat Inap VIP", 10);
        rs.tambahLayananMedis("OBJ-B", "Rawat Jalan Reguler", 2);
        rs.tambahLayananMedis("OBJ-C", "UGD Ekonomis", 50);

        System.out.println("--- MEMULAI PEMBACAAN FILE TRANSAKSI ---");

        try {
            rs.jalankanTransaksiDariFile("input_transaksi.txt");
        } catch (Exception e) {
            System.out.println("[SISTEM FATAL ERROR] File gagal dibaca/diproses: "
                + e.getMessage());
        }

        rs.simpanLaporanAkhir();
    }
}