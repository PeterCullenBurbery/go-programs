package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

func main() {
	start_length_checker()
}

func start_length_checker() {
	// Using a scanner to handle spaces in input strings
	input_scanner := bufio.NewScanner(os.Stdin)

	fmt.Println("--- String Length Tool (Go) ---")
	fmt.Println("Type 'exit' to quit the program.")

	for {
		fmt.Print("\nEnter a string: ")

		// Scan for next line
		if !input_scanner.Scan() {
			break
		}

		user_input := input_scanner.Text()

		// Check for the exit condition (case-insensitive)
		if strings.ToLower(user_input) == "exit" {
			fmt.Println("Exiting program. Goodbye!")
			break
		}

		// Calculate length (using len() for bytes or utf8.RuneCountInString for characters)
		// Go strings are UTF-8 encoded, len() returns byte length.
		input_length := len(user_input)
		fmt.Printf("Length: %d\n", input_length)
	}
}