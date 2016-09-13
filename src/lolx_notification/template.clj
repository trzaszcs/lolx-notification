(ns lolx-notification.template
  (:require 
   [hbs.core :as hbs]
   [clojure.java.io :as io]))

(hbs/set-template-url! (str (io/resource "templates")) ".html")

(defn resolve 
  [type context]
  {:subject "test"
   :content (hbs/render-file "test" {})})
