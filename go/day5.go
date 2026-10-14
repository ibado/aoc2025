package main

import (
	"iter"
	"slices"
	"strings"
)

type day5 struct{}

func (d day5) Part1(lines iter.Seq[string]) any {
	ranges, ids := parseDay5(lines)
	count := 0
	for _, id := range ids {
		for _, r := range ranges {
			if id >= r.From && id <= r.To {
				count++
				break
			}
		}
	}
	return count
}

func (d day5) Part2(lines iter.Seq[string]) any {
	ranges, _ := parseDay5(lines)
	overlap := true
	for overlap {
		overlap = false
		for i, r1 := range ranges {
			for j := i + 1; j < len(ranges); j++ {
				r2 := ranges[j]
				if nr, ok := r1.MergeIfOverlap(r2); ok {
					f := func(r Range) bool {
						return r == r1 || r == r2
					}
					ranges = slices.DeleteFunc(ranges, f)
					ranges = append(ranges, nr)
					overlap = true
				}
			}

		}
	}
	total := 0
	for _, r := range ranges {
		total += r.To - r.From + 1
	}
	return total
}

func parseDay5(lines iter.Seq[string]) ([]Range, []int) {
	linesSlice := slices.Collect(lines)
	separatorIdx := slices.Index(linesSlice, "")
	var ranges []Range
	var ids []int
	for _, line := range linesSlice[:separatorIdx] {
		parts := strings.Split(line, "-")
		ranges = append(
			ranges,
			Range{
				From: strToInt(parts[0]),
				To:   strToInt(parts[1]),
			},
		)

	}
	for _, line := range linesSlice[separatorIdx+1:] {
		ids = append(ids, strToInt(line))
	}
	return ranges, ids
}
