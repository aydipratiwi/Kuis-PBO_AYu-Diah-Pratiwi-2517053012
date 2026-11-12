public class Pasien extends Aktor {

    private static final long serialVersionUID = 1L;

    public Pasien(String id, String nama) {
        super(id, nama);
    }

    @Override
    public String getJenisAktor() {
        return "Pasien";
    }
}