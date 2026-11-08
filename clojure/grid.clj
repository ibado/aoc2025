(ns grid)

(def dirs
  [[0 1] [1 0] [0 -1] [-1 0] [1 1] [-1 -1] [1 -1] [-1 1]])

(defn sum-point [[x1 y1] [x2 y2]] [(+ x1 x2) (+ y1 y2)])

(defn in-range
  ([[x1 y1] n] (in-range [x1 y1] n n))
  ([[x1 y1] xmax ymax] (and (<= 0 x1 xmax) (<= 0 y1 ymax))))

(defn parse [input]
  (let [mx (mapv (fn [line] (mapv identity line)) input)]
    {:mx mx
     :rows (count mx)
     :cols (count (first mx))}))

(defn points [{:keys [rows cols]}]
  (for [i (range rows) j (range cols)] [i j]))


