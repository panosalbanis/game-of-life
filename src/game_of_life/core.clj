(ns game-of-life.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Not ready yet."))

(defn neighbours [i lst]
  (let [size (count lst)
        step (int (Math/sqrt size))
        line-start (- i (mod i step))
        line-end (+ line-start (dec step))]
    (concat
      (subvec lst
              (max (- i (inc step)) (- line-start step) 0)
              (max (min (- i (dec (dec step))) (- line-end (dec step))) 0))
      (subvec lst
              (max (dec i) line-start)
              i)
      (subvec lst
              (inc i)
              (min (inc (inc i)) (inc line-end)))
      (subvec lst
              (min (max (+ i (dec step)) (+ line-start step)) size)
              (min (+ i (inc (inc step))) (+ line-end (inc step)) size)))))

(defn alive? [x]
  (if (= x "*")
    true
    false))

(defn decide [cell cnt]
  (cond
    (and (alive? cell) (< cnt 2)) "."
    (and (alive? cell) (> cnt 3)) "."
    (and (not (alive? cell)) (= cnt 3)) "*"
    :else cell))

(defn itrate [lst]
  (loop [x [] l lst cnt 0]
    (if (empty? l)
      x
      (recur
        (conj x (decide (first l) (count (filter alive? (neighbours cnt lst)))))
        (rest l)
        (inc cnt)))))
