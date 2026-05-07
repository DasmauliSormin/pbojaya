package model; 
// package = tempat pengelompokan class agar rapi.
// Class Admin berada di dalam package bernama "model".

public class Admin extends User { 
// public = bisa diakses dari package lain.
// class = blueprint/kerangka untuk membuat objek.
// Admin = nama class.
// extends User = Admin adalah TURUNAN (inheritance) dari class User.
// Artinya Admin otomatis punya atribut & method dari User.

    // constructor Admin
    public Admin(String id, String name, String email) {
        // Constructor = method khusus yang dipanggil saat object dibuat.
        // public = bisa dipanggil dari mana saja.
        // Admin(...) = nama constructor HARUS sama dengan nama class.
        // String = tipe data teks.
        // id, name, email = parameter yang dikirim saat membuat object Admin.

        super(id, name, email);
        // super = memanggil constructor milik class induk (User).
        // Jadi data id, name, email disimpan di class User.
    }

    // method untuk memvalidasi permintaan swap jadwal
    public void validateRequest(SwapRequest request) {
        // public = bisa dipanggil dari class lain.
        // void = method TIDAK mengembalikan nilai.
        // validateRequest = nama method.
        // SwapRequest = tipe data object (class buatan sendiri).
        // request = parameter berupa objek SwapRequest.

        request.setStatus("VALID");
        // memanggil method setStatus() dari objek request.
        // status permintaan diubah menjadi "VALID".
    }

    // method untuk menyetujui permintaan swap jadwal
    public void approveRequest(SwapRequest request) {
        // method untuk MENYETUJUI permintaan tukar jadwal.

        request.applyReschedule();
        // memanggil method applyReschedule() dari SwapRequest.
        // biasanya method ini akan menukar jadwal kuliah.

        request.setStatus("DISETUJUI");
        // setelah ditukar, status diubah menjadi DISETUJUI.
    }

    // method untuk menampilkan informasi admin
    public String getAdminInfo() {
        // public = bisa dipanggil dari mana saja.
        // String = method mengembalikan teks.
        // getAdminInfo() = method untuk menampilkan info admin.

        return "ADMIN | " + getUserInfo();
        // return = mengembalikan nilai.
        // getUserInfo() = method dari class User (class induk).
        // hasilnya digabung dengan tulisan "ADMIN | ".
    }
}