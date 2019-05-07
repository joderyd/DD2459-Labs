
### Exercises  

  
  **  **  
  


**1.** Recall	the	**2-bit	shift	register** discussed	in	the	lecture	notes	(Lecture	6).	Draw	a	model	of this	system	using	the	UML	2.4	Statechart	language.	**Hint:**	you	should	use	a graphics	tool,	such	as	Powerpoint	or	any similar tool.  
* [UML diagram of 2-bit shift register](misc/2bitshift-UML.PNG)  
  
  
  **  **  
  

**2.** Consider	the	file	**bitshift.smv**.	This	file	is	an	incomplete	model	of	the	**_2-bit	shift	register_**.	In	particular,	the	next	state	functions	for	_Bit1_ and	_Bit2_ are	_missing_.	**Complete these	definitions.**	Then	execute	the	file	using	the	command  
`NuSMV –bmc bitshift.smv`  
Note	down	carefully	the	output	of	NuSMV,	and	use	it	to	confirm	that	your	completed	definitions	are	actually	correct. Which specific	output	data	confirms	the	correctness	of	your definitions?  
* [Bitshift_output](misc/bitshift.smv.output)  
  
  
  **  **  
  
**3.** Consider	the	simple	**_car-controller_** discussed	in	the	lecture	notes.	Download	the file	**carcontroller.smv** from	the	course	web	page.	You	will	now	generate	three test	cases	that	achieve	**100%	node coverage** _(NC)_ for	the	CC.	We	want	to	be	sure	we	reach	each	node	
(state).	There	are	3	nodes	in	total.  

  
  
  * __(a)__ Run	NuSMV	on	the	existing	file	carcontroller.smv with	the	command	 
`NuSMV –bmc carcontroller.smv`  
Write	down	the	counterexample to	the	1	NC	trap	property which	the	tool	generates.	Clearly	this	achieves	33%	NC!  
 [Original carcontroller.smv output](misc/carcontroller3a.smv.output)  



  * __(b)__ Extract	from	this	_counterexample_	a	suitable	__test	case__,	consisting	of	input	values	
and	output	predictions.	Hint:	you	could	e.g.	transfer	the	sequences	of	values	to	an	MS Excel spreadsheet	or	similar.	Remember	to	store	the	test	_requirement_	alongside	the	test	case	for	future	reference.  

The first _requirement_ is that state=`stop`, which is the initial value, and accelerate=_false_ & brake=_false_  

*n* | _state_ | _accelerate_ | _brake_
--- | --- | --- | ---
0 | `stop` | false | false  

   
   
  * __(c)__ Write	out	2	additional	trap	properties needed	for	100%	NC	as	LTL	formulas	so	that	you	achieve	100%	NC.	Then	execute	the	file	again	and	repeat	step	_(b)_	above	to	extract	the	2	additional	test	cases.	You	should	now	have	achieved	100%	NC!  
  The second _requirement_ is that state=`slow`  
  
  *n* | _state_ | _accelerate_ | _brake_
--- | --- | --- | ---
0 | `stop` | false | false  
1 | `slow` | **true** | false  
  
  
The third _requirement_ is that state=`fast`  
  
  *n* | _state_ | _accelerate_ | _brake_
--- | --- | --- | ---
0 | `stop` | false | false  
1 | `slow` | true | false  
2 | `fast` | true | false
  
  
  **  **  
  

**4.**	Continuing	with	the	car-controller	example,	we	are	going	to	generate	a	set	of	test	cases	which	achieve	100% edge	coverage (EC).	We	want	to	be	sure	we	traverse	each	edge	between	any	pair	of	nodes.	There	are	6	edges	in	total.  

  * __(a)__ Run	NuSMV	on	the	existing	file	carcontroller.smv with	the	command `NuSMV –bmc carcontroller.smv`  
Write	down	the	counterexample to	the	1	EC	trap	property which	the	tool	generates.	Clearly	this	achieves	16%	EC!  
[16% EC output](misc/carcontroller4a.smv.output)  

  * __(b)__ Extract	from	this	counterexample	a	suitable	test	case,	consisting	of	input	values	and	output	predictions.	__Hint:__	you	could	e.g.	transfer	the	sequences	of	values	to	an	MS Excel spreadsheet	or	similar. Remember	to	store	the	test requirement alongside	the	test	case	for	future	reference.  
  * __(c)__ Write	out	5	additional	trap	properties needed	for	100%	EC	as	LTL	formulas	so	that	you	achieve	100%	EC.	Then	execute	the	file	again	and	repeat	step	_(b)_	above	to	extract	the	5 additional	test	cases.	You	should	now	have	achieved	100%	EC!



[Link to specification as pdf](misc/DD2459-lab3_2016.pdf)

