(ns booking.models.db
        (:require [clojure.java.jdbc :as sql])
        (:import java.sql.DriverManager))

(def db {:classname "org.sqlite.JDBC",
         :subprotocol "sqlite",
         :subname "db.sq3"})

(defn read-wikis []
  (sql/with-connection
    db
    (sql/with-query-results res
      ["SELECT * FROM wikipedia ORDER BY timestamp DESC"]
      (doall res))))

(defn save-wiki [title body]
  (sql/with-connection
    db
    (sql/insert-values
     :wikipedia
     [:title :body :timestamp]
     [title body (new java.util.Date)])))


(defn read-entry [id]
  (sql/with-connection
    db (sql/with-query-results res ["SELECT * FROM wikipedia WHERE id=? "  id]
 
         (do (println "read-entry called") res)         
         )
    )
  )

(defn create-wikipedia-table []
  (sql/with-connection
    db
    (sql/create-table
     :wikipedia
     [:id "INTEGER PRIMARY KEY AUTOINCREMENT"]
     [:timestamp "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"]
     [:title "TEXT"]
     [:body "TEXT"])

    (sql/do-commands "CREATE INDEX timestamp_index ON wikipedia (timestamp)")))


(defn create-rooms-table []
  (sql/with-connection
    db (sql/create-table
        :rooms
        [:id "INTEGER PRIMARY KEY AUTOINCREMENT"]
        [:name "TEXT"]
        [:seating "INTEGER"]
        [:projector "BOOLEAN"]
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
         (do (println "read-entry called") res)         
         )
    )
)


(defn read-meeting [rid time meeting] 
  (sql/with-connection
    db (sql/with-query-results res ["SELECT * FROM meeting WHERE rid=? AND time=? AND date=? "  rid time date]
         (do (println "read-meetings called") res)         
         )
    )

  )