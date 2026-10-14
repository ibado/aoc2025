package main

import (
	"iter"
)

type day1 struct{}

func (d day1) Part1(lines iter.Seq[string]) any {
	dial := 50
	count := 0
	for line := range lines {
		dir := line[0]
		clicks := strToInt(line[1:])
		switch dir {
		case 'L':
			dial = mod(dial-clicks, 100)
		case 'R':
			dial = mod(dial+clicks, 100)
		default:
			panic("invalid input")
		}
		if dial == 0 {
			count++
		}

	}
	return count
}

func (d day1) Part2(lines iter.Seq[string]) any {
	dial := 50
	count := 0
	for line := range lines {
		dir := line[0]
		clicks := strToInt(line[1:])
		var incr int
		switch dir {
		case 'L':
			incr = -1
		case 'R':
			incr = 1
		default:
			panic("invalid input")
		}
		for range clicks {
			dial = mod(dial+incr, 100)
			if dial == 0 {
				count++
			}
		}
	}
	return count
}
