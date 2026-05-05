package model;

public class Ruangan {
    private String roomId;
    private String gedung;
    private int kapasitas;

    public Ruangan(String roomId, String gedung, int kapasitas) {
        this.roomId = roomId;
        this.gedung = gedung;
        this.kapasitas = kapasitas;
    }

    public String getRoomId() { return roomId; }
    public String getGedung() { return gedung; }
    public int getKapasitas() { return kapasitas; }

    public String getRoomInfo() {
        return roomId + " | " + gedung + " | Kap:" + kapasitas;
    }
}