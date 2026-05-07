package src;

import dao.UserDao;
import model.User;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Membuat object DAO untuk mengakses database
        UserDao userDao = new UserDao();

        // Mengambil semua data user dari database
        List<User> users = userDao.getAllUsers();

        // Menampilkan data user ke terminal
        for (User u : users) {

            System.out.println(
                u.getId() + " | " +
                u.getName() + " | " +
                u.getEmail()
            );

        }

    }
}