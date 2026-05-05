package model;

public class SwapRequest extends Peminjaman {
    private Jadwal jadwalLama;
    private String requestId;
    private String hariBaru;
    private String jamBaru;
    private Ruangan ruanganBaru;
    private String tahunAjaran;
    private String semester;
    private String status;

    public SwapRequest(String requestId, Jadwal jadwalLama,
                       String hariBaru, String jamBaru, Ruangan ruanganBaru,
                       String tahunAjaran, String semester, String status) {
        super(requestId, ruanganBaru, hariBaru, jamBaru);
        this.requestId = requestId;
        this.jadwalLama = jadwalLama;
        this.hariBaru = hariBaru;
        this.jamBaru = jamBaru;
        this.ruanganBaru = ruanganBaru;
        this.tahunAjaran = tahunAjaran;
        this.semester = semester;
        this.status = status;
    }

    public String getRequestId() { return requestId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public void applyReschedule() {
        jadwalLama.setHari(hariBaru);
        jadwalLama.setJam(jamBaru);
        jadwalLama.setRuangan(ruanganBaru);
    }

    public String getDetail() {
        return requestId + "|" +
               jadwalLama.getScheduleId() + "|" +
               jadwalLama.getHari() + " " + jadwalLama.getJam() +
               " -> " + hariBaru + " " + jamBaru + "|" +
               jadwalLama.getRuangan().getRoomId() + "->" +
               ruanganBaru.getRoomId() + "|" +
               tahunAjaran + "|" + semester + "|" + status;
    }
}