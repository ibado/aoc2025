package main

import (
	"iter"
	"strings"
)

type day9 struct{}

func (d day9) Part1(lines iter.Seq[string]) any {
	points := parsePointsDay9(lines)
	maxRectagleArea := 0
	for i := range points {
		for j := i + 1; j < len(points); j++ {
			p1 := points[i]
			p2 := points[j]
			maxRectagleArea = max(maxRectagleArea, rectangleArea(p1, p2))
		}
	}
	return maxRectagleArea
}

func (d day9) Part2(lines iter.Seq[string]) any {
	return 0
}

func rectangleArea(d1, d2 Point) int {
	return (abs(d2.x-d1.x) + 1) * (abs(d2.y-d1.y) + 1)
}

func parsePointsDay9(lines iter.Seq[string]) []Point {
	var points []Point
	for line := range lines {
		parts := strings.Split(line, ",")
		x := strToInt(parts[0])
		y := strToInt(parts[1])
		points = append(points, Point{x: x, y: y})
	}
	return points
}
