(ns like-counter.core
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [sablono.core :as sab]
            [cljs.core.async :refer [<! >!]]
            [cljs-http.client :as rest]
            [chord.client :refer [ws-ch]]))

(defn like-response-handler [data]
  (go
    (let [response (<! (rest/post "http://localhost:3599/backend/likes" {}))]
      (swap! data assoc :likes (:body response)))))

(defn like-ws-handler [data]
  (go
    (let [ws-channel (:ws-channel @data)]
      (>! ws-channel {:event-type :like})
      (let [likes (:message (<! ws-channel))]
        (swap! data assoc :likes (:likes likes))))))

(defn like-me [data]
  (sab/html [:div
             [:h1 "My quantified popularity: " (:likes @data)]
             [:div
               [:a {:href "#"
                    :onClick (partial like-response-handler data)}
                    "Thumbs up"]]
             [:div
               [:a {:href "#"
                    :onClick (partial like-ws-handler data)}
                    "WS Thumbs up"]
              ]]))

(defonce app-state (atom { :likes 0
                           :ws-channel nil} ))


(go (swap! app-state assoc :likes (:body (<! (rest/get "http://localhost:3599/backend/likes")))
                           :ws-channel (:ws-channel (<! (ws-ch "ws://localhost:9999/ws" {:format :edn})))))

(defn render! []
  (.render js/React
           (like-me app-state)
           (.getElementById js/document "app")))

(add-watch app-state :on-change (fn [_ _ _ _] (render!)))

(render!)
