(ns land.core
  (:gen-class))

(defn smooth?'
  "true if all the heights of the top blocks of the chunk are
  (1) same, or (2) <=1 diff for each directions.
  i.e. Players can go from any point to another any point just by
  walking and jumping straight."
  [chunk getHighestBlockAt getChunkSnapshot]
  (let [blocks (for [x (range 16)
                     z (range 16)]
                 (getHighestBlockAt (getChunkSnapshot chunk) x z))]
    (first blocks)))

(defn smooth? [chunk]
  (smooth?' chunk
          (fn [chunk-snapshot x y z]
            (.getHighestBlockAt chunk-snapshot x z))
          (fn [chunk]
            (.getChunkSnapshot chunk))))

;---
(defn debug-print-chunk [chunk]
  (println "-----------------------------------------------")
  (doseq [x (range 16)]
    (doseq [z (range 16)]
      (print (get chunk [x z])
             ""))
    (prn))
  (println "-----------------------------------------------"))

(defn gen-rand-chunk []
  (->>
    (for [x (range 16)
          z (range 16)]
      [[x z] (inc (rand-int 10))])
    (into {})))

(defn gen-flat-chunk []
  (let [y (inc (rand-int 10))]
    (->>
      (for [x (range 16)
            z (range 16)]
        [[x z] y])
      (into {}))))

(defn gen-smooth-chunk []
  (letfn [(gen [constraint-points]
            (if (seq constraint-points)
              (let [[base & constraint-points] constraint-points]
                (rand-nth
                  (for [diff [-1 0 1]
                        :let [y (+ base diff)]
                        :when (every? #(<= -1 (- y %) 1) constraint-points)]
                    y)))
              (gen [(inc (rand-int 10))])))]
    (loop [i 0 memo {}]
      (if (<= 16 i)
        memo
        (recur (inc i)
               (letfn [(f [memo j]
                         (let [constraint-points
                               (for [xdiff [-1 0 1]
                                     zdiff [-1 0 1]
                                     :when (not= 0 xdiff zdiff)
                                     :let [y (get memo [(- i xdiff) (- j zdiff)])]
                                     :when y]
                                 y)]
                           (conj memo [[i j] (gen constraint-points)])))]
                 (reduce f memo (range 16))))))))

(debug-print-chunk (gen-smooth-chunk))

(let [dummy-chunk-snapshot
      (->> (for [x (range 16)
                z (range 16)]
            [[x z] (rand-nth [10 10 10 10 10 10 10 11 11 11 11 12])])
        (into {}))
      getHighestBlockAt
      (fn [chunk-snapshot x z] :dirt)
      getChunkSnapshot
      (fn [chunk] :world)]
  #_ (debug-print-chunk dummy-chunk-snapshot)
  (prn (smooth?' :dummy-chunk getHighestBlockAt getChunkSnapshot)))

; land-purchasable == smooth? + no-water?)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
