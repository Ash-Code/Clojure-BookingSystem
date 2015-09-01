(defproject booking "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.5"]
                 [ring-server "0.3.1"]
                 [ring/ring-codec "1.0.0"]
                 [org.clojure/java.jdbc "0.2.3"]
                 [org.xerial/sqlite-jdbc "3.7.2"]

]
  :plugins [[lein-ring "0.8.12"]]
  :ring {:handler booking.handler/app
         :init booking.handler/init
         :destroy booking.handler/destroy}
  :profiles
  {:uberjar {:aot :all}
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [
[clj-webdriver "0.7.1"]
                        [org.seleniumhq.selenium/selenium-server "2.47.0"]
                        [javax.servlet/servlet-api "2.5"]
                        [ring/ring-jetty-adapter "1.4.0"]
[ring-mock "0.1.5"] [ring/ring-devel "1.3.1"]]}})
