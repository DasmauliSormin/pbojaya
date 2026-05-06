package model;
// package = tempat mengelompokkan class agar rapi.
// Class Mahasiswa berada di package "model".

public class Mahasiswa extends User {
// public = class bisa dipakai dari package lain.
// class = blueprint / cetakan object Mahasiswa.
// extends User = INHERITANCE (pewarisan).
// Artinya Mahasiswa adalah turunan dari class User.
// Jadi Mahasiswa otomatis punya atribut & method milik User.

    private String programStudi;
    // private = hanya bisa diakses di dalam class Mahasiswa saja (data aman).
    // String = tipe data teks.
    // programStudi = menyimpan jurusan mahasiswa (contoh: Informatika).

    public Mahasiswa(String id, String name, String email, String programStudi) {
        // Constructor = method yang otomatis dipanggil saat object dibuat.
        // Parameter = data yang wajib diisi saat membuat object Mahasiswa.

        super(id, name, email);
        // super = memanggil constructor milik class induk (User).
        // Jadi id, name, email disimpan di class User.
        // Ini bukti konsep INHERITANCE bekerja.

        this.programStudi = programStudi;
        // this = menunjuk atribut milik class Mahasiswa.
        // Mengisi atribut programStudi dengan nilai dari parameter.
    }

    public String getProgramStudi() { return programStudi; }
    // Getter = method untuk mengambil nilai programStudi.
    // public = bisa diakses dari luar class.
    // String = method mengembalikan teks.

    public String getMahasiswaInfo() {
        // Method untuk menampilkan info lengkap mahasiswa dalam bentuk teks.

        return "MHS | " + getUserInfo() + " | " + programStudi;
        // "MHS" = penanda bahwa ini data mahasiswa.
        // getUserInfo() = method milik class User (warisan).
        // programStudi = menambahkan info jurusan mahasiswa.
    }
}