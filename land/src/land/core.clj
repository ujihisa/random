(ns land.core
  (:gen-class))

(defn flat?'
  "true if all the heights of the top blocks of the chunk are same"
  [chunk getHighestBlockAt getWorld]
  (let [blocks (for [x (range 16)
                     z (range 16)]
                 (getHighestBlockAt (getWorld chunk) x z))]
    (first blocks)))

(defn flat? [chunk]
  (flat?' chunk
          (fn [world x y z]
            (.getHighestBlockAt world x y z))
          (fn [chunk]
            (.getWorld chunk))))

(prn (let [getHighestBlockAt
           (fn [world xdiff zdiff] :dirt)
           getWorld
           (fn [chunk] :world)]
       (flat?' :dummy-chunk getHighestBlockAt getWorld)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
