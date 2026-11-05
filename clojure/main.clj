(require '[aoc])
(def days '[day1 day2 day3])
(apply require days)

(defn solve-day [i ns]
  (let [input (aoc/input-day (inc i))]
    [((ns-resolve ns 'part1) input)
     ((ns-resolve ns 'part2) input)]))

(defn format-day [i [part1 part2]]
  (format
   "day%d\n\tpart1: %s\n\tpart2: %s"
   (inc i) part1 part2))

(doseq [day (->> days
                 (map-indexed solve-day)
                 (map-indexed format-day))]
  (println day))
