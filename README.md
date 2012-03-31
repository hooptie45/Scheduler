# Schedule

Clojure app to make schedules for multiple people based on their desired work schedule.

## Usage

	(def availablity-spec
	[    ;Name 		WED	THU	FRI	SAT	SUN	MON	TUE 
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
	     :Sean		X A	X A	X A	X A	X A	X X	X X
	])
	
## License

Copyright (C) 2012 Shaun Hannah

Distributed under the Eclipse Public License, the same as Clojure.
