package mapper;
// mapper = package khusus untuk mengubah data mentah menjadi object model

import model.Jadwal;
import model.Ruangan;
import model.SwapRequest;
// import ketiga class ini karena SwapRequest membutuhkan Jadwal dan Ruangan

public class SwapMapper {
    // SwapMapper = class yang bertugas mengubah data String[] + object yang sudah di-resolve
    // menjadi object SwapRequest, dan sebaliknya
    // SwapRequest = permintaan reschedule / tukar jadwal kuliah

    /**
     * METHOD: fromArray
     * Tujuan : Mengubah array String + object Jadwal & Ruangan → object SwapRequest
     * Dipakai: Saat membaca input CLI seperti:
     *          "reschedule-request#REQ01#SCH01#Monday#10:00#R102#2024/2025#odd#MENUNGGU"
     *
     * Kenapa Jadwal & Ruangan sudah berupa object?
     * Sama seperti JadwalMapper — proses lookup (pencarian) data dilakukan oleh DAO.
     * Mapper hanya merakit object dari data yang sudah siap.
     *
     * Contoh input array:
     * data[0] = "REQ01"      → requestId
     * data[1] = "SCH01"      → scheduleId (sudah jadi object jadwalLama)
     * data[2] = "Monday"     → hariBaru
     * data[3] = "10:00"      → jamBaru
     * data[4] = "R102"       → roomId baru (sudah jadi object ruanganBaru)
     * data[5] = "2024/2025"  → tahunAjaran
     * data[6] = "odd"        → semester
     * data[7] = "MENUNGGU"   → status awal request
     */
    public static SwapRequest fromArray(String[] data, Jadwal jadwalLama, Ruangan ruanganBaru) {
        String requestId   = data[0].trim(); // ID request (REQ01)
        // data[1] dilewati karena scheduleId sudah jadi object jadwalLama
        String hariBaru    = data[2].trim(); // hari pengganti
        String jamBaru     = data[3].trim(); // jam pengganti
        // data[4] dilewati karena roomId sudah jadi object ruanganBaru
        String tahunAjaran = data[5].trim(); // contoh: 2024/2025
        String semester    = data[6].trim(); // contoh: odd / even
        String status      = data[7].trim(); // status awal: MENUNGGU

        return new SwapRequest(requestId, jadwalLama, hariBaru, jamBaru,
                               ruanganBaru, tahunAjaran, semester, status);
        // Membuat object SwapRequest baru dengan semua data yang sudah disiapkan
    }

    /**
     * METHOD: toDetail
     * Tujuan : Menampilkan detail SwapRequest ke CLI sesuai format output
     * Langsung memanggil getDetail() dari class SwapRequest
     *
     * Contoh output:
     * REQ01|SCH01|Monday 08:00 -> Monday 10:00|R101->R102|2024/2025|odd|MENUNGGU
     */
    public static String toDetail(SwapRequest req) {
        return req.getDetail();
        // getDetail() sudah didefinisikan di class SwapRequest
        // method ini yang menghasilkan format output sesuai spesifikasi soal
    }

    /**
     * METHOD: toArray
     * Tujuan : Mengubah object SwapRequest → array String
     * Dipakai: Jika data SwapRequest perlu disimpan
     */
    public static String[] toArray(SwapRequest req) {
        return new String[]{
            req.getRequestId(), // ID request (REQ01)
            req.getStatus()     // status saat ini (MENUNGGU/VALID/DISETUJUI)
        };
    }
}