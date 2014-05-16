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
