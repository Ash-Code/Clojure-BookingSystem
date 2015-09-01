(ns booking.routes.home
  (:require [compojure.core :refer :all]
            [booking.views.layout :as layout]
            [booking.models.db :as db]
            [hiccup.form :refer :all]
            [clojure.walk :as w]
            ))


(defn home []

  (layout/common 
   [:h1 "Welcome"]
   [:h2 "List of available rooms for booking"]
   [:table  
    (for [{:keys [id name seating projector]} (db/read-rooms)]
      [:tr [:td {:border 1}
            [:a {:href (str "/room-api?id=" id ) } [:h2 name ]]
            [:p (str "Seating capacity "  seating)]
            [:p (str "Projector " projector )]]]
      )]

   [:hr]
   [:h2 [:a {:href "/create-room"} "Or create a new room"]]
   ))


(defn meeting [rid time date]
  (layout/common
   [:br]
   [:br]
   [:h1 "Create a new meeting"]
   [:br]
   [:br]

   (form-to [:post "/save-meeting"]
            [:p "Meeting name:"]
            (text-field "name")
            [:p "Meeting description"]
            (text-area {:rows 5 :cols 40} "desc")
            [:p (str "Meeting time " time " hrs.")]
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

(defn test [time2]
  [:tr [:th ""]
       (for [x (range 0 7)]
         [:th (format-time (+ time2 (* x (* 24 60 60 1000))))]
         )]
)

(defn room [id]
  (layout/common
[:br]
[:br]
[:h1 "Please select a day and time"]
[:br]
[:br]
   [:table {:cellpadding 14 :border 2}
    (test (getDate))
    (let [currtime (getDate) day (* 24 60 60 1000)]
      (for [row-iter (range 9 24)]

        [:tr [:th (str row-iter ":00 hrs ")] 
         (for [col-iter (range 0 7)]
           (let [date (format-time (+ currtime (* col-iter day)))]
             [:td [:a
                   {:href
                    (if (= nil (db/read-meeting id row-iter date) )
                      (str "/create-meeting?rid=" id "&time="
                           row-iter "&date=" date)
                      (str "/show-meeting?rid=" id "&time="
                           row-iter "&date=" date ) )
                    }
                   (if (= nil (db/read-meeting id row-iter date))
                     "Book" "Booked")] ])
           )]
        )
      )
    ]  
   )  
  )

(defn show-meeting [rid time date]

  (layout/common
   [:h1 (:name (first (db/read-meeting rid time date)))]
   [:h2 (:desc (first (db/read-meeting rid time date)))]
   [:p "At " time " hrs."]
   [:p "On " date]
   [:hr]
   [:h3 (str "In room " (:name (first (db/read-room rid))))]
   [:p (str "With seating capacity " (:seating (first (db/read-room rid))))]
   [:p (str "With projector availability "
            (:projector (first (db/read-room rid))))]

   )

  )

(defn add-room [] 
  (layout/common 
[:br]
[:br]
[:h1 "Create a new room"]
[:br]
[:br]
   (form-to [:post "/save-room"]
            [:p "Room name:"]
            (text-field "name")
            [:p "Room seating capacity"]
            (text-field "cap")
            [:p "Room projector availability"]
            (text-field "projector")
            [:br]
            [:br]
            (submit-button "create room"))
   )
  )


(defn errormessage [link]
  (layout/common
   [:h2 "Invalid data, please try again."] [:br] [:a {:href link} [:h3 "Go back"] ])
  )


(defn save-room [name cap projector]
  (cond
   (empty? name) (errormessage "/")
   (empty? cap) (errormessage "/")
   :else
   (do (db/save-room name cap projector)
       (str "<script> window.location=\"/\";</script>")))
  )




(defn save-meeting
  [rid name desc time date]
  (cond 
   (empty? name) (errormessage (str "/room-api?id=" rid))
   (empty? desc) (errormessage (str "/room-api?id=" rid))
   :else
   (do (db/save-meeting rid name desc time date)
       (str "<script> window.location=\"/room-api?id="rid "\"</script>")))
 
  )


(defroutes home-routes
  (GET "/" [] (home))
  (POST "/save-meeting" [name desc rid time date]
        (save-meeting rid name desc time date ))
  (GET "/create-meeting" [rid time date] (meeting rid time date) )
  (POST "/save-room" [name cap projector] (save-room name cap projector) )
  (GET "/create-room" [] (add-room))
  (GET "/room-api"  [id] (room id) )
  (GET "/show-meeting" [rid time date] (show-meeting rid time date))
  (GET "/rooms/:id" [id] (room id))
  )
