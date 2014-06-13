(ns game-of-life.core
  (:gen-class))

(defn alive? [cell]
  (if (= cell :alive)
    true
    false))

(defn count-alive [[top [ml _ mr] bottom]]
  (count
    (concat
      (filter alive? top)
      (filter alive? [ml])
      (filter alive? [mr])
      (filter alive? bottom))))

(defn neighbours [x y grid]
  (vec
    (map vec
      (partition 3
        (for [i (map (partial + x) (range -1 2))
              j (map (partial + y) (range -1 2))]
          (get-in grid [i j] nil))))))

(defn construct-enhanced-grid [grid]
  (vec
    (map vec
      (partition 3
        (for [i (range (count grid))
              j (range (count grid))]
          [(get-in grid [i j]) (count-alive (neighbours i j grid))])))))

(defn decider [[previous-state alive-neighbours]]
  (cond
    (and (alive? previous-state) (< alive-neighbours 2)) :dead
    (and (alive? previous-state) (> alive-neighbours 3)) :dead
    (and (not (alive? previous-state)) (= alive-neighbours 3)) :alive
    :else previous-state))

(defn execute-cycle [enhanced-grid]
  (map vec (map (partial map decider) enhanced-grid)))

(defn advance [grid]
  (vec (execute-cycle (construct-enhanced-grid grid))))

(defn -main
  "Game Of Life"
  [& args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))

  (let [initial-grid (try
                       (load-file (first args))
                       (catch java.io.FileNotFoundException e
                         (println "First argument must be a valid file name")
                         (System/exit -1)))]

  (loop [grid initial-grid]
    (do
      (Thread/sleep 1000)
      (doseq [line grid]
        (println line))
      (println))
    (recur (advance grid)))))
