(ns schedule.test.schedule
  (:use midje.sweet
        dk.ative.docjure.spreadsheet)
  (:use [schedule.scheduler])
  (:use [clojure.test]))


(def test-availity-spec
  [;Name 		WED	THU	FRI	SAT	SUN	MON	TUE 
   :Adam		X R	X X	A X	A X	A X	A X	X X
   :Alyssa		X D	X A	X X	X A	X A	X X	X A 
   :Caitlin       	X X	X A	X A	A X	X A	X A	X X             
   :Cathy		X X	A X	X X	X X	A X	A X	A X 
   :Caylie		X X	X A	A A	A A	X X	A X	A X 
   :Cinnamon		X X	A X	A X	A X	X X	X X	X X 
   :Davina		X X	X X	X A	X A	X A	X A	X X 
   :Dawn		X X	A X	A A	X X	X X	A X	A X 
   :Denise		X X	A X	A A	X A	A A	A A	A A 
   :Frankie       	X X	X X	A A	A A	X X	X A	X A 
   :Heather       	X X	X X	A X	A X	X X	A X	X X 
   :Hope		X X	X X	X A	X A	A A	X X	X X 
   :Jonathan		X X	X A	X X	X X	X X	X A	X A 
   :Kathy		X X	X A	X A	X X	X X	X A	X A 
   :Kimmie		X X	X X	X X	X X	X X	X X	X A
   :Sean		X X	X A	X A	X A	X A	X X	X X])



(unfinished employee-finisher)

;;; Code you'd be proud to give your mom to show off on the fridge. --
;;; Mike Cohn
(facts "available should return employees available for a given day and shift"
  (let [spec (process-schedule :spec test-availity-spec)]
    (fact
      (available :spec spec
                 :shift :pm
                 :day :wed
                 :finisher-fn :emp
                 :scope #{R D}) => [:Adam :Alyssa])
    (fact
      (available :spec spec
                 :shift :am
                 :finisher-fn :emp
                 :day :wed) => [])
    (fact
      (available :spec spec
                 :shift :pm
                 :finisher-fn :emp                 
                 :day :mon) => [:Caitlin :Davina :Denise :Frankie :Jonathan :Kathy])))

;;; The right word may be effective, but no word was ever as effective as
;;; a rightly timed pause. -- Twain
(facts
  (fact "Weekend only Job"
    ((make-day-extractor :week-days [:sun :mon])
     [:Nanarpuss D D D D])
    => [{:day :sun :emp :Nanarpuss :am 1 :pm 1}
        {:day :mon :emp :Nanarpuss :am 1 :pm 1}]) 
  
  (fact "Extract customizable shifts"
    ((make-day-extractor :shifts [:coding :working])
     [:Nanarpuss D D D D D D D D D D D D D D])
    => [{:day :wed, :emp :Nanarpuss, :working D, :coding D}
        {:day :thu, :emp :Nanarpuss, :working D, :coding D}
        {:day :fri, :emp :Nanarpuss, :working D, :coding D}
        {:day :sat, :emp :Nanarpuss, :working D, :coding D}
        {:day :sun, :emp :Nanarpuss, :working D, :coding D}
        {:day :mon, :emp :Nanarpuss, :working D, :coding D}
        {:day :tue, :emp :Nanarpuss, :working D, :coding D}])
  
  (fact "Basic extractor test"
    ((make-day-extractor)
     [:Adam D D D D D D D D D D D D D D])
    => [{:day :wed, :emp :Adam, :pm D, :am D}
        {:day :thu, :emp :Adam, :pm D, :am D}
        {:day :fri, :emp :Adam, :pm D, :am D}
        {:day :sat, :emp :Adam, :pm D, :am D}
        {:day :sun, :emp :Adam, :pm D, :am D}
        {:day :mon, :emp :Adam, :pm D, :am D}
        {:day :tue, :emp :Adam, :pm D, :am D}])
  
  (fact "extractor should work for multiple-weeks at a time"
    ((make-day-extractor)
     [:Adam A A X X A X A X A X A X X X A A X X A X A X A X A X X X])
    => [{:day :wed, :emp :Adam, :pm 0,  :am 0}
        {:day :thu, :emp :Adam, :pm -2, :am -2}
        {:day :fri, :emp :Adam, :pm -2, :am 0}
        {:day :sat, :emp :Adam, :pm -2, :am 0}
        {:day :sun, :emp :Adam, :pm -2, :am 0}
        {:day :mon, :emp :Adam, :pm -2, :am 0}
        {:day :tue, :emp :Adam, :pm -2, :am -2}
        {:day :wed, :emp :Adam, :pm 0,  :am 0}
        {:day :thu, :emp :Adam, :pm -2, :am -2}
        {:day :fri, :emp :Adam, :pm -2, :am 0}
        {:day :sat, :emp :Adam, :pm -2, :am 0}
        {:day :sun, :emp :Adam, :pm -2, :am 0}
        {:day :mon, :emp :Adam, :pm -2, :am 0}
        {:day :tue, :emp :Adam, :pm -2, :am -2}])
  )

(facts "Labor"
  ())