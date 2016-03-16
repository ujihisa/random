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

(let [dummy-chunk-snapshot
      (-> (for [x (range 16)
                z (range 16)]
            [[x z] (rand-nth [10 10 10 10 10 10 10 11])])
        (into {}))
      getHighestBlockAt
      (fn [chunk-snapshot x z] :dirt)
      getChunkSnapshot
      (fn [chunk] :world)]
  (doseq [[k v] dummy-chunk-snapshot]
    (prn :key k :value v))
  (prn)
  (prn (flat?' :dummy-chunk getHighestBlockAt getChunkSnapshot)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
