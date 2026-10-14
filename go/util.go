package main

import (
	"fmt"
	"strconv"
)

var debug = true

func log(a ...any) {
	if debug {
		fmt.Println(a...)
	}
}

func strToInt(s string) int {
	return getOrPanic(strconv.Atoi(s))
}

func assert(cond bool) {
	if !cond {
		panic("assertion fail!")
	}
}

func assertErrNil(err error) {
	if err != nil {
		panic(err)
	}
}

func getOrPanic[T any](t T, err error) T {
	assertErrNil(err)
	return t
}
