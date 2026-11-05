(ns day3)
(require '[aoc])

(defn char-to-digit [c] (Character/digit c 10))

(defn substring-after [c s] ; inspired by Kotlin :)
  (subs s (inc (.indexOf s (int c)))))

(defn max-num
  ([len s] (max-num len 0 s))
  ([len result s]
   (if (zero? len) result
       (let [max-digit
             (apply max-key char-to-digit (drop-last (dec len) s))]
         (recur (dec len)
                (+ (* result 10) (char-to-digit max-digit))
                (substring-after max-digit s))))))

(defn solve [input num-len]
  (->> input
       (map (partial max-num num-len))
       (apply +)))

(defn part1 [input] (solve input 2))

(defn part2 [input] (solve input 12))
