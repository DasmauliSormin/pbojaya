package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Jadwal;
import model.Ruangan;

public class JadwalDao {

    /*
     * Method untuk mengambil seluruh data jadwal kuliah dari database
     */
    public List<Jadwal> getAllJadwal() {

        // list untuk menyimpan data jadwal
        List<Jadwal> list = new ArrayList<>();

        try {

            // mengambil koneksi database
            Connection conn = DBConnection.getConnection();

            // query untuk mengambil semua jadwal
            String sql = "SELECT * FROM jadwal";

            // menyiapkan query
            PreparedStatement ps = conn.prepareStatement(sql);

            // menjalankan query
            ResultSet rs = ps.executeQuery();

            // membaca hasil query satu per satu
            while (rs.next()) {

                /*
                 * membuat object ruangan terlebih dahulu
                 * karena Jadwal memiliki relasi dengan Ruangan
                 */
                Ruangan r = new Ruangan(
                        rs.getString("room_id"),
                        "",
                        0
                );

                /*
                 * membuat object Jadwal berdasarkan data database
                 */
                Jadwal j = new Jadwal(
                        rs.getString("schedule_id"),
                        rs.getString("course_id"),
                        rs.getString("kelas"),
                        rs.getString("lecturer_id"),
                        rs.getString("hari"),
                        rs.getString("jam"),
                        r
                );

                // menambahkan object jadwal ke dalam list
                list.add(j);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // mengembalikan semua jadwal
        return list;
    }
}