package main

import (
	"bufio"
	"fmt"
	"io"
	"iter"
	"net/http"
	"os"
)

var session string

func fetchDay(day uint8, path string) {
	url := fmt.Sprintf("https://adventofcode.com/2025/day/%d/input", day)
	req := getOrPanic(http.NewRequest("GET", url, nil))

	if session == "" {
		scanner := bufio.NewScanner(os.Stdin)
		fmt.Print("Session: ")
		if !scanner.Scan() {
			fmt.Fprintln(os.Stderr, "error reading from stdin")
			os.Exit(1)
		}
		session = scanner.Text()
	}

	req.AddCookie(&http.Cookie{
		Name:  "session",
		Value: session,
	})

	res := getOrPanic(http.DefaultClient.Do(req))
	defer res.Body.Close()

	input := getOrPanic(io.ReadAll(res.Body))
	assertErrNil(os.WriteFile(path, input, 0644))
}

func readLines(day uint8) iter.Seq[string] {
	assert(day <= 25)
	return func(yield func(string) bool) {
		fpath := fmt.Sprintf("../input/%d.txt", day)
		f, err := os.OpenFile(fpath, os.O_RDONLY, 0)
		if err != nil {
			if os.IsNotExist(err) {
				fetchDay(day, fpath)
				f, err = os.OpenFile(fpath, os.O_RDONLY, 0)
			}
			assertErrNil(err)
		}
		defer f.Close()

		scanner := bufio.NewScanner(f)

		for scanner.Scan() {
			if !yield(scanner.Text()) {
				return
			}
		}
	}
}
