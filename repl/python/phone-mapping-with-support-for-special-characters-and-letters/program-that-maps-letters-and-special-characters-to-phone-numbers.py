import math

# Reference from your Java source
DEFAULT_DECIMAL_VALUE = 3

def group_string(s, k):
    """Groups characters into chunks of size k separated by underscores."""
    groups = [s[i:i + k] for i in range(0, len(s), k)]
    return "_".join(groups)

def group_integer(value_str, k):
    """Pads and groups the integer part (Internal requirement)."""
    if value_str == "0" or not value_str:
        return "0" * k
    pad = (k - (len(value_str) % k)) % k
    s = ("0" * pad) + value_str
    return group_string(s, k)

def group_fractional(frac_str, k):
    """Pads and groups the fractional part (Internal requirement)."""
    f = frac_str if frac_str else "0"
    pad = (k - (len(f) % k)) % k
    f = f + ("0" * pad)
    return group_string(f, k)

def parse_group_size(group_size):
    """
    Port of your Java parse_group_size logic.
    If the string is empty, it returns DEFAULT_DECIMAL_VALUE.
    """
    if group_size == "" or group_size is None:
        return DEFAULT_DECIMAL_VALUE
    
    try:
        k = int(group_size)
        if k <= 0:
            raise ValueError
        return k
    except ValueError:
        raise ValueError("group_size must be a positive integer")

def number_formatting_function(x, group_size):
    """
    Python port of your Java logic.
    Takes the string and the group_size (which can be "").
    """
    k = parse_group_size(group_size)
    
    s = x.strip()
    is_negative = s.startswith("negative_") or s.startswith("-")
    
    if s.startswith("negative_"): 
        s = s[len("negative_"):]
    elif s.startswith("-") or s.startswith("+"): 
        s = s[1:]

    if "." in s or "_decimal_point_" in s:
        parts = s.replace("_decimal_point_", ".").split(".", 1)
        int_part = parts[0] if parts[0] else "0"
        frac_part = parts[1] if len(parts) > 1 else "0"
        
        result = f"{group_integer(int_part, k)}_decimal_point_{group_fractional(frac_part, k)}"
    else:
        result = group_integer(s if s else "0", k)

    return f"negative_{result}" if is_negative else result

def convert_to_phone_scheme_with_stars(input_string):
    """Maps letters to phone digits and special chars to '*'."""
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
        elif char.isdigit() or char == ".":
            result.append(char)
        else:
            result.append("*")
    return "".join(result)

def start_phone_tool():
    print("--- Phone Tool (Defaulting via Empty String) ---")
    print("Type 'exit' to quit.")

    while True:
        user_input = input("\nEnter text: ")
        
        if user_input.lower() == "exit":
            print("Exiting...")
            break
            
        # 1. Convert text via phone scheme
        phone_digits = convert_to_phone_scheme_with_stars(user_input)
        
        # 2. Pass "" as the required second argument to trigger the default
        final_output = number_formatting_function(phone_digits, "")
        
        print(f"Output: {final_output}")

if __name__ == "__main__":
    start_phone_tool()