(ns game-of-life.core-test
  (:require [clojure.test :refer :all]
            [game-of-life.core :refer :all]))

(deftest neighbours-middle-test
  (testing "A cell not in the edge should return 8 items"
    (is (= [0 1 2 3 5 6 7 8] (neighbours 4 [0 1 2 3 4 5 6 7 8])))))

(deftest neighbours-first-test
  (testing "The first cell should return 3 items"
    (is (= [1 3 4] (neighbours 0 [0 1 2 3 4 5 6 7 8])))))

(deftest neighbours-last-test
  (testing "The last cell should return 3 items"
    (is (= [4 5 7] (neighbours 8 [0 1 2 3 4 5 6 7 8])))))
