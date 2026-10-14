package main

type Range struct {
	From, To int
}

func (r1 Range) MergeIfOverlap(r2 Range) (Range, bool) {
	if r1.From <= r2.To && r2.From <= r1.To {
		return Range{
			From: min(r1.From, r2.From),
			To:   max(r1.To, r2.To),
		}, true
	}
	return Range{}, false
}
