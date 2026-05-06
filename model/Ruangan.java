package model;
// package = tempat mengelompokkan class.
// Class Ruangan berada di package "model".

public class Ruangan {
    // public = class bisa digunakan oleh class lain (misalnya Jadwal, Peminjaman, SwapRequest)

    private String roomId;
    // private = hanya bisa diakses di dalam class ini saja (ENCAPSULATION).
    // Tujuan: supaya data ruangan tidak bisa diubah sembarangan dari luar.
    // String = tipe data teks.
    // roomId = menyimpan ID ruangan (contoh: R722).

    private String gedung;
    // String = teks.
    // gedung = menyimpan nama gedung tempat ruangan berada (contoh: GedungA).

    private int kapasitas;
    // int = tipe data angka bulat.
    // kapasitas = menyimpan jumlah maksimum orang yang bisa masuk ruangan.
    // contoh: 58 mahasiswa.

    public Ruangan(String roomId, String gedung, int kapasitas) {
        // Constructor = method yang otomatis dipanggil saat object dibuat.
        // Parameter = data wajib saat membuat object ruangan.

        this.roomId = roomId;
        // this.roomId = atribut milik class
        // roomId (kanan) = parameter dari constructor

        this.gedung = gedung;
        // Menyimpan nama gedung ke atribut class.

        this.kapasitas = kapasitas;
        // Menyimpan kapasitas ruangan ke atribut class.
    }

    public String getRoomId() { return roomId; }
    // Getter = method untuk mengambil data roomId.
    // public = bisa dipanggil dari luar class.
    // Digunakan oleh class lain (Jadwal, Peminjaman, SwapRequest).

    public String getGedung() { return gedung; }
    // Getter untuk mengambil nama gedung.

    public int getKapasitas() { return kapasitas; }
    // Getter untuk mengambil kapasitas ruangan.

    public String getRoomInfo() {
        // Method untuk menampilkan informasi ruangan dalam bentuk teks.

        return roomId + " | " + gedung + " | Kap:" + kapasitas;
        // Menggabungkan data ruangan menjadi satu string.
        // Contoh output: R722 | Gedung7 | Kap:58
    }
}