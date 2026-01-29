import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class program_that_maps_letters_and_special_characters_to_phone_numbers {

    private static final int DEFAULT_DECIMAL_VALUE = 3;

    public static void main(String[] args) {
        start_phone_tool();
    }

    // --- Core Logic Functions ---

    public static String group_string(String s, int k) {
        List<String> groups = new ArrayList<>();
        for (int i = 0; i < s.length(); i += k) {
            groups.add(s.substring(i, Math.min(i + k, s.length())));
        }
        return String.join("_", groups);
    }

    public static String group_integer(String value_str, int k) {
        if (value_str.equals("0") || value_str.isEmpty()) {
            return "0".repeat(k);
        }
        int pad = (k - (value_str.length() % k)) % k;
        String s = "0".repeat(pad) + value_str;
        return group_string(s, k);
    }

    public static String group_fractional(String frac_str, int k) {
        String f = frac_str.isEmpty() ? "0" : frac_str;
        int pad = (k - (f.length() % k)) % k;
        f = f + "0".repeat(pad);
        return group_string(f, k);
    }

    public static int parse_group_size(String group_size) {
        if (group_size == null || group_size.isEmpty()) {
            return DEFAULT_DECIMAL_VALUE;
        }
        try {
            int k = Integer.parseInt(group_size);
            if (k <= 0) throw new NumberFormatException();
            return k;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("group_size must be a positive integer");
        }
    }

    public static String number_formatting_function(String x, String group_size) {
        int k = parse_group_size(group_size);
        String s = x.trim();
        boolean is_negative = s.startsWith("negative_") || s.startsWith("-");

        if (s.startsWith("negative_")) {
            s = s.substring("negative_".length());
        } else if (s.startsWith("-") || s.startsWith("+")) {
            s = s.substring(1);
        }

        String result;
        if (s.contains(".") || s.contains("_decimal_point_")) {
            String delimiter = s.contains("_decimal_point_") ? "_decimal_point_" : "\\.";
            String[] parts = s.split(delimiter, 2);
            String int_part = parts[0].isEmpty() ? "0" : parts[0];
            String frac_part = (parts.length > 1) ? parts[1] : "0";

            result = group_integer(int_part, k) + "_decimal_point_" + group_fractional(frac_part, k);
        } else {
            result = group_integer(s.isEmpty() ? "0" : s, k);
        }

        return is_negative ? "negative_" + result : result;
    }

    public static String convert_to_phone_scheme_with_stars(String input_string) {
        Map<Character, Character> lookup_table = new HashMap<>();
        String[] mappings = {"abc:2", "def:3", "ghi:4", "jkl:5", "mno:6", "pqrs:7", "tuv:8", "wxyz:9"};
        
        for (String m : mappings) {
            String[] parts = m.split(":");
            for (char c : parts[0].toCharArray()) lookup_table.put(c, parts[1].charAt(0));
        }

        StringBuilder result = new StringBuilder();
        for (char char_item : input_string.toLowerCase().toCharArray()) {
            if (lookup_table.containsKey(char_item)) {
                result.append(lookup_table.get(char_item));
            } else if (Character.isDigit(char_item) || char_item == '.') {
                result.append(char_item);
            } else {
                result.append("*");
            }
        }
        return result.toString();
    }

    // --- REPL Interface ---

    public static void start_phone_tool() {
        try (Scanner input_scanner = new Scanner(System.in)) {
            System.out.println("--- Phone Tool (Java Port) ---");
            System.out.println("Type 'exit' to quit.");

            while (true) {
                System.out.print("\nEnter text: ");
                if (!input_scanner.hasNextLine()) break;
                String user_input = input_scanner.nextLine();

                if (user_input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting...");
                    break;
                }

                String phone_digits = convert_to_phone_scheme_with_stars(user_input);
                String final_output = number_formatting_function(phone_digits, "");

                System.out.println("Output: " + final_output);
            }
        }
    }
}