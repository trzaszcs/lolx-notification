(ns lolx-notification.jwt
  (:require 
   [clj-jwt.core  :refer :all]
   [clj-jwt.key   :refer [private-key public-key]]
   [clj-time.core :refer [now plus days]]
   [clojure.java.io :as io]))

(defn build-claim
  [subject]
  {:iss "notification"
   :exp (plus (now) (days 1))
   :iat (now)
   :sub subject}
  )

(defonce rsa-prv-key (private-key (io/resource "rsa/key") "password"))

(defn rsa-pub-key
  [issuer]
  (public-key (io/resource (str "rsa/" issuer ".pub"))))

(defn ok?
  [jwt-token subject]
  (let [jwt (str->jwt jwt-token)
        issuer (get-in jwt [:claims :iss])]
    (and
     (verify jwt (rsa-pub-key issuer))
     (= subject (get-in jwt [:claims :sub])))
    ))

(defn produce
  [subject]
  (-> (build-claim subject) jwt (sign :RS256 rsa-prv-key) to-str))
