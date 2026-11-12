(ns day7 (:require [grid] [aoc]))

(defn part1 [input]
  (let [g (grid/parse input)
        mx (g :mx)
        beams (vec (repeat (count (first mx)) false))
        coords (grid/points g)]
    (->> (reduce (fn [[cache cnt] [_ i :as coord]]
                   (let [val (get-in mx coord)]
                     (cond
                       (= val \S) [(assoc cache i true) cnt]
                       (and (= val \^) (cache i))
                       [(assoc cache
                               i false
                               (inc i) true
                               (dec i) true)
                        (inc cnt)]
                       :else [cache cnt])))
                 [beams 0] coords)
         second)))

(defn part2 [input]
  (let [g (grid/parse input)
        mx (g :mx)
        beams (vec (repeat (count (first mx)) 0))
        coords (grid/points g)]
    (->>
     (reduce (fn [cache [_ i :as coord]]
               (let [val (get-in mx coord)]
                 (cond
                   (= val \S) (assoc cache i 1)
                   (and (= val \^) (cache i))
                   (let [i+1 (inc i) i-1 (dec i)]
                     (assoc cache
                            i 0
                            i+1 (+ (cache i+1) (cache i))
                            i-1 (+ (cache i-1) (cache i))))
                   :else cache)))
             beams coords)
     (apply +))))

