package model;

public class Mahasiswa extends User {
    private String programStudi;

    public Mahasiswa(String id, String name, String email, String programStudi) {
        super(id, name, email);
        this.programStudi = programStudi;
    }

    public String getProgramStudi() { return programStudi; }

    public String getMahasiswaInfo() {
        return "MHS | " + getUserInfo() + " | " + programStudi;
    }
}