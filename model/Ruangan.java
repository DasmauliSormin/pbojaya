package model; // package model untuk merepresentasikan tabel ruangan di database

public class Ruangan {

    private String roomId;   // menyimpan ID ruangan
    private String gedung;   // menyimpan nama gedung
    private int kapasitas;   // menyimpan kapasitas ruangan

    // constructor untuk membuat object ruangan
    public Ruangan(String roomId, String gedung, int kapasitas) {
        this.roomId = roomId;   // mengisi id ruangan
        this.gedung = gedung;   // mengisi gedung ruangan
        this.kapasitas = kapasitas; // mengisi kapasitas ruangan
    }

    // method untuk mengambil ID ruangan
    public String getRoomId() { return roomId; }

    // method untuk mengambil nama gedung
    public String getGedung() { return gedung; }

    // method untuk mengambil kapasitas ruangan
    public int getKapasitas() { return kapasitas; }

    // method untuk menampilkan informasi ruangan
    public String getRoomInfo() {
        return roomId + " | " + gedung + " | Kap:" + kapasitas;
    }
}