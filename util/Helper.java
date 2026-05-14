package util;

public class Helper {

    // Method untuk memecah command berdasarkan "#"
    public static String[] parseCommand(String input) {
        return input.split("#");
    }

    // Method untuk mengubah string menjadi integer dengan aman
    public static int toInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    // Method untuk mengecek apakah string kosong
    public static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

}