(ns booking.routes.home
  (:require [compojure.core :refer :all]
            [booking.views.layout :as layout]
            [booking.models.db :as db]
            [hiccup.form :refer :all]
            [clojure.walk :as w]
            ))


(defn home []
  (layout/common 
(for [{:keys [id name seating projector]} (db/read-rooms)]
   [:li
    [:a {:href (str "/room-api?id=" id ) } [:h2 name ]]
    [:p (str "Seating capacity "  seating)]
    [:p (str "Projector " (if (= projector "false" ) "No" "Yes") )]
    ])
))

(defn meeting [rid time date]
  (layout/common
   (form-to [:post "/save-meeting"]
                          [:p "Meeting name:"]
                          (text-field "name")
                          [:p "Meeting description"]
                          (text-area {:rows 5 :cols 40} "desc")
                          [:p (str "Meeting time " time)]
                          (hidden-field "rid" rid)
                          (hidden-field "time" time)
                          (hidden-field "date" date)
                          [:p (str "Meeting day " date)]
                          [:br]
                          (submit-button "submit"))))


(defn format-time [timestamp]
  (-> "dd-MM-yy"
      (java.text.SimpleDateFormat.)
      (.format timestamp)))

(defn getDate []
  (System/currentTimeMillis))


(defn gen []
  (layout/common
   [:ul
    (for [x (range 1 4)] [:li x])
    ])
)


(defn room [id]
  (layout/common
   [:table
    (let [currtime (getDate) day (* 24 60 60 1000)]
      [:tr [:td "empty"]
       (for [x (range 0 6)]
         [:td (format-time (+ currtime (* x day)))]
         )]
      (for [row-iter (range 9 21)]
        [:tr [:th row-iter] 
         (for [col-iter (range 0 6)]
           (let [date (format-time (+ currtime (* col-iter day)))]
             [:td [:a
                   {:href
                    (if (= nil (db/read-meeting id row-iter date) )(str "/create-meeting?rid=" id "&time=" row-iter "&date=" date) (str "/show-meeting?rid=" id "&time=" row-iter "&date" date ) )} (if (= nil (db/read-meeting id row-iter date)) "Book" "Booked")] ])
           )]
        )
      )
    ]  
   )  
  )

(defn show-meeting [rid time date]
(layout/common

)
)


(defn add-room [] 
  (layout/common 
   (form-to [:post "/create-room"]
            [:p "Room name:"]
            (text-field "name")
            [:p "Room seating capacity"]
            (text-area {:rows 5 :cols 40} "cap")                                        [:br]
            [:a {:href "/"} (submit-button "submit")])
   )
  )


(defn save-room [name cap]
  (db/save-room name 20 "false")
  )

(defn save-meeting
  [rid name desc time date]
  (db/save-meeting rid name desc time date)
(str "<script> window.location=\"/\";</script>")
  )

(defroutes home-routes
  (GET "/" [] (home))
  (POST "/save-meeting" [name desc rid time date] (save-meeting rid name desc time date ))
  (GET "/create-meeting" [rid time date] (meeting rid time date) )
  (POST "/create-room" [name cap] (save-meeting name cap) )
  (GET "/room-api"  [id] (room id) )
  (GET "/show-meeting" [rid time date] (show-meeting rid time date))
  (GET "/rooms/:id" [id] (room id))

  )
