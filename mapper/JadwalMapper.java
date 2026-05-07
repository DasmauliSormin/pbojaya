package mapper;
// mapper = package khusus untuk mengubah data mentah menjadi object model

import model.Jadwal;
import model.Ruangan;
// import kedua class ini karena Jadwal membutuhkan object Ruangan di dalamnya

public class JadwalMapper {
    // JadwalMapper = class yang bertugas mengubah data String[] + object Ruangan
    // menjadi object Jadwal, dan sebaliknya

    /**
     * METHOD: fromArray
     * Tujuan : Mengubah array String + object Ruangan menjadi object Jadwal
     * Dipakai: Saat membaca input CLI seperti:
     *          "schedule-add#SCH01#IF101#A#L001#Monday#08:00#R101"
     *
     * Kenapa Ruangan sudah berupa object?
     * Karena RoomId (R101) harus di-lookup dulu dari daftar ruangan yang ada,
     * proses lookup itu dilakukan oleh DAO, bukan oleh Mapper.
     * Mapper hanya bertugas merakit object, bukan mencari data.
     *
     * Contoh input array:
     * data[0] = "SCH01"   → scheduleId
     * data[1] = "IF101"   → courseId
     * data[2] = "A"       → kelas
     * data[3] = "L001"    → lecturerId
     * data[4] = "Monday"  → hari
     * data[5] = "08:00"   → jam
     * ruangan = object Ruangan R101 (sudah di-resolve oleh DAO)
     */
    public static Jadwal fromArray(String[] data, Ruangan ruangan) {
        String scheduleId = data[0].trim(); // ID jadwal
        String courseId   = data[1].trim(); // ID mata kuliah
        String kelas      = data[2].trim(); // kelas (A/B/C)
        String lecturerId = data[3].trim(); // ID dosen
        String hari       = data[4].trim(); // hari kuliah
        String jam        = data[5].trim(); // jam kuliah

        return new Jadwal(scheduleId, courseId, kelas, lecturerId, hari, jam, ruangan);
        // Membuat object Jadwal baru dengan semua data yang sudah disiapkan
    }

    /**
     * METHOD: toArray
     * Tujuan : Mengubah object Jadwal kembali menjadi array String
     * Urutan array harus konsisten dengan fromArray di atas
     */
    public static String[] toArray(Jadwal jadwal) {
        return new String[]{
            jadwal.getScheduleId(),           // [0] ID jadwal
            jadwal.getCourseId(),             // [1] ID mata kuliah
            jadwal.getKelas(),                // [2] kelas
            jadwal.getLecturerId(),           // [3] ID dosen
            jadwal.getHari(),                 // [4] hari
            jadwal.getJam(),                  // [5] jam
            jadwal.getRuangan().getRoomId()   // [6] hanya ID ruangan yang disimpan
            // getRuangan() → mengambil object Ruangan
            // .getRoomId() → lalu ambil ID-nya saja (String)
        };
    }

    /**
     * METHOD: toDisplay
     * Tujuan : Menampilkan info jadwal ke CLI dalam format yang sudah ditentukan
     * Langsung memanggil getFullInfo() dari class Jadwal
     */
    public static String toDisplay(Jadwal jadwal) {
        return jadwal.getFullInfo();
        // getFullInfo() sudah didefinisikan di class Jadwal
        // format: SCH01|IF101|A|L001|Monday 08:00|R101
    }
}