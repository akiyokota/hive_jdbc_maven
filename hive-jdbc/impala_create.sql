

drop table if exists impala_request;
drop table if exists impala_dcunits;
drop table if exists impala_shipmodes;
drop table if exists impala_response;



create external table if not exists impala_request (
	orderid int,
	fulfilldate TIMESTAMP,
	requesttype string,
	membertype string,
	itemid int,
	partnumber int,
	ksn int,
	weight double,
	length double,
	width double,
	height double,
	division int,
	addressid int,
	quantity int,
	storeid string,
	customerzip int,
	attributes string,

	shipmode string,

	originalffmtype string,
	originaldcunit int,
	originalpromisedate TIMESTAMP,

	excluded_priority string,
	excluded_dcunits string,
	excluded_dcunittypes string,
	excluded_ffmtypes string,
	excluded_shipmenttypes string

)
location "/gold/ordersvcqa/qa/request";

create external table if not exists impala_dcunits (
	orderid int,
	itemid int,
	dcunitid int,
	priority int,
	totalOrderCount int,
	maxordercount int,
	isEligible boolean,
	isAvailable boolean,
	regular int,
	presell int,
	tempreserved int,
	numfullpackages int,
	nitemsperpackage int,
	facilitycost double
)
location "/gold/ordersvcqa/qa/dcunits";

create external table if not exists impala_shipmodes (
	orderid int,
	itemid int,
	dcunitid int,
	carrier string,
	shipmode string,
	daysintransit int,
	cost double,
	transportationcost double,
	servicelevel string,
	pickupdate TIMESTAMP,
	promsiedate TIMESTAMP,
	combooption boolean,
	combocustomercost double
)
location "/gold/ordersvcqa/qa/shipmodes";

create external table if not exists impala_response (
	orderid int,
	requesttype string,
	itemid int,

	result string,
	dcunitid int,

	shipmode string,
	daysintransit int,
	cost double,
	carrier string,
	servicelevel string,
	pickupdate TIMESTAMP,
	promsiedate TIMESTAMP,

	mustshipbydate TIMESTAMP,
	originalpromisedate TIMESTAMP,
	quantity int
	
	

)
location "/gold/ordersvcqa/qa/response";