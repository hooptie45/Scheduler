(ns schedule.employee)

(def employee-db (atom {}))

(defrecord Employee [name prefers])

(defn get-employee [name & {:keys [db]
                            :or {db employee-db}}]
  (@db name))

(defn new-employee [name]
  (Employee. name {}))

(defn create-employee [name & {:keys [db]
                               :or {db employee-db}}]
  (or
   (get-employee name :db db)   
   (let [employee (new-employee name)]
     (do (swap! db assoc-in [name] employee)
         employee))))



