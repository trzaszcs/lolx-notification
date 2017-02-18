(ns lolx-notification.clients
  (:require
   [clojure.data.json :as json]
   [lolx-notification.jwt :as jwt]
   [clj-http.client :as client]
   [environ.core :refer [env]]))


(defn- as-json
  [str]
  (json/read-str str))

(defn anounce-details
  [anounce-id]
  (let [response (client/get (str (env :backend-url) "/anounces" anounce-id)
                                 {:throw-exceptions false})]
    (when (= 200 (:status response))
      (let [anounce (as-json (:body response))]
        (:subject (:subject anounce) :author-id (:ownerId anounce)))
      )
    ))


(defn user-details
  [user-id]
  (let [response (client/get (str (env :auth-url) "/users" user-id)
                             {:headers {"Authorization" (str "Bearer " (jwt/produce user-id))}
                              :throw-exceptions false})]
    (when (= 200 (:status response))
      (:email (:email(as-json (:body response))))
      )))
