(ns lolx-notification.email
  (:require
   [postal.core :as postal]
   [environ.core :refer [env]]
   [clojure.tools.logging :as log]))

(defonce config {:host "smtp.gmail.com"
                 :user (env :email-user)
                 :pass (env :email-password)
                 :ssl true})

(defonce from (str (:user config) "@gmail.com"))

(defn send!
  [to subject html-content]
  (log/info (str "sending email to" to " with subject" subject))
  (postal/send-message config
       {:from from
        :to to
        :subject subject
        :body [:aleternative {:type "text/html;charset=utf-8" :content html-content}]}))
