(ns lolx-notification.email
  (:require 
   [postal.core :as postal]
   [environ.core :refer [env]]))

(defonce config {:host "smtp.gmail.com"
             :user (env :email-user)
             :pass (env :email-password)
             :ssl :yes})

(defonce from (str (:user config) "@gmail.com"))

(defn send!
  [to subject html-content]
  (postal/send-message config
       {:from from
        :to to
        :subject subject
        :body [:aleternative {:type "text/html" :content html-content}]}))
