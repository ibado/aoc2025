package main

import (
	"aoc2025/ds"
	"iter"
)

type day7 struct{}

var down = Point{1, 0}
var left = Point{0, -1}
var right = Point{0, 1}

func (d day7) Part1(lines iter.Seq[string]) any {
	mx := parseMatrix(lines)
	start := findPoints(mx, 'S')[0]
	mxlen := len(mx)
	var queue ds.Queue[Point]
	count := 0
	queue.Push(start)
	visited := make(map[Point]bool)
	for queue.Len() != 0 {
		next := queue.Pop()
		for next.isInRange(mxlen) && mx[next.x][next.y] != '^' {
			next = next.Plus(down)
		}
		if !next.isInRange(mxlen) {
			continue
		}
		if visited[next] {
			continue
		}
		visited[next] = true
		count++
		queue.Push(next.Plus(left))
		queue.Push(next.Plus(right))
	}
	return count
}

func (d day7) Part2(lines iter.Seq[string]) any {
	mx := parseMatrix(lines)
	start := findPoints(mx, 'S')[0]
	cache := make([][]int, len(mx))
	for i := range mx {
		cache[i] = make([]int, len(mx[0]))
	}
	var solve func(Point) int
	solve = func(p Point) int {
		next := p.Plus(down)
		if !next.isInRange(len(mx)) {
			return 1
		}
		var count int
		if v := cache[p.x][p.y]; v != 0 {
			count = v
		} else if mx[next.x][next.y] == '^' {
			count = solve(next.Plus(left)) + solve(next.Plus(right))
		} else if mx[next.x][next.y] == '.' {
			count = solve(next)
		}
		cache[next.x][next.y] += count
		return count
	}
	return solve(start)
}
