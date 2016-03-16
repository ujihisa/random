(ns land.core
  (:gen-class))

(defn flat?'
  "true if all the heights of the top blocks of the chunk are same"
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

(prn (let [dummy-chunk-snapshot
           (-> (for [x []]
                 x)
             (into {}))
           getHighestBlockAt
           (fn [chunk-snapshot x z] :dirt)
           getChunkSnapshot
           (fn [chunk] :world)]
       (prn 'dummy-chunk-snapshot dummy-chunk-snapshot)
       (flat?' :dummy-chunk getHighestBlockAt getChunkSnapshot)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
