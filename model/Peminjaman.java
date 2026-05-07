package model;
// package = tempat mengelompokkan class agar rapi.
// Class ini berada di package bernama "model".

public class Peminjaman {
// public = class bisa dipakai oleh package lain.
// Class ini akan menjadi CLASS INDUK (superclass) untuk request seperti SwapRequest.

    protected String id;
    // protected = bisa diakses:
    // 1) di class ini
    // 2) di package yang sama
    // 3) di class TURUNAN (inheritance)
    // String = tipe data teks.
    // id = menyimpan ID peminjaman / ID request.

    protected Ruangan ruangan;
    // Ruangan = tipe data OBJECT (class buatan sendiri).
    // Menyimpan objek ruangan yang dipinjam.
    // protected → supaya class turunan (SwapRequest) bisa akses.

    protected String hari;
    // String = teks.
    // hari = menyimpan hari peminjaman (Senin, Selasa, dll).

    protected String jam;
    // String = teks.
    // jam = menyimpan waktu peminjaman (contoh: 08:00-10:00).


    // constructor untuk membuat object peminjaman
    public Peminjaman(String id, Ruangan ruangan, String hari, String jam) {
        // Constructor = method yang otomatis dipanggil saat object dibuat.
        // Parameter = data yang wajib diberikan saat membuat object.

        this.id = id;
        // this = menunjuk atribut milik class.
        // Menyimpan nilai id ke atribut class.

        this.ruangan = ruangan;
        // Menyimpan objek ruangan ke atribut ruangan.

        this.hari = hari;
        // Menyimpan hari peminjaman.

        this.jam = jam;
        // Menyimpan jam peminjaman.
    }

    // getter untuk mengambil hari
    public String getHari() { return hari; }
    // Getter = method untuk mengambil nilai hari.
    // public = bisa dipanggil dari luar class.

    public String getJam() { return jam; }
    // Getter untuk mengambil jam peminjaman.

    public Ruangan getRuangan() { return ruangan; }
    // Getter untuk mengambil objek ruangan.

    // method untuk menampilkan informasi peminjaman
    public String getPeminjamanInfo() {
        // Method untuk menampilkan informasi peminjaman dalam bentuk teks.

        return id + " | " + hari + " " + jam + " | " + ruangan.getRoomId();
        // Menggabungkan:
        // ID peminjaman
        // Hari + Jam
        // ID ruangan
        // menjadi satu string.
    }
}