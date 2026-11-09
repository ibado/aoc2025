(ns day5
  (:require [clojure.string :as str] [aoc]))

(defn parse-ranges [input]
  (map (fn [line] (let [[from to] (str/split line #"-")]
                    [(parse-long from)
                     (parse-long to)]))
       input))

(defn parse-input [input]
  (let [[ranges numbers]
        (->> input
             (filter #(not (empty? %)))
             (partition-by #(str/includes? % "-")))]
    [(parse-ranges ranges) (map parse-long numbers)]))

(defn in-range [n [from to]] (<= from n to))

(defn merge-if-overlap? [[x1 y1] [x2 y2]]
  (if (and (<= x1 y2) (<= x2 y1)) [(min x1 x2) (max y1 y2)] nil))

(defn part1 [input]
  (let [[ranges nums] (parse-input input)]
    (aoc/count-if #(some (partial in-range %) ranges) nums)))

(defn part2 [input]
  (let [[ranges _] (parse-input input)]
    (->> (sort ranges)
         (mapv identity)
         (reduce (fn [acc r]
                   (if-let [ri (peek acc)]
                     (if-let [merged (merge-if-overlap? r ri)]
                       (conj (pop acc) merged)
                       (conj acc r))
                     (conj acc r))) [])
         (reduce (fn [total [x y]] (+ total (- y x) 1)) 0))))
