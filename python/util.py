
def input(day: int) -> list[str]:
    assert day > 0 and day <= 12
    f = open(f"../input/{day}.txt")
    lines = f.readlines()
    f.close()
    return lines

class Point():
    def __init__(self, x, y) -> None:
        self.x = x
        self.y = y

    def plus(self, other):
        return Point(self.x + other.x, self.y + other.y)

    def in_range(self, max_point):
        return self.x >= 0 and self.x < max_point.x and self.y >= 0 and self.y < max_point.y

DIRS = [
    Point(0, 1), Point(0, -1), Point(1, 0), Point(-1, 0),
    Point(-1, -1), Point(1, -1), Point(-1, 1), Point(1, 1),
]

def range_intersect(r1: range, r2: range) -> bool:
    return max(r1.start, r2.start) < min(r1.stop, r2.stop)

def union_ranges(r1: range, r2: range) -> range | None:
    if range_intersect(r1, r2):
        return range(min(r1.start, r2.start), max(r1.stop, r2.stop))
    return None

