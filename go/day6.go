package main

import (
	"iter"
	"strings"
)

type day6 struct{}

func (d day6) Part1(lines iter.Seq[string]) any {
	nums, operators := parseDay6(lines)
	results := initSlice(operators)
	for _, row := range nums {
		for i := range row {
			switch operators[i] {
			case '+':
				results[i] += row[i]
			case '*':
				results[i] *= row[i]
			}
		}
	}
	return sumAll(results)
}

func (d day6) Part2(lines iter.Seq[string]) any {
	mx := parseMatrix(lines)
	operators := parseOperators(mx[len(mx)-1])
	numbers := make([]int, len(mx[0]))
	for i, row := range mx[:len(mx)-1] {
		for j := range row {
			char := mx[i][j]
			if !isDigit(char) {
				continue
			}
			numbers[j] *= 10
			numbers[j] += int(char - 48)
		}
	}
	i := 0
	results := initSlice(operators)
	for _, num := range numbers {
		if num == 0 {
			i++
			continue
		}
		switch operators[i] {
		case '*':
			results[i] *= num
		case '+':
			results[i] += num
		}
	}
	return sumAll(results)
}

func initSlice(operators []byte) []int {
	s := make([]int, len(operators))
	for i, op := range operators {
		switch op {
		case '+':
			s[i] = 0
		case '*':
			s[i] = 1
		}
	}
	return s
}

func parseDay6(lines iter.Seq[string]) ([][]int, []byte) {
	var nums [][]int
	var operators []byte
	for line := range lines {
		if strings.Contains(line, "*") {
			operators = parseOperators([]byte(line))
			break
		}
		nums = append(nums, parseNums([]byte(line)))
	}
	return nums, operators
}

func parseOperators(line []byte) []byte {
	var operators []byte
	for _, char := range line {
		if char == '*' || char == '+' {
			operators = append(operators, char)
		}
	}
	return operators
}
