(ns booking.routes.home
  (:require [compojure.core :refer :all]
            [booking.views.layout :as layout]
            [booking.models.db :as db]
            [hiccup.form :refer :all]
))

(defn home []
  (layout/common [:h1 "Hello World!"]))

(defn meeting [rid time date &[name desc]]
  (layout/common
   (form-to [:post "/create-meeting"]
                          [:p "Meeting name:"]
                          (text-field "name" name)
                          [:p "Meeting description"]
                          (text-area {:rows 5 :cols 40} "desc" desc)
                          [:p (str "Meeting time " time)]
                          (hidden-field "rid" rid)
                          (hidden-field "time" time)
                          (hidden-field "date" date)
                          [:p (str "Meeting day " date)]
                          [:br]
                          [:a {:href "/"} (submit-button "submit")])))

(defn room[id]
"some"
)



(defn save-meeting
[rid name desc time date]
(db/save-meeting rid name desc time date)
)

(defroutes home-routes
  (GET "/" [] (home))
  (POST "/create-meeting" [name desc rid time date] (save-meeting rid name desc time date ))

)
