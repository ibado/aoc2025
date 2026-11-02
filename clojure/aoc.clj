(ns aoc)
(require '[clojure.string :as str])

(defn input-day [day]
  (assert (<= 1 day 12) "only ints from 1 to 12")
  (->>
   (format "../input/%d.txt" day)
   (slurp)
   (str/split-lines)))

(defn count-if [f l]
  (reduce (fn [count next] (if (f next) (inc count) count)) 0 l))

(defn sum-if [f l]
  (reduce (fn [sum next] (if (f next) (+ next sum) sum)) 0 l))

