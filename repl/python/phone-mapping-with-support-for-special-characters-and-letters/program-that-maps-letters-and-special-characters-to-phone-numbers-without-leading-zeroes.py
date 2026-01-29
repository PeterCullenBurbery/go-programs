import math

# Reference from your Java source
DEFAULT_DECIMAL_VALUE = 3

def group_integer(value_str, k):
    """
    Groups the integer part.
    The first group contains the remainder, preventing leading zeros.
    """
    if not value_str or value_str == "0":
        return "0"

    s = value_str
    n = len(s)
    remainder = n % k
    groups = []

    if remainder > 0:
        groups.append(s[:remainder])

    for i in range(remainder, n, k):
        groups.append(s[i:i + k])

    return "_".join(groups)

def parse_group_size(group_size):
    if group_size == "" or group_size is None:
        return DEFAULT_DECIMAL_VALUE
    try:
        k = int(group_size)
        return k if k > 0 else DEFAULT_DECIMAL_VALUE
    except ValueError:
        return DEFAULT_DECIMAL_VALUE

def number_formatting_function(x, group_size):
    """Simplified to treat the mapped string as a single block for grouping."""
    k = parse_group_size(group_size)
    s = x.strip()
    
    # Mathematical logic removed as mapper now converts signs/decimals to '*'
    return group_integer(s if s else "0", k)

def convert_to_phone_scheme_with_stars(input_string):
    """Maps letters to phone digits and ALL special chars (including '.') to '*'."""
    phone_map = {
        'abc': '2', 'def': '3', 'ghi': '4',
        'jkl': '5', 'mno': '6', 'pqrs': '7',
        'tuv': '8', 'wxyz': '9'
    }
    lookup_table = {char: digit for chars, digit in phone_map.items() for char in chars}
    result = []

    for char in input_string.lower():
        if char in lookup_table:
            result.append(lookup_table[char])
        elif char.isdigit():
            # Period check removed so it hits the 'else' block
            result.append(char)
        else:
            result.append("*")
    return "".join(result)

def start_phone_tool():
    print("--- Phone Tool (Python Fixed Version) ---")
    print("Type 'exit' to quit.")

    while True:
        try:
            user_input = input("\nEnter text: ")
        except EOFError:
            break
            
        if user_input.lower() == "exit":
            print("Exiting...")
            break

        # 1. Map to digits and stars
        phone_digits = convert_to_phone_scheme_with_stars(user_input)
        
        # 2. Group according to your k=3 logic
        final_output = number_formatting_function(phone_digits, "")
        print(f"Output: {final_output}")

if __name__ == "__main__":
    start_phone_tool()