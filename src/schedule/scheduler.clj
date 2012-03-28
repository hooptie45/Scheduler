(ns schedule.scheduler)

;; Object to represent a work day
(defrecord Day [emp day am pm])

;; One your (D)ream schedule, you would want to work this shift
(def D 1)
;; You are (A)vailable to work this shift
(def A 0)
;; You made a (R)equest to be off this day
(def R -1)
;; You not available on this shift
(def X -2)

(defn make-day-extractor 
  "Creates a function to extract the days from an array of availablities"
  [& {:keys [shifts week-days]
      :or {shifts [:am :pm]
           week-days [:wed :thu :fri :sat :sun :mon :tue]}}]
  (fn [[name & work-days]]    
    (let [groups-of-? (partition (count shifts) work-days)]
      (map (fn [day work-day]             
             (merge
              (zipmap shifts work-day)
              {:emp name :day day}))
           (cycle week-days)
           groups-of-?))))

(defn process-schedule 
  "Takes an availablity spec, as a basic array, and converts it into a list of Days"
  [& {:keys [spec shifts week-days]
      :or   {shifts [:am :pm]
             week-days [:wed :thu :fri :sat :sun :mon :tue]}}]
  (flatten
   (map (make-day-extractor :shifts shifts :week-days week-days)
        (partition (+ 1 (* 7 (count shifts))) spec))))




(defn available
  [& {:keys [day shift spec finisher-fn  scope]
      :or {finisher-fn identity
           scope #{A}}}]   
  (map finisher-fn
       (filter
        (fn [el]
          (and
           (= day (:day el))
           (scope (shift el))))
        spec)))




