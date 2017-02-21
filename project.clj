(defproject lolx-notification "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.draines/postal "2.0.2"]
                 [compojure "1.5.1"]
                 [ring/ring-defaults "0.2.1"]
                 [ring/ring-json "0.4.0"]
                 [clj-jwt "0.1.1"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [camel-snake-kebab "0.4.0"]
                 [environ "1.1.0"]
                 [hbs "0.9.2"]
                 [clj-http "3.3.0"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/tools.logging "0.3.1"]]
  :plugins [[lein-ring "0.9.7"]
            [lein-midje "3.1.3"]
            [lein-environ "1.1.0"]]
  :ring {:handler lolx-notification.handler/app}
  :uberjar-name "lolx-notification-standalone.jar"
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]
                        [midje "1.6.3"]]}})
