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

func parseNums(line []byte) []int {
	var s []int
	i := 0
	for i < len(line) {
		isNeg := line[i] == '-'
		if isDigit(line[i]) || isNeg {
			var n int = 0
			if isNeg {
				i++
			}
			for i < len(line) && isDigit(line[i]) {
				n = concat(n, line[i])
				i++
			}
			if isNeg {
				n *= -1
			}
			s = append(s, n)
		} else {
			i++
		}
	}
	return s
}
