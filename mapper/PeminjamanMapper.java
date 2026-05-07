


import model.Peminjaman;
import model.Ruangan;

/**
 * PeminjamanMapper berperan sebagai BASE mapper.
 * Peminjaman adalah superclass dari SwapRequest.
 * Mapper ini digunakan jika ada kebutuhan menampilkan
 * data peminjaman ruangan secara umum (bukan SwapRequest).
 */
public class PeminjamanMapper {

    /**
     * Mengubah data mentah + object Ruangan → object Peminjaman (base)
     *
     * Format array: [id, ruanganId (sudah jadi object), hari, jam]
     * ruangan sudah di-resolve sebelum masuk mapper ini
     */
    public static Peminjaman fromArray(String[] data, Ruangan ruangan) {
        String id   = data[0].trim();
        String hari = data[2].trim();
        String jam  = data[3].trim();

        return new Peminjaman(id, ruangan, hari, jam);
    }

    /**
     * Menampilkan info peminjaman ke CLI
     * Memanggil getPeminjamanInfo() dari class Peminjaman
     */
    public static String toDisplay(Peminjaman p) {
        return p.getPeminjamanInfo();
    }
}