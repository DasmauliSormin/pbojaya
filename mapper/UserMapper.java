

import model.User;

public class UserMapper {

    // Mengubah array String → object User
    // Format input: ["L001", "Dr. Andi", "andi@kampus.ac.id"]
    public static User fromArray(String[] data) {
        String id    = data[0].trim();
        String name  = data[1].trim();
        String email = data[2].trim();
        return new User(id, name, email);
    }

    // Mengubah object User → array String (untuk disimpan ke file/DB)
    public static String[] toArray(User user) {
        return new String[]{
            user.getId(),
            user.getName(),
            user.getEmail()
        };
    }
}