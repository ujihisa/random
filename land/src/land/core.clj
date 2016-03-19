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

(defn flat? [chunk]
  (flat?' chunk
          (fn [chunk-snapshot x y z]
            (.getHighestBlockAt chunk-snapshot x z))
          (fn [chunk]
            (.getChunkSnapshot chunk))))

(let [dummy-chunk-snapshot
      (->> (for [x (range 16)
                z (range 16)]
            [[x z] (rand-nth [10 10 10 10 10 10 10 11 11 11 11 12])])
        (into {}))
      getHighestBlockAt
      (fn [chunk-snapshot x z] :dirt)
      getChunkSnapshot
      (fn [chunk] :world)]
  (debug-print-chunk dummy-chunk-snapshot)
  (prn (smooth?' :dummy-chunk getHighestBlockAt getChunkSnapshot)))

; land-purchasable == smooth? + no-water?)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
