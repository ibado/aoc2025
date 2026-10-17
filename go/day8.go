package main

import (
	"aoc2025/ds"
	"cmp"
	"iter"
	"math"
	"slices"
	"strings"
)

type day8 struct{}

type Point3 struct {
	x, y, z int
}

type Pair[T any] struct {
	first, second T
}

func (p Point3) Distance(other Point3) float64 {
	dx := other.x - p.x
	dy := other.y - p.y
	dz := other.z - p.z
	return math.Sqrt(float64(dx*dx + dy*dy + dz*dz))
}

func (d day8) Part1(lines iter.Seq[string]) any {
	pairs := pairsIdxByDisntace(parsePoints(lines))
	dsu := ds.NewDSU(1000)
	for _, pair := range pairs[:1000] {
		dsu.UnionSets(pair.first, pair.second)
	}
	top3 := dsu.BigestSets(3)
	return top3[0] * top3[1] * top3[2]
}

func (d day8) Part2(lines iter.Seq[string]) any {
	points := parsePoints(lines)
	pairs := pairsIdxByDisntace(points)
	dsu := ds.NewDSU(len(points))
	for _, pair := range pairs {
		dsu.UnionSets(pair.first, pair.second)
		if dsu.Sets() == 1 {
			return points[pair.first].x * points[pair.second].x
		}
	}
	panic("unreachable")
}

func parsePoints(lines iter.Seq[string]) []Point3 {
	var points []Point3
	for line := range lines {
		parts := strings.Split(line, ",")
		point := Point3{
			strToInt(parts[0]),
			strToInt(parts[1]),
			strToInt(parts[2]),
		}
		points = append(points, point)
	}
	return points
}

func pairsIdxByDisntace(points []Point3) []Pair[int] {
	var pairs []Pair[int]
	for i := range points {
		for j := i + 1; j < len(points); j++ {
			pairs = append(pairs, Pair[int]{i, j})
		}
	}
	slices.SortFunc(pairs, func(p1, p2 Pair[int]) int {
		return cmp.Compare(
			points[p1.first].Distance(points[p1.second]),
			points[p2.first].Distance(points[p2.second]),
		)
	})
	return pairs
}
