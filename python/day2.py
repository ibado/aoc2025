import re

def map_range(rangestr: str) -> range:
    s = rangestr.split("-")
    return range(int(s[0]), int(s[1])+1)

def solve(lines: list[str], regex) -> int:
    ranges = map(map_range, lines[0].split(","))
    count = 0
    for r in ranges:
        valid_ranges = filter(lambda n : re.fullmatch(regex, str(n)), r)
        count += sum(valid_ranges)
    return count

def part1(lines: list[str]) -> int:
    return solve(lines, r'(\d+)\1')

def part2(lines: list[str]) -> int:
    return solve(lines, r'(\d+)\1+')
