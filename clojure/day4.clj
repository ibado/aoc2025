(ns day4
  (:require [aoc] [grid]))

(defn accessible-roll? [mx point]
  (< (->> grid/dirs
          (map #(get-in mx (grid/sum-point % point)))
          (filter #{\@})
          count)
     4))

(defn find-accessible [g]
  (->> (grid/points g)
       (filter #(= (get-in (:mx g) %) \@))
       (filter (partial accessible-roll? (:mx g)))))

(defn remove-rolls [mx points]
  (reduce (fn [mx p] (assoc-in mx p \.)) mx points))

(defn find-part2
  ([g] (find-part2 g 0))
  ([g cnt]
   (let [points (find-accessible g)
         partial (count points)
         total (+ cnt partial)]
     (if (zero? partial) total
         (recur
          (assoc g :mx (remove-rolls (:mx g) points)) total)))))

(defn part1 [input]
  (let [g (grid/parse input)] (count (find-accessible g))))

(defn part2 [input] (find-part2 (grid/parse input)))
