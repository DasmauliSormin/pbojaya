package model;

public class Admin extends User {

    public Admin(String id, String name, String email) {
        super(id, name, email);
    }

    public void validateRequest(SwapRequest request) {
        request.setStatus("VALID");
    }

    public void approveRequest(SwapRequest request) {
        request.applyReschedule();
        request.setStatus("DISETUJUI");
    }

    public String getAdminInfo() {
        return "ADMIN | " + getUserInfo();
    }
}