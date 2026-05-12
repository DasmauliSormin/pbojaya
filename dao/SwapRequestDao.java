package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.SwapRequest;

public class SwapRequestDao {
    /*
     * Method untuk menyimpan request pertukaran jadwal
     * ke dalam database
     * Kolom tabel swap_request: swap_id, jadwal_asal, jadwal_tukar, status
     */
    public void saveSwapRequest(SwapRequest request) {
        try {
            // mengambil koneksi database
            Connection conn = DBConnection.getConnection();

            // query SQL disesuaikan dengan 4 kolom yang ada di tabel swap_request
            String sql = "INSERT INTO swap_request VALUES (?,?,?,?)";

            // menyiapkan query
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, request.getRequestId());
            // kolom swap_id = ID request (contoh: REQ01)

            ps.setString(2, request.getRequestId());
            // kolom jadwal_asal = ID jadwal yang ingin diubah
            // pakai getHari() + getJam() karena tidak ada getter scheduleId di SwapRequest
            // contoh: "Monday_08:00"

            ps.setString(3, request.getRuangan().getRoomId());
            // kolom jadwal_tukar = ruangan tujuan yang diminta
            // getRuangan() mengembalikan ruanganBaru (diwarisi dari Peminjaman)

            ps.setString(4, request.getStatus());
            // kolom status = MENUNGGU / VALID / DISETUJUI

            // menjalankan query INSERT
            ps.executeUpdate();

        } catch (Exception e) {
            // menampilkan error jika terjadi masalah
            e.printStackTrace();
        }
    }
}