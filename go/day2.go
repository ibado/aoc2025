package main

import (
	"iter"
	"strconv"
	"strings"
)

type day2 struct{}

func (d day2) Part1(lines iter.Seq[string]) any {
	test := func(num string) bool {
		numLen := len(num)
		return mod(numLen, 2) == 0 && num[:numLen/2] == num[numLen/2:]
	}
	return solveDay2(lines, test)
}

func (d day2) Part2(lines iter.Seq[string]) any {
	test := func(num string) bool {
		return isSameDigitRepeated(num) || isNEqualChunks(num)
	}
	return solveDay2(lines, test)
}

func solveDay2(lines iter.Seq[string], test func(string) bool) int {
	next, close := iter.Pull(lines)
	defer close()
	line, ok := next()
	assert(ok)
	total := 0
	for r := range parseRanges(line) {
		for n := r.From; n <= r.To; n++ {
			num := strconv.Itoa(n)
			if test(num) {
				total += n
			}
		}

	}
	return total
}

func isSameDigitRepeated(num string) bool {
	if len(num) == 1 { // not repeated
		return false
	}
	var c byte = num[0]
	for _, x := range []byte(num) {
		if x != c {
			return false
		}
	}
	return true
}
func isNEqualChunks(num string) bool {
	numLen := len(num)
OUTER:
	for chunkLen := 2; chunkLen <= 5; chunkLen++ { // max numLen is 10
		if mod(numLen, chunkLen) != 0 {
			continue
		}
		chunkSize := numLen / chunkLen
		curr := num[:chunkSize]
		for i := chunkSize; i <= numLen-chunkSize; i += chunkSize {
			next := num[i : i+chunkSize]
			if curr != next {
				continue OUTER
			}
			curr = next
		}
		return true
	}
	return false
}

func parseRanges(line string) iter.Seq[Range] {
	return func(yield func(Range) bool) {
		for r := range strings.SplitSeq(line, ",") {
			parts := strings.Split(r, "-")
			from := strToInt(parts[0])
			to := strToInt(parts[1])
			if !yield(Range{from, to}) {
				return
			}
		}
	}

}
