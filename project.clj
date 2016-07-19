(defproject like-counter "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.8.51"]
                 [org.clojure/core.async "0.2.374" :exclusions [org.clojure/tools.reader]]
                 [sablono "0.3.6"]
                 [ring/ring-core "1.5.0"]
                 [compojure "1.0.2"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/tools.logging "0.2.3"]
                 [http-kit "2.1.5"]
                 [javax.servlet/servlet-api "2.5"]
                 [cljs-http "0.1.11"]
                 [jarohen/chord "0.6.0"]]
  :plugins [[lein-figwheel "0.5.4-7"]
            [lein-cljsbuild "1.1.3" :exclusions [[org.clojure/clojure]]]]
  :clean-targets ^{:protect false} [:target-path "out" "output" "resources/public/cljs"]
  :main main
  :source-paths ["src/clj"]
  :output-path "output"
  :cljsbuild {
    :builds [{:id "dev"
              :source-paths ["src/cljs"]
              :figwheel false
              :compiler {:main "like-counter.core"
                         :asset-path "cljs/out"
                         :output-to "resources/public/cljs/main.js"
                         :output-dir "resources/public/cljs/out"}
             }]
   }
  :figwheel {
    :css-dirs ["resources/public/css"]
  })
