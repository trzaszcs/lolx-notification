(ns lolx-notification.template
  (:require
   [hbs.core :as hbs]
   [clojure.java.io :as io]))

(hbs/set-template-url! (str (io/resource "templates")) ".html")

(defonce templates
  {
   :reset {:subject "Resetowanie hasła"
           :name "reset"}}
   :unread-messages {:subject "Masz nieprzeczytane wiadomości"
                     :name "unread-messages"})

(defn resolve
  [type context]
  (if-let [template (get templates (keyword type))]
    {:subject (:subject template)
     :content (hbs/render-file (:name template) context)}))
