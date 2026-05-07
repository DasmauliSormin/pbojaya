

import model.Jadwal;
import model.Ruangan;
import model.SwapRequest;

public class SwapMapper {

    /**
     * Mengubah data mentah + object yang sudah di-resolve → object SwapRequest
     *
     * Contoh dari CLI:
     * reschedule-request#REQ01#SCH01#Monday#10:00#R102#2024/2025#odd#MENUNGGU
     *
     * data[] = ["REQ01","SCH01","Monday","10:00","R102","2024/2025","odd","MENUNGGU"]
     * jadwalLama = object Jadwal SCH01 (sudah di-lookup dari DAO)
     * ruanganBaru = object Ruangan R102 (sudah di-lookup dari DAO)
     */
    public static SwapRequest fromArray(String[] data, Jadwal jadwalLama, Ruangan ruanganBaru) {
        String requestId   = data[0].trim(); // REQ01
        // data[1] = scheduleId → sudah jadi object jadwalLama
        String hariBaru    = data[2].trim(); // Monday
        String jamBaru     = data[3].trim(); // 10:00
        // data[4] = roomId → sudah jadi object ruanganBaru
        String tahunAjaran = data[5].trim(); // 2024/2025
        String semester    = data[6].trim(); // odd
        String status      = data[7].trim(); // MENUNGGU

        return new SwapRequest(requestId, jadwalLama, hariBaru, jamBaru,
                               ruanganBaru, tahunAjaran, semester, status);
    }

    /**
     * Mengubah SwapRequest → format String detail untuk output CLI
     *
     * Output: REQ01|SCH01|Monday 08:00 -> Monday 10:00|R101->R102|2024/2025|odd|MENUNGGU
     * Langsung memanggil getDetail() dari class SwapRequest
     */
    public static String toDetail(SwapRequest req) {
        return req.getDetail();
    }

    /**
     * Mengubah SwapRequest → String[] jika perlu disimpan
     */
    public static String[] toArray(SwapRequest req) {
        return new String[]{
            req.getRequestId(),            // REQ01
            req.getStatus()               // MENUNGGU / VALID / DISETUJUI
            // field lain bisa ditambah sesuai kebutuhan penyimpanan
        };
    }
}