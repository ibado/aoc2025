package main

import (
	"iter"
)

type day3 struct{}

func (d day3) Part1(lines iter.Seq[string]) any {
	return solveDay3(lines, 2)
}

func (d day3) Part2(lines iter.Seq[string]) any {
	return solveDay3(lines, 12)
}
func solveDay3(lines iter.Seq[string], numLen int) int {
	total := 0
	for line := range lines {
		lastIdx := 0
		var num int = 0
		for nth := range numLen {
			var digit byte = 0
			for i := lastIdx; i <= len(line)-numLen+nth; i++ {
				if line[i] > digit {
					digit = line[i]
					lastIdx = i + 1
				}
			}
			num = num*10 + int(digit-48)
		}
		total += num

	}
	return total
}
