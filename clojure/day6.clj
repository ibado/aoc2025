(ns day6
  (:require
   [clojure.string :as str]))

(defn split-line [s]
  (->> (str/split s #"\s+")
       (remove str/blank?)))

(defn parse-input [input]
  [(mapv #(mapv parse-long (split-line %)) (drop-last 1 input))
   (mapv first (split-line (last input)))])

(defn l2int [l]
  (parse-long (str/join l)))

(defn part1 [input]
  (let [[num-rows ops] (parse-input input)
        result (mapv #(if (= % \*) 1 0) ops)]
    (->> (reduce (fn [acc row]
                   (->> (map-indexed
                         (fn [i num]
                           (let [op (ops i) acci (acc i)]
                             (if (= op \*)
                               (* num acci)
                               (+ num acci))))
                         row)
                        (mapv identity)))
                 result
                 num-rows)
         (apply +))))

(defn part2 [input]
  (let [ops (->> (last input)
                 (map identity)
                 (keep #(#{\+ \*} %)))
        nums (->>
              (map #(map identity %) input)
              (apply map list) ; transposed the grid
              (map (fn [s] (filter #(not= % \space) s)))
              (map #(take-while Character/isDigit %))
              (map #(if (empty? %) nil (l2int %))))]
    (->>
     (partition-by number? nums)
     (filter #(not= % '(nil)))
     (map (fn [op nums]
            (if (= op \*)
              (apply * nums)
              (apply + nums))) ops)
     (reduce + 0))))
