(ns lolx-notification.jwt
  (:require 
   [clj-jwt.core  :refer :all]
   [clj-jwt.key   :refer [private-key public-key]]
   [clj-time.core :refer [now plus days]]
   [clojure.java.io :as io]))

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
