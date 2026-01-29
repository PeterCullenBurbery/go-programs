import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class program_that_maps_letters_and_special_characters_to_phone_numbers_without_leading_zeroes {

    private static final int DEFAULT_GROUP_SIZE = 3;

    public static void main(String[] args) {
        start_phone_tool();
    }

    // --- Core Logic Functions ---

    /**
     * Groups a string every k characters with underscores.
     */
    public static String group_string(String s, int k) {
        List<String> groups = new ArrayList<>();
        for (int i = 0; i < s.length(); i += k) {
            groups.add(s.substring(i, Math.min(i + k, s.length())));
        }
        return String.join("_", groups);
    }

    /**
     * Formats the integer-like string without leading zeros.
     * The first group receives the remainder of (length % k).
     */
    public static String group_integer(String value_str, int k) {
        if (value_str == null || value_str.isEmpty() || value_str.equals("0")) {
            return "0";
        }

        String s = value_str;
        int n = s.length();
        int remainder = n % k;
        List<String> groups = new ArrayList<>();

        // If there's a remainder, it becomes the first group (prevents leading zeros)
        if (remainder > 0) {
            groups.add(s.substring(0, remainder));
        }

        // Add the subsequent groups of size k
        for (int i = remainder; i < n; i += k) {
            groups.add(s.substring(i, i + k));
        }

        return String.join("_", groups);
    }

    public static int parse_group_size(String group_size) {
        if (group_size == null || group_size.isEmpty()) {
            return DEFAULT_GROUP_SIZE;
        }
        try {
            int k = Integer.parseInt(group_size);
            return (k > 0) ? k : DEFAULT_GROUP_SIZE;
        } catch (NumberFormatException e) {
            return DEFAULT_GROUP_SIZE;
        }
    }

    /**
     * Final formatting step. Since the mapper converts special chars to '*',
     * we treat the input as a single string to be grouped.
     */
    public static String number_formatting_function(String x, String group_size) {
        int k = parse_group_size(group_size);
        String s = x.trim();
        
        // Mathematical decimal/negative logic removed as mapper now converts those characters to '*'
        return group_integer(s.isEmpty() ? "0" : s, k);
    }

    /**
     * Maps letters to digits and everything non-alphanumeric to '*'.
     */
    public static String convert_to_phone_scheme_with_stars(String input_string) {
        Map<Character, Character> lookup_table = new HashMap<>();
        String[] mappings = {"abc:2", "def:3", "ghi:4", "jkl:5", "mno:6", "pqrs:7", "tuv:8", "wxyz:9"};

        for (String m : mappings) {
            String[] parts = m.split(":");
            for (char c : parts[0].toCharArray()) {
                lookup_table.put(c, parts[1].charAt(0));
            }
        }

        StringBuilder result = new StringBuilder();
        for (char char_item : input_string.toLowerCase().toCharArray()) {
            if (lookup_table.containsKey(char_item)) {
                result.append(lookup_table.get(char_item));
            } else if (Character.isDigit(char_item)) {
                result.append(char_item);
            } else {
                // All other characters (including '.', '-', '+', '#') become '*'
                result.append("*");
            }
        }
        return result.toString();
    }

    // --- REPL Interface ---

    public static void start_phone_tool() {
        try (Scanner input_scanner = new Scanner(System.in)) {
            System.out.println("--- Phone Tool (Cleaned & Fixed) ---");
            System.out.println("Type 'exit' to quit.");

            while (true) {
                System.out.print("\nEnter text: ");
                if (!input_scanner.hasNextLine()) break;
                String user_input = input_scanner.nextLine();

                if (user_input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting...");
                    break;
                }

                // 1. Map chars to phone digits or stars
                String phone_digits = convert_to_phone_scheme_with_stars(user_input);
                
                // 2. Group the resulting string (No leading zeros logic)
                String final_output = number_formatting_function(phone_digits, "");

                System.out.println("Output: " + final_output);
            }
        }
    }
}