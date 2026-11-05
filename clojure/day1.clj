(ns day1)
(require '[aoc])

(defn parse-clicks [s] (parse-long (subs s 1)))

(defn part1 [input]
  (->> (reduce (fn [[dial total] line]
                 (let [d
                       (case (first line)
                         \R (mod (+ dial (parse-clicks line)) 100)
                         \L (mod (- dial (parse-clicks line)) 100))]
                   [d (if (zero? d) (inc total) total)]))
               [50 0]
               input)
       second))

(defn- parse-line [line]
  [(case (first line) \R inc \L dec)
   (parse-clicks line)])

(defn part2 [input]
  (->>  input
        (map parse-line)
        (reduce (fn [[dial total] [incr clicks]]
                  (->>
                   (range clicks)
                   (reduce (fn [[d t] _]
                             (let [nd (mod (incr d) 100)]
                               [nd (if (zero? nd) (inc t) t)]))
                           [dial total])))
                [50 0])
        second))

