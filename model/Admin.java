package model; // package model untuk menyimpan class yang merepresentasikan data dalam sistem

public class Admin extends User { // class Admin merupakan turunan dari class User (inheritance)

    // constructor Admin
    public Admin(String id, String name, String email) {
        super(id, name, email); // memanggil constructor dari class User
    }

    // method untuk memvalidasi permintaan swap jadwal
    public void validateRequest(SwapRequest request) {
        request.setStatus("VALID"); // mengubah status request menjadi VALID
    }

    // method untuk menyetujui permintaan swap jadwal
    public void approveRequest(SwapRequest request) {
        request.applyReschedule(); // menerapkan perubahan jadwal yang diminta
        request.setStatus("DISETUJUI"); // mengubah status request menjadi DISETUJUI
    }

    // method untuk menampilkan informasi admin
    public String getAdminInfo() {
        return "ADMIN | " + getUserInfo(); // memanggil method getUserInfo dari class User
    }
}