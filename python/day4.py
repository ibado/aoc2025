from util import Point, DIRS

def find_accessible(lines: list[str]) -> set:
    pmax = Point(len(lines), len(lines[0]))
    accessible_rolls = set()
    for i, line in enumerate(lines):
        for j, char in enumerate(line):
            if char == '@':
                p = Point(i, j)
                box_count = 0
                for d in DIRS:
                    np = p.plus(d)
                    if np.in_range(pmax) and lines[np.x][np.y] == '@':
                        box_count += 1
                if box_count < 4:
                    accessible_rolls.add((i,j))

    return accessible_rolls

def part1(lines: list[str]) -> int:
    return len(find_accessible(lines))

def part2(lines: list[str]) -> int:
    grid = lines
    res = set()
    n = len(lines)
    while True:
        partial = find_accessible(grid)
        res = res.union(partial)
        if not partial:
            break
        grid = [
            "".join(['.' if (i, j) in partial else grid[i][j] for j in range(n)])
            for i in range(n)
        ]
    return len(res)

