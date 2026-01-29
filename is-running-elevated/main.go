package main

import (
	"fmt"
	"unsafe"
	
	"golang.org/x/sys/windows"
)

func is_process_elevated() bool {
	var token windows.Token
	// Get the handle for the current process token
	err := windows.OpenProcessToken(windows.CurrentProcess(), windows.TOKEN_QUERY, &token)
	if err != nil {
		return false
	}
	defer token.Close()

	var elevation_flag uint32
	var return_length uint32
	// Query the TokenElevation information class
	err = windows.GetTokenInformation(
		token,
		windows.TokenElevation,
		(*byte)(unsafe.Pointer(&elevation_flag)),
		uint32(unsafe.Sizeof(elevation_flag)),
		&return_length,
	)

	if err != nil {
		return false
	}

	return elevation_flag != 0
}

func main() {
	if is_process_elevated() {
		fmt.Println("Running with administrative privileges.")
	} else {
		fmt.Println("Running as a standard user.")
	}
}