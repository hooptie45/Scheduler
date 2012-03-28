(ns schedule.test.employee
  (:import [schedule.employee Employee])
  (:use midje.sweet
        dk.ative.docjure.spreadsheet)
  (:use schedule.scheduler
        schedule.employee
        )
  (:use [clojure.test]))


(def test-availity-spec
  [;Name________________WED___THU___FRI___SAT___SUN___MON___TUE
   :Adam		X R , X X , A X , A X , A X , A X , X X
   :Alyssa		X D , X A , X X , X A , X A , X X , X A
   :Caitlin       	X X , X A , X A , A X , X A , X A , X X
   ;;-------------------WED-|-THU-|-FRI-|-SAT-|-SUN-|-MON-|-TUE
   :Cathy		X X , A X , X X , X X , A X , A X , A X
   :Caylie		X X , X A , A A , A A , X X , A X , A X
   :Cinnamon		X X , A X , A X , A X , X X , X X , X X
   ;;-------------------WED-|-THU-|-FRI-|-SAT-|-SUN-|-MON-|-TUE
   :Davina		X X , X X , X A , X A , X A , X A , X X
   :Dawn		X X , A X , A A , X X , X X , A X , A X
   :Denise		X X , A X , A A , X A , A A , A A , A A
   ;;-------------------WED-|-THU-|-FRI-|-SAT-|-SUN-|-MON-|-TUE
   :Frankie       	X X , X X , A A , A A , X X , X A , X A
   :Heather       	X X , X X , A X , A X , X X , A X , X X
   :Hope		X X , X X , X A , X A , A A , X X , X X
   ;;-------------------WED-|-THU-|-FRI-|-SAT-|-SUN-|-MON-|-TUE
   :Jonathan		X X , X A , X X , X X , X X , X A , X A
   :Kathy		X X , X A , X A , X X , X X , X A , X A
   :Kimmie		X X , X X , X X , X X , X X , X X , X A   
   ])
(def test-employee-db (atom {:Adam (Employee. :Adam {})}))

(unfinished employee-finisher)

;; Out of clutter find simplicity; from discord find harmony; in the
;;                               ; middle of difficulty lies opportunity.
;;                               ; -- Einstein



;; Any intelligent fool can make things bigger, more complex, and more
;; violent. It takes a touch of genius -- and a lot of courage -- to move
;; in the opposite direction. -- Schumacher
(facts "Employee Traits"
  (fact    
    (get-employee :Nanarpuss2 :db test-employee-db)
    => nil
    
    (get-employee :Adam :db test-employee-db)
    => (new-employee :Adam)
    
    (create-employee :Adam :db test-employee-db)
    => (new-employee :Adam)
    
    (create-employee :Nanarpuss :db test-employee-db)
    => (new-employee :Nanarpuss)
    
    (create-employee :Nanarpuss :db test-employee-db)
    => (new-employee :Nanarpuss)))


;; This is the future you were hoping for. -- @Vaguery
(facts "available results can/should customizable"
  (let [spec (process-schedule :spec test-availity-spec)]
    (fact "return Employee records"
      (available :spec spec
                 :finisher-fn (fn [{:keys [emp]}] (new-employee emp))
                 :shift :pm
                 :day :wed
                 :scope #{R D}) => [(new-employee :Adam)
                                    (new-employee :Alyssa)])    
    (fact "return a custom map"
      (available :spec spec
                 :finisher-fn (fn [{:keys [emp]}] {:name emp})
                 :shift :pm
                 :day :wed
                 :scope #{R D}) => [{:name :Adam} {:name :Alyssa}])))

