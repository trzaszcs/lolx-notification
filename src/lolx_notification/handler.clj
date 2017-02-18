(ns lolx-notification.handler
  (:require [lolx-notification.email :as email]
            [lolx-notification.jwt :as jwt]
            [lolx-notification.template :as template]
            [lolx-notification.clients :as clients]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :refer [site]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]))

(defn- extract-jwt
  [headers]
  (let [authorization-header (get headers "authorization")]
    (when (not (nil? authorization-header))
      (clojure.string/replace-first authorization-header #"Bearer " ""))))

(defn notify
  [request]
  (if-let [jwt-token (extract-jwt (:headers request))]
    (let [{email-to :email context :context type :type} (request :body)]
      (if (jwt/ok? jwt-token email-to)
        (if-let [template (template/resolve type context)]
          (do
            (email/send! email-to (template :subject) (template :content))
            {:status 201})
          {:status 404})
        {:status 401}
        )
      )
    {:status 400}))

(defroutes app-routes
  (GET "/" [] "Hello Lolx-Notification")
  (POST "/notify" []  notify)
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-json-response)
      (wrap-json-body {:keywords? true :bigdecimals? true})
      (wrap-defaults (assoc-in site-defaults [:security] {:anti-forgery false}))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
