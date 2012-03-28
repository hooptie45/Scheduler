(ns schedule.scheduler)

;; Object to represent a work day
;; TODO: Make the number of shifts variable
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
  ""
  ([work-day work-shift processed-availablity] (available work-day work-shift processed-availablity :emp))
  ([work-day work-shift processed-availablity finisher-fn]     
     (map finisher-fn
          (filter
           (fn [el]
             (and
              (= work-day (:day el))
              (>= (work-shift el) 0)))
           processed-availablity))))

(comment
  
  ;; Test availability spec, Cut and paste from an excel spreadsheet,
  ;; with minor editing
  ;;
  ;; TODO: Pull directly from a data source, like an excel
  ;; spreadsheet, or HTTP request
  (def test-availity-spec
    [;Name 		WED	THU	FRI	SAT	SUN	MON	TUE 
     :Adam		A A	X X	A X	A X	A X	A X	X X
     :Alyssa		X A	X A	X X	X A	X A	X X	X A 
     :Caitlin       	X A	X A	X A	A X	X A	X A	X X             
     :Cathy		A X	A X	X X	X X	A X	A X	A X 
     :Caylie		X A	X A	A A	A A	X X	A X	A X 
     :Cinnamon		X X	A X	A X	A X	X X	X X	X X 
     :Davina		X X	X X	X A	X A	X A	X A	X X 
     :Dawn		A X	A X	A A	X X	X X	A X	A X 
     :Denise		X X	A X	A A	X A	A A	A A	A A 
     :Frankie       	A A	X X	A A	A A	X X	X A	X A 
     :Heather       	A X	X X	A X	A X	X X	A X	X X 
     :Hope		A A	X X	X A	X A	A A	X X	X X 
     :Jonathan		X A	X A	X X	X X	X X	X A	X A 
     :Kathy		X A	X A	X A	X X	X X	X A	X A 
     :Kimmie		X A	X X	X X	X X	X X	X X	X A
     :Sean		X A	X A	X A	X A	X A	X X	X X])
  (def test-days (process-schedule :spec test-availity-spec))
  
  (available :mon :am test-days)
  (available :mon :pm test-days
             (fn [{:keys [day shift emp]}]
               (swap! *schedule-db* update-in [day] conj emp)))
  
  
  (process-schedule
   [;Name 		WED	THU	FRI	SAT	SUN	MON	TUE 
    :Adam		A A	X X	A X	A X	A X	A X	X X
    :Alyssa		X A	X A	X X	X A	X A	X X	X A 
    :Caitlin       	X A	X A	X A	A X	X A	X A	X X             
    :Cathy		A X	A X	X X	X X	A X	A X	A X 
    :Caylie		X A	X A	A A	A A	X X	A X	A X 
    :Cinnamon		X X	A X	A X	A X	X X	X X	X X 
    :Davina		X X	X X	X A	X A	X A	X A	X X 
    :Dawn		A X	A X	A A	X X	X X	A X	A X 
    :Denise		X X	A X	A A	X A	A A	A A	A A 
    :Frankie       	A A	X X	A A	A A	X X	X A	X A 
    :Heather       	A X	X X	A X	A X	X X	A X	X X 
    :Hope		A A	X X	X A	X A	A A	X X	X X 
    :Jonathan		X A	X A	X X	X X	X X	X A	X A 
    :Kathy		X A	X A	X A	X X	X X	X A	X A 
    :Kimmie		X A	X X	X X	X X	X X	X X	X A
    :Sean		X A	X A	X A	X A	X A	X X	X X])
  )


