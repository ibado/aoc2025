import sys
import util
import day1
import day2
import day3
import day4
import day5
import day6
import day7

def run_day(day):
    day_mod = globals().get(f'day{day}', None)
    if day_mod:
        input = util.input(day)
        print(f"day{day}:")
        print("  part1", day_mod.part1(list(input)))
        print("  part2", day_mod.part2(input))

def main():
    if len(sys.argv) == 3 and sys.argv[1] == 'day' and sys.argv[2].isdigit:
        day = int(sys.argv[2])
        run_day(day)
        return
    for day in range(1, 13):
        run_day(day)

main()
