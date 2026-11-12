import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SistemRumahSakit implements Serializable {

    private static final long serialVersionUID = 1L;

    private String namaRumahSakit;

    private HashMap<String, Pasien> daftarPasien;
    private HashMap<String, LayananMedis> daftarLayanan;

    private ArrayList<Transaksi> daftarTransaksi;

    public SistemRumahSakit(String namaRumahSakit) {

        this.namaRumahSakit = namaRumahSakit;

        daftarPasien = new HashMap<>();
        daftarLayanan = new HashMap<>();

        daftarTransaksi = new ArrayList<>();
    }

    public void tambahPasien(String id, String nama) {

        Pasien pasien = new Pasien(id, nama);

        daftarPasien.put(id, pasien);

        tampilkanJenisAktor(pasien);
    }
    private void tampilkanJenisAktor(Aktor aktor) {

        System.out.println(
            aktor.getJenisAktor() + " ditambahkan: " + aktor.getNama()
        );
    }

    public void tambahLayananMedis(
            String kode, String nama, int kuota) {

        LayananMedis layanan =
            new LayananMedis(kode, nama, kuota);

        daftarLayanan.put(kode, layanan);

        System.out.println("Layanan ditambahkan: " + nama);
    }

    public void jalankanTransaksiDariFile(String namaFile)
            throws Exception {

        StringBuilder isiFile = new StringBuilder();

        try (BufferedReader pembaca =
                new BufferedReader(new FileReader(namaFile))) {

            String baris;

            while ((baris = pembaca.readLine()) != null) {
                isiFile.append(baris).append(" ");
            }
        }

        String[] dataTransaksi =
            isiFile.toString().trim().split(",\\s*(?=ID=)");

        for (String data : dataTransaksi) {

            if (data.trim().isEmpty()) {
                continue;
            }

            try {

                String idPasien = null;
                String kodeLayanan = null;
                String teksJumlah = null;
                String[] bagian = data.split(",");

                for (String kolom : bagian) {

                    String[] pasangan =
                        kolom.trim().split("=", 2);

                    if (pasangan.length != 2) {
                        throw new IllegalArgumentException(
                            "Format data tidak sesuai: " + kolom
                        );
                    }

                    String kunci = pasangan[0].trim();
                    String nilai = pasangan[1].trim();

                    if (kunci.equals("ID")) {
                        idPasien = nilai;
                    } else if (kunci.equals("KD")) {
                        kodeLayanan = nilai;
                    } else if (kunci.equals("JML")) {
                        teksJumlah = nilai;
                    }
                }

                if (idPasien == null ||
                    kodeLayanan == null ||
                    teksJumlah == null) {

                    throw new IllegalArgumentException(
                        "Data transaksi tidak lengkap: " + data
                    );
                }

                int jumlah = Integer.parseInt(teksJumlah);

                if (jumlah <= 0) {
                    throw new IllegalArgumentException(
                        "Jumlah harus lebih dari 0."
                    );
                }

                Pasien pasien = daftarPasien.get(idPasien);

                if (pasien == null) {
                    throw new DataTidakDitemukanException(
                        "Pasien " + idPasien + " tidak terdaftar."
                    );
                }

                LayananMedis layanan =
                    daftarLayanan.get(kodeLayanan);

                if (layanan == null) {
                    throw new DataTidakDitemukanException(
                        "Layanan " + kodeLayanan + " tidak ditemukan."
                    );
                }

                Transaksi transaksi =
                    new Transaksi(pasien, layanan, jumlah);

                daftarTransaksi.add(transaksi);

                System.out.println(
                    "Transaksi berhasil: " +
                    idPasien + " - " +
                    kodeLayanan + " - " +
                    jumlah
                );

            } catch (DataTidakDitemukanException e) {

                System.out.println(
                    "Transaksi dilewati: " + e.getMessage()
                );

            } catch (IllegalArgumentException e) {

                System.out.println(
                    "Data transaksi tidak valid: " + e.getMessage()
                );
            }
        }
    }

    public void simpanLaporanAkhir() {
        try (ObjectOutputStream output =
                new ObjectOutputStream(
                    new FileOutputStream("storage.db"))) {

            output.writeObject(this);

            System.out.println(
                "Data berhasil disimpan ke storage.db"
            );

        } catch (IOException e) {

            System.out.println(
                "Gagal melakukan serialization: " + e.getMessage()
            );
        }

        try (BufferedWriter penulis =
                new BufferedWriter(new FileWriter("laporan.txt"))) {

            penulis.write(
                "LAPORAN TRANSAKSI BESAR - " + namaRumahSakit
            );

            penulis.newLine();

            penulis.write("Transaksi dengan QTY >= 5");
            penulis.newLine();

            for (Transaksi transaksi : daftarTransaksi) {

                if (transaksi.getJumlah() >= 5) {

                    penulis.write(
                        transaksi.getPasien().getId() + ", " +
                        transaksi.getLayananMedis().getKode() + ", " +
                        transaksi.getJumlah()
                    );

                    penulis.newLine();
                }
            }

            System.out.println(
                "Laporan berhasil disimpan ke laporan.txt"
            );

        } catch (IOException e) {

            System.out.println(
                "Gagal menulis laporan: " + e.getMessage()
            );
        }
    }
}