(ns day2)
(require '[aoc])
(require '[clojure.string :as str])

(defn parse-ranges [line]
  (->> (str/split line #",")
       (map (fn [r] (str/split r #"-")))
       (map (fn [[from to]] [(parse-long from) (parse-long to)]))))

(defn str-half [s]
  (let [half (/ (count s) 2)]
    [(subs s 0 half) (subs s half)]))

(defn valid-part1? [num]
  (cond
    (not= (mod (count (str num)) 2) 0) false
    (let [[n1 n2] (str-half (str num))] (= n1 n2)) true
    :else false))

(defn valid-part2? [num]
  (re-matches #"(\d+)\1+" (str num)))

(defn solve [input test]
  (->> (first input)
       (parse-ranges)
       (mapcat (fn [[from to]] (range from (inc to))))
       (aoc/sum-if test)))

(defn part1 [input] (solve input valid-part1?))

(defn part2 [input] (solve input valid-part2?))
