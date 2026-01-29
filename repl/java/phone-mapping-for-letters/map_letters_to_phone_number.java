import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class map_letters_to_phone_number {

    public static void main(String[] args) {
        start_phone_converter();
    }

    public static String convert_letters_to_phone_digits(String input_string) {
        // Standard telephone keypad mapping
        Map<Character, Character> lookup_table = new HashMap<>();
        setup_lookup_table(lookup_table, "abc", '2');
        setup_lookup_table(lookup_table, "def", '3');
        setup_lookup_table(lookup_table, "ghi", '4');
        setup_lookup_table(lookup_table, "jkl", '5');
        setup_lookup_table(lookup_table, "mno", '6');
        setup_lookup_table(lookup_table, "pqrs", '7');
        setup_lookup_table(lookup_table, "tuv", '8');
        setup_lookup_table(lookup_table, "wxyz", '9');

        StringBuilder result = new StringBuilder();

        for (char c : input_string.toLowerCase().toCharArray()) {
            if (lookup_table.containsKey(c)) {
                result.append(lookup_table.get(c));
            } else if (Character.isDigit(c)) {
                // Keep existing numbers as they are
                result.append(c);
            }
            // Skip spaces, punctuation, or symbols as per original logic
        }

        return result.toString();
    }

    private static void setup_lookup_table(Map<Character, Character> table, String chars, char digit) {
        for (char c : chars.toCharArray()) {
            table.put(c, digit);
        }
    }

    public static void start_phone_converter() {
        Scanner input_scanner = new Scanner(System.in);
        System.out.println("--- Phone Scheme Converter (Java) ---");
        System.out.println("Type 'exit' to quit.");

        while (true) {
            System.out.print("\nEnter text to convert: ");
            String user_input = input_scanner.nextLine();

            if (user_input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                break;
            }

            String converted_number = convert_letters_to_phone_digits(user_input);
            System.out.println("Phone Number: " + converted_number);
        }
        
        input_scanner.close();
    }
}