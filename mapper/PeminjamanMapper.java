package mapper;
// mapper = package khusus untuk mengubah data mentah menjadi object model

import model.Peminjaman;
import model.Ruangan;
// import kedua class ini karena Peminjaman membutuhkan object Ruangan

/**
 * PeminjamanMapper = mapper untuk class Peminjaman (class INDUK dari SwapRequest)
 *
 * PERAN dalam sistem ini:
 * Peminjaman adalah superclass / class induk.
 * Yang benar-benar dipakai dalam sistem adalah SwapRequest (turunannya).
 * PeminjamanMapper berperan sebagai BASE mapper — digunakan jika ada kebutuhan
 * menampilkan data peminjaman ruangan secara umum, bukan sebagai SwapRequest.
 */
public class PeminjamanMapper {

    /**
     * METHOD: fromArray
     * Tujuan : Mengubah array String + object Ruangan → object Peminjaman (base)
     *
     * Contoh input array:
     * data[0] = "PEM01"    → id peminjaman
     * data[1] = roomId     → sudah jadi object ruangan (di-resolve oleh DAO)
     * data[2] = "Monday"   → hari peminjaman
     * data[3] = "08:00"    → jam peminjaman
     */
    public static Peminjaman fromArray(String[] data, Ruangan ruangan) {
        String id   = data[0].trim(); // ID peminjaman
        // data[1] dilewati karena roomId sudah jadi object ruangan
        String hari = data[2].trim(); // hari peminjaman
        String jam  = data[3].trim(); // jam peminjaman

        return new Peminjaman(id, ruangan, hari, jam);
        // Membuat object Peminjaman baru (class induk)
        // Biasanya tidak langsung dipakai, karena sistem memakai SwapRequest
    }

    /**
     * METHOD: toDisplay
     * Tujuan : Menampilkan info peminjaman ke CLI
     * Langsung memanggil getPeminjamanInfo() dari class Peminjaman
     *
     * Contoh output: PEM01 | Monday 08:00 | R101
     */
    public static String toDisplay(Peminjaman p) {
        return p.getPeminjamanInfo();
        // getPeminjamanInfo() sudah didefinisikan di class Peminjaman
    }
}