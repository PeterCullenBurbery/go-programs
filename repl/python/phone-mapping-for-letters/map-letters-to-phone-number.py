def convert_letters_to_phone_digits(input_string):
    # Mapping based on the international standard telephone keypad
    phone_map = {
        'abc': '2', 'def': '3', 'ghi': '4',
        'jkl': '5', 'mno': '6', 'pqrs': '7',
        'tuv': '8', 'wxyz': '9'
    }
    
    # Create a fast lookup dictionary (char -> digit)
    lookup_table = {char: digit for chars, digit in phone_map.items() for char in chars}
    
    result = []
    
    for char in input_string.lower():
        if char in lookup_table:
            result.append(lookup_table[char])
        elif char.isdigit():
            # Keep existing numbers as they are
            result.append(char)
        else:
            # Skip spaces, punctuation, or symbols
            continue
            
    return "".join(result)

def start_phone_converter():
    print("--- Phone Scheme Converter ---")
    print("Type 'exit' to quit.")
    
    while True:
        user_input = input("\nEnter text to convert: ")
        
        if user_input.lower() == "exit":
            print("Exiting...")
            break
            
        converted_number = convert_letters_to_phone_digits(user_input)
        print(f"Phone Number: {converted_number}")

if __name__ == "__main__":
    start_phone_converter()