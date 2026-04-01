from typing import Tuple
import util

def parse_input(lines: list[str]) -> Tuple[list[range], list[int]]:
    ranges = []
    ids = []
    while True:
        line = lines.pop(0)
        if line == '\n':
            break
        split = line[:-1].split('-')
        r = range(int(split[0]), int(split[1]))
        if len(r) > 0:
            ranges.append(r)
    for line in lines:
        ids.append(int(line[:-1]))
    return ranges, ids

def part1(lines: list[str]) -> int:
    ranges, ids = parse_input(lines)
    count = 0
    for id in ids:
        for r in ranges:
            if id in r:
                count += 1
                break
    return count

def part2(lines: list[str]) -> int:
    ranges, _ = parse_input(lines)
    ranges = sorted(ranges, key=lambda x: x.stop)
    overlaps: set[range] = set()
    for i, rng in enumerate(ranges):
        r1 = rng
        for r2 in ranges[i+1:]:
            r1 = util.union_ranges(r1, r2) or r1
        if any(util.range_intersect(r1, r2) for r2 in overlaps):
            continue
        overlaps.add(r1)
    count = 0
    for r in overlaps:
        count += r.stop - r.start + 1
    return count

