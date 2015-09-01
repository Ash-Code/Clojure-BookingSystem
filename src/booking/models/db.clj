(ns booking.models.db
        (:require [clojure.java.jdbc :as sql])
        (:import java.sql.DriverManager))

(def db {:classname "org.sqlite.JDBC",
         :subprotocol "sqlite",
         :subname "db.sq3"})


(defn create-rooms-table []
  (sql/with-connection
    db (sql/create-table
        :rooms
        [:id "INTEGER PRIMARY KEY AUTOINCREMENT"]
        [:name "TEXT"]
        [:seating "TEXT"]
        [:projector "TEXT"]
        )
    )
  )

(defn create-meetings-table []
  (sql/with-connection
    db (sql/create-table
        :meetings
        [:rid "INTEGER"]
        [:name "TEXT"]
        [:desc "TEXT"]
        [:time "INTEGER"]
        [:date "TEXT"]
        )
    )


  )

(defn read-room [id]
(sql/with-connection
    db (sql/with-query-results res ["SELECT * FROM rooms WHERE id=? "  id]        
        (do res) )

    )
)


(defn read-rooms []
  (sql/with-connection
    db (sql/with-query-results res ["SELECT * FROM rooms"]
         (doall res)
         )
    )
  )


(defn read-meetings []
  (sql/with-connection
    db (sql/with-query-results res ["SELECT * FROM meetings"]
         (doall res)
         )

    )


  )

(defn read-meeting [rid time date] 
  (sql/with-connection
    db (sql/with-query-results res ["SELECT * FROM meetings WHERE rid=? AND time=? AND date=? "  rid time date]
         (do (println "read-meetings called") res)         
         )
    )

  )

 (defn save-meeting [rid name desc time date]
     (sql/with-connection
       db
       (sql/insert-values
        :meetings
        [:rid :name :desc :time :date]
        [rid name desc time date])))

(defn save-room [name seating projector]
(sql/with-connection
    db
    (sql/insert-values
     :rooms
     [:name :seating :projector]
     [name seating projector])
))
