package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import model.SwapRequest;

public class SwapRequestDao {

    /*
     * Method untuk menyimpan request pertukaran jadwal
     * ke dalam database
     */
    public void saveSwapRequest(SwapRequest request) {

        try {

            // mengambil koneksi database
            Connection conn = DBConnection.getConnection();

            /*
             * query SQL untuk memasukkan data ke tabel swap_request
             */
            String sql = "INSERT INTO swap_request VALUES (?,?,?,?,?,?,?)";

            // menyiapkan query
            PreparedStatement ps = conn.prepareStatement(sql);

            /*
             * mengisi parameter query sesuai dengan data
             * yang ada di object SwapRequest
             */
            ps.setString(1, request.getRequestId());
            ps.setString(2, request.getHari());
            ps.setString(3, request.getJam());
            ps.setString(4, request.getRuangan().getRoomId());
            ps.setString(5, "2024");
            ps.setString(6, "GANJIL");
            ps.setString(7, request.getStatus());

            // menjalankan query INSERT
            ps.executeUpdate();

        } catch (Exception e) {

            // menampilkan error jika terjadi masalah
            e.printStackTrace();
        }
    }
}