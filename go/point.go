package main

import (
	"fmt"
	"iter"
)

var Dirs = []Point{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}

type Point struct {
	x, y int
}

func (p Point) Plus(other Point) Point {
	return Point{p.x + other.x, p.y + other.y}
}

func (p Point) Minus(other Point) Point {
	return Point{p.x - other.x, p.y - other.y}
}

func (p Point) String() string {
	return fmt.Sprint(p.x, ",", p.y)
}

func (p Point) isInRage(n int) bool {
	return p.x >= 0 && p.x < n && p.y >= 0 && p.y < n
}

func (p Point) RotateRight() Point {
	return Point{p.y, -p.x}
}

func findPoints[T int | byte](mx [][]T, val T) []Point {
	var points []Point
	for i := range mx {
		for j := 0; j < len(mx[0]); j++ {
			if mx[i][j] == val {
				points = append(points, Point{i, j})
			}
		}
	}
	return points
}

func parseMatrix(lines iter.Seq[string]) [][]byte {
	var mx [][]byte
	for l := range lines {
		mx = append(mx, []byte(l))
	}
	return mx
}

func printMx(mx [][]byte) {
	for i := range mx {
		for j := 0; j < len(mx[0]); j++ {
			fmt.Print(string(mx[i][j]))
		}
		fmt.Println()
	}
}
