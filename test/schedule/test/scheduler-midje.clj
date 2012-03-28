(ns schedule.test.core
  (:use midje.sweet
        dk.ative.docjure.spreadsheet)
  (:use [schedule.scheduler])
  (:use [clojure.test]))



(unfinished extract-shifts)



;.;. There is an inevitable reward for good deeds. -- Ming Fu Wu
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
