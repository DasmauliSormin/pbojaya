package model; // class model untuk menyimpan data peminjaman ruangan

public class Peminjaman {

    protected String id;     // id peminjaman
    protected Ruangan ruangan; // object ruangan yang dipinjam
    protected String hari;   // hari peminjaman
    protected String jam;    // jam peminjaman

    // constructor untuk membuat object peminjaman
    public Peminjaman(String id, Ruangan ruangan, String hari, String jam) {
        this.id = id;           // menyimpan id peminjaman
        this.ruangan = ruangan; // menyimpan ruangan yang dipinjam
        this.hari = hari;       // menyimpan hari peminjaman
        this.jam = jam;         // menyimpan jam peminjaman
    }

    // getter untuk mengambil hari
    public String getHari() { return hari; }

    // getter untuk mengambil jam
    public String getJam() { return jam; }

    // getter untuk mengambil ruangan
    public Ruangan getRuangan() { return ruangan; }

    // method untuk menampilkan informasi peminjaman
    public String getPeminjamanInfo() {
        return id + " | " + hari + " " + jam + " | " + ruangan.getRoomId();
    }
}