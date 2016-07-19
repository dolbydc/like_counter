(ns main
  (:gen-class)
  (:use org.httpkit.server
        [ring.middleware.file-info :only [wrap-file-info]]
        [clojure.data.json :only [json-str read-json]]
        (compojure [core :only [defroutes GET POST context]]
                   [route :only [files not-found]]
                   [handler :only [site]]
                   [route :only [not-found]])))

(def likes (atom 0))

(defn handle-web-socket-msg
  [con edn-msg]
  (let [msg (read-string edn-msg)]
    (when (= (:event-type msg) :like)
      (send! con (prn-str {:likes (swap! likes inc)})))))

(defn handle-web-socket
  [req]
  (with-channel req con
    (on-close con (fn [status] (clojure.pprint/pprint "Client Closed!")))
    (on-receive con (partial handle-web-socket-msg con))))


(defroutes my-routes
  (context "/backend" []
    (GET "/likes" [] (str @likes))
    (POST "/likes" [] (str (swap! likes inc))))
  (files "" {:root "static"})
  (not-found "<p>Page not found</p>"))

(defroutes my-ws-routes
  (GET "/ws" [] handle-web-socket)
  (files "" {:root "static"})
  (not-found "<p>Page not found</p>"))

(defn -main [& args]
  (run-server (-> #'my-routes site) {:port 9899})
  (run-server (-> #'my-ws-routes site) {:port 9999}))
