package model; // package untuk menyimpan class model yang merepresentasikan tabel database

public class User {

    protected String id;     // menyimpan ID user
    protected String name;   // menyimpan nama user
    protected String email;  // menyimpan email user

    // constructor untuk membuat object User
    public User(String id, String name, String email) {
        this.id = id;       // mengisi id user
        this.name = name;   // mengisi nama user
        this.email = email; // mengisi email user
    }

    // method untuk mengambil id user
    public String getId() { return id; }

    // method untuk mengambil nama user
    public String getName() { return name; }

    // method untuk mengambil email user
    public String getEmail() { return email; }

    // method untuk menampilkan informasi user dalam bentuk string
    public String getUserInfo() {
        return id + " | " + name + " | " + email;
    }
}