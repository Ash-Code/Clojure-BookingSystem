(ns booking.routes.meeting
  (:require [compojure.core :refer :all]
            [booking.views.layout :as layout]))

(defn meeting []
  (layout/common [:h1 "Hello world"]))

(defroutes meeting-routes
  (GET "/" [] (home)))
