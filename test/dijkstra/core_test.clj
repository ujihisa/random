(ns dijkstra.core-test
  (:require [expectations :refer :all]
            [dijkstra.core :refer :all]))


(let [all-vertices #{:a :b :c :d :e}
      distances {[:a :b] 2
                 [:b :e] 3
                 [:a :e] 10
                 [:c :e] 1}]
  (expect [[:a :b :e] 5] (solve all-vertices distances :a :e)))
