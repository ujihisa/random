(ns dijkstra.core
  (:gen-class))

(defn solve
  "Returns a route like, [(:a :b :e) 2], or \"NO ROUTE_ROUND\""
  [all-vertices distances start-v goal-v]
  (if (= start-v goal-v)
    [[goal-v] 0]
    (let [next-routes
          (for [v (disj all-vertices start-v)
                :let [result
                      (solve (disj all-vertices start-v) distances v goal-v)]
                :when (not= "NO ROUTE FOUND" result)]
            result)

          routes-with-me
          (for [[path cost] next-routes]
            (when-let [new-cost (distances [start-v (first path)])]
              [(cons start-v path) (+ new-cost cost)]))

          routes-with-me
          (remove nil? routes-with-me)]
      (if (empty? routes-with-me)
        "NO ROUTE FOUND"
        (apply min-key second routes-with-me)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
