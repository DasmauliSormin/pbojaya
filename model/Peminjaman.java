package model;

public class Peminjaman {
    protected String id;
    protected Ruangan ruangan;
    protected String hari;
    protected String jam;

    public Peminjaman(String id, Ruangan ruangan, String hari, String jam) {
        this.id = id;
        this.ruangan = ruangan;
        this.hari = hari;
        this.jam = jam;
    }

    public String getHari() { return hari; }
    public String getJam() { return jam; }
    public Ruangan getRuangan() { return ruangan; }

    public String getPeminjamanInfo() {
        return id + " | " + hari + " " + jam + " | " + ruangan.getRoomId();
    }
}