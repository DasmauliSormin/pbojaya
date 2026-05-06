package model; // package model untuk menyimpan class data mahasiswa

public class Mahasiswa extends User { // class Mahasiswa merupakan turunan dari class User

    private String programStudi; // menyimpan program studi mahasiswa

    // constructor untuk membuat object Mahasiswa
    public Mahasiswa(String id, String name, String email, String programStudi) {
        super(id, name, email); // memanggil constructor dari class User
        this.programStudi = programStudi; // menyimpan program studi
    }

    // getter untuk mengambil program studi
    public String getProgramStudi() { 
        return programStudi; 
    }

    // method untuk menampilkan informasi mahasiswa
    public String getMahasiswaInfo() {
        return "MHS | " + getUserInfo() + " | " + programStudi; 
        // memanggil getUserInfo dari class User lalu menambahkan program studi
    }
}