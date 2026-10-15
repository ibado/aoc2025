package main

import "cmp"

func mod(n, divisor int) int {
	return ((n % divisor) + divisor) % divisor
}

func abs[T int64 | int](a T) T {
	if a >= 0 {
		return a
	}
	return -a
}

func isDigit(n byte) bool {
	return '0' <= n && n <= '9'
}

func isAlpha(n byte) bool {
	return 'a' <= n && n <= 'z' || 'A' <= n && n <= 'Z'
}

func concat(n int, c byte) int {
	if n == 0 {
		return int(c - '0')
	} else {
		return n*10 + int(c-'0')
	}
}

func sumAll[T cmp.Ordered](slice []T) T {
	var sum T
	for _, x := range slice {
		sum += x
	}
	return sum
}

func intPow(base, exp int) int {
	result := 1
	for range exp {
		result *= base
	}
	return result
}
