(ns game-of-life.core-test
  (:require [clojure.test :refer :all]
            [game-of-life.core :refer :all]))

(deftest alive-alive?-test
  (testing "An alive cell should return true"
    (is (= true (alive? :alive)))))

(deftest dead-alive?-test
  (testing "A dead cell should return false"
    (is (= false (alive? :dead)))))

(deftest count-alive-test
  (testing "A cell surrounded by alive cells should return 8"
    (is (= 8 (count-alive [[:alive :alive :alive]
                           [:alive :alive :alive]
                           [:alive :alive :alive]])))))

(deftest middle-neighbours-test
  (testing "Should return an 3x3 grid with the cell in the center"
    (is (= [[:alive :alive :alive]
            [:alive :dead :alive]
            [:alive :alive :alive]] (neighbours  1 1 [[:alive :alive :alive :dead]
                                                      [:alive :dead :alive :dead]
                                                      [:alive :alive :alive :dead]
                                                      [:dead :dead :dead :dead]])))))

(deftest edge-neighbours-test
  (testing "Should return an 3x3 grid with the cell in the center and nil for out of bound indices"
    (is (= [[nil nil nil]
            [nil :dead :alive]
            [nil :alive :alive]] (neighbours  0 0 [[:dead :alive :dead :dead]
                                                   [:alive :alive :dead :dead]
                                                   [:dead :dead :dead :dead]
                                                   [:dead :dead :dead :dead]])))))

(deftest construct-enhanced-grid-all-dead-test
  (testing "Should return all 0s for an all dead grid"
    (is (= [[[:dead 0] [:dead 0] [:dead 0]]
            [[:dead 0] [:dead 0] [:dead 0]]
            [[:dead 0] [:dead 0] [:dead 0]]] (construct-enhanced-grid [[:dead :dead :dead]
                                                                       [:dead :dead :dead]
                                                                       [:dead :dead :dead]])))))

(deftest construct-enhanced-grid-test
  (testing "Should return a correct pattern for a mixed grid"
    (is (= [[[:dead 1] [:dead 1] [:dead 1]]
            [[:dead 1] [:alive 0] [:dead 1]]
            [[:dead 1] [:dead 1] [:dead 1]]] (construct-enhanced-grid [[:dead :dead :dead]
                                                                       [:dead :alive :dead]
                                                                       [:dead :dead :dead]])))))

(deftest alive-one-alive-neighbour-decider-test
  (testing "Should return dead if a live cell has less than 2 alive neighbours"
    (is (= :dead (decider [:alive 1])))))

(deftest alive-four-alive-neighbours-decider-test
  (testing "Should return dead if a live cell has more than 3 alive neighbours"
    (is (= :dead (decider [:alive 4])))))

(deftest dead-three-alive-neighbours-decider-test
  (testing "Should return alive if a dead cell has exactly 3 alive neighbours"
    (is (= :alive (decider [:dead 3])))))

(deftest alive-three-alive-neighbours-decider-test
  (testing "Should return alive if a live cell has exactly 3 alive neighbours"
    (is (= :alive (decider [:alive 3])))))

(deftest execute-cycle-test
  (testing "Should correctly decide the next state based on the enhanced grid"
    (is (= [[:dead :dead :dead]
            [:dead :dead :dead]
            [:dead :dead :dead]] (execute-cycle [[[:dead 1] [:dead 1] [:dead 1]]
                                                 [[:dead 1] [:alive 0] [:dead 1]]
                                                 [[:dead 1] [:dead 1] [:dead 1]]])))))

(deftest advance-test
  (testing "Should correctly perform one iteration of the game of life"
    (is (= [[:dead :dead :dead]
            [:dead :dead :dead]
            [:dead :dead :dead]] (advance [[:dead :dead :dead]
                                           [:dead :alive :dead]
                                           [:dead :dead :dead]])))))
