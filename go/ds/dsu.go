package ds

import "slices"

// Disjoint Set Union
type DSU struct {
	parents []int
	sizes   []int
	sets    int
}

func NewDSU(size int) *DSU {
	dsu := &DSU{
		parents: make([]int, size),
		sizes:   make([]int, size),
		sets:    size,
	}

	for i := range size {
		dsu.parents[i] = i
		dsu.sizes[i] = 1
	}
	return dsu
}

func (dsu *DSU) FindSet(element int) int {
	if dsu.parents[element] == element {
		return element
	}
	return dsu.FindSet(dsu.parents[element])
}

func (dsu *DSU) UnionSets(a, b int) {
	sa := dsu.FindSet(a)
	sb := dsu.FindSet(b)
	if sa != sb {
		dsu.parents[sb] = sa
		dsu.sizes[sa] += dsu.sizes[sb]
		dsu.sizes[sb] = 0
		dsu.sets--
	}
}

func (dsu *DSU) BigestSets(n int) []int {
	return slices.SortedFunc(
		slices.Values(dsu.sizes),
		func(i1, i2 int) int { return i2 - i1 },
	)[:n]
}

func (dsu *DSU) Sets() int {
	return dsu.sets
}
