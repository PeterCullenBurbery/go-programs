def start_length_checker():
    print("--- String Length Tool ---")
    print("Type 'exit' to quit the program.")
    
    while True:
        # Get user input
        user_input = input("\nEnter a string: ")
        
        # Check for the exit condition
        if user_input.lower() == "exit":
            print("Exiting program. Goodbye!")
            break
        
        # Calculate and print length
        input_length = len(user_input)
        print(f"Length: {input_length}")

if __name__ == "__main__":
    start_length_checker()