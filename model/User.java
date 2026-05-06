package model; 
// Class ini berada di package model (kelompok class data sistem).

public class User {
    // public = class bisa diakses dari mana saja.

    protected String id;
    // protected = bisa diakses oleh class ini + class turunannya (Admin & Mahasiswa).
    // String = tipe data teks.
    // id = menyimpan ID user (contoh: L001, M001).
    // Kenapa protected? Supaya Admin & Mahasiswa bisa langsung pakai atribut ini.

    protected String name;
    // String = teks.
    // name = menyimpan nama user (contoh: Dr. Andi, Budi).
    // protected karena akan diwarisi oleh class turunan.

    protected String email;
    // String = teks.
    // email = menyimpan email user.
    // protected agar bisa digunakan oleh Admin & Mahasiswa.

    public User(String id, String name, String email) {
        // Constructor = method yang otomatis dipanggil saat object dibuat.
        // Parameter bertipe String karena semua data berupa teks.

        this.id = id;
        // this.id = atribut milik class
        // id = parameter dari constructor
        // Menyimpan ID ke dalam object User.

        this.name = name;
        // Menyimpan nama ke dalam object User.

        this.email = email;
        // Menyimpan email ke dalam object User.
    }

    public String getId() { return id; }
    // Getter = method untuk mengambil nilai id.
    // Digunakan agar data tetap aman (Encapsulation).

    public String getName() { return name; }
    // Getter untuk mengambil nama user.

    public String getEmail() { return email; }
    // Getter untuk mengambil email user.

    public String getUserInfo() {
        // Method untuk menampilkan semua informasi user dalam bentuk teks.
        return id + " | " + name + " | " + email;
    }
}