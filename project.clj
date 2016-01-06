(defproject dijkstra "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "GPL3 or any later versions"
            :url "http://www.gnu.org/licenses/gpl-3.0.en.html"}
  :dependencies [[org.clojure/clojure "1.8.0-RC4"]]
  :main ^:skip-aot dijkstra.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
