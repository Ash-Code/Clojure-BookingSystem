(ns booking.routes.home
  (:require [compojure.core :refer :all]
            [booking.views.layout :as layout]
            [booking.models.db :as db]
))

(defn home []
  (layout/common [:h1 "Hello World!"]))

(defn meeting [rid time date]
  (layout/common []
                 (form-to [:post "/:rid/:date/:time"]
                          [:p "Meeting name:"]
                          (text-field "name" name)
                          [:p "Meeting description"]
                          (text-area {:rows 5 :cols 40} "desc" desc)
                          [:p (str "Meeting time " time)]
                          [:p (str "Meeting day " date)]
                          [:br]
                          (submit-button "submit")
                          ))

(defn save-meeting
[]

)


(defroutes home-routes
  (GET "/" [] (home))
  (POST "/:rid/:date/:time" [name desc] (save-meeting id name desc time date ) )

)
