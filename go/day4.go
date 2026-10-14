package main

import (
	"iter"
)

type day4 struct{}

func (d day4) Part1(lines iter.Seq[string]) any {
	mx := parseMatrix(lines)
	count := 0
	accessibleRolls(mx, func(p Point) { count++ })

	return count
}

func (d day4) Part2(lines iter.Seq[string]) any {
	mx := parseMatrix(lines)
	count := 0
	foundAnyRoll := true
	for foundAnyRoll {
		foundAnyRoll = false
		var toRemove []Point
		accessibleRolls(mx, func(p Point) {
			count++
			foundAnyRoll = true
			toRemove = append(toRemove, p)
		})
		for _, p := range toRemove {
			mx[p.x][p.y] = '.'
		}
	}

	return count
}

func accessibleRolls(mx [][]byte, callback func(Point)) {
	for i := range mx {
		for j := range mx[i] {
			if mx[i][j] == '@' {
				p := Point{i, j}
				if countRollsAround(p, mx) < 4 {
					callback(p)
				}
			}
		}
	}
}

func countRollsAround(p Point, mx [][]byte) int {
	rolls := 0
	for _, dir := range AllDirs {
		pd := p.Plus(dir)
		if pd.isInRage(len(mx)) && mx[pd.x][pd.y] == '@' {
			rolls++
		}
	}
	return rolls
}
