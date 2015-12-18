
<html>
	<head>
		<title>BookMyRide | RideRequest Details</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<!--[if lte IE 8]><script src="../../js/ie/html5shiv.js"></script><![endif]-->
		<link rel="stylesheet" href="../../css/main.css" />
		<link rel="stylesheet" href="../../css/jquery.datetimepicker.css"/ >
		<!--[if lte IE 8]><link rel="stylesheet" href="../../css/ie8.css" /><![endif]-->
	</head>
	<body class="landing">
		<div id="page-wrapper">

			<!-- Header -->
				<header id="header">
					<h1><a href="/BookMyRide/">BookMyRide</a></h1>
					<nav id="nav">
						<ul>
							<li><a href="/BookMyRide/">Home</a></li>
							<li>
								<a href="#" class="icon fa-angle-down">${loggedInUser.getFirstName()}</a>
								<ul>
									<li><a href="/BookMyRide/request">New Ride</a></li>
									<li><a href="/BookMyRide/queue">Queue</a></li>
									<li><a href="/BookMyRide/history">History</a></li>
									
								</ul>
							</li>
							<li><a href="/BookMyRide/logout" class="button">Sign Out</a></li>
						</ul>
					</nav>
				</header>

			<!-- Main -->
				<section id="main" class="container">
					<header>
						<h2>RideRequest Details</h2>
					</header>
					<div class="row">
					
						 <div class="12u">

							<!-- Form -->
								<section class="box">
									<br/>
									<br/>
									<h2>RideRequest Details</h2>
									<form id="cancelRide" method="post" action="/BookMyRide/cancelrequest">
										
										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
											Pickup Address: ${rideRequestInstance.getStartAddress()}	
											</div>
										</div>
										
										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
												Pickup Map: <div id="pickup_map" style="width: 400px; height: 300px;"></div>
											</div>
										</div>
														
										<input type="hidden" name="pickup_latitude" id="pickup_latitude" value="${rideRequestInstance.getStartLatitude()}"/>
										<input type="hidden" name="pickup_longitude" id="pickup_longitude" value="${rideRequestInstance.getStartLongitude()}"/>
										<input type="hidden" name="pickup_address" id="pickup_address" value="${rideRequestInstance.getStartAddress()}"/>
										<input type="hidden" name="request_id" id="request_id" value="${rideRequestInstance.getId()}"/>
										
										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
												Dropoff Address: ${rideRequestInstance.getEndAddress()}
											</div>
										</div>
										
										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
												Dropoff Map: <div id="drop_map" style="width: 400px; height: 300px;"></div>
											</div>
										</div>
														
										<input type="hidden" name="drop_latitude" id="drop_latitude" value="${rideRequestInstance.getEndLatitude()}"/>
										<input type="hidden" name="drop_longitude" id="drop_longitude" value="${rideRequestInstance.getEndLongitude()}"/>
										<input type="hidden" name="drop_address" id="drop_address" value="${rideRequestInstance.getEndAddress()}"/>
										
										<input type="hidden" name="vehicle-select" id="vehicle-select" value="${rideRequestInstance.getProductId()}">
										<input type="hidden" name="datetime" id="datetime" value="${rideRequestInstance.getRequestDate()}">
										<br/>
										
										
										
										<div id="vehicle"></div>
										
										
										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
												Request Time: ${rideRequestInstance.getRequestDate()}
											</div>
										</div>
										
													
										<div class="row uniform">
											<div class="12u">
												<ul class="actions">
													<li><input type="submit" value="Cancel Request" /></li>
												</ul>
											</div>
										</div>
										
										
									</form>
								</section>
						</div> 
						</div>
				</section>
		</div>
		
		
		

		<!-- Scripts -->
			<script src="../../js/jquery.min.js"></script>
			<script src="../../js/jquery.dropotron.min.js"></script>
			<script src="../../js/jquery.scrollgress.min.js"></script>
			<script src="../../js/skel.min.js"></script>
			<script src="../../js/util.js"></script>
			<!--[if lte IE 8]><script src="../../js/ie/respond.min.js"></script><![endif]-->
			<script src="../../js/main.js"></script>
			<script src='https://maps.google.com/maps/api/js?sensor=false&libraries=places'></script>
			<script src="../../js/locationpicker.jquery.js"></script>
			<script src="../../js/jquery.datetimepicker.full.min.js"></script>
			<script>$('#pickup_map').locationpicker({
			location: {latitude: $('#pickup_latitude').val(), longitude: $('#pickup_longitude').val()},	
			radius: 30
	});

			$('#drop_map').locationpicker({
				location: {latitude: $('#drop_latitude').val(), longitude: $('#drop_longitude').val()},	
				radius: 30
				});

			$('#cancelRide').submit(function() {
				var confirmation = confirm("Do you want to cancel this ride?");
			        if(confirmation == false)
				        {
				        return false;
				        }
			});

			
	function populateVehicleDetail(){
		
				var product_id = $('#vehicle-select').val();
					//Ajax call to send data to the server,
					$.get("http://localhost:8080/BookMyRide/product",
									{
										product_id : product_id
									},
									function(data) {
										document.getElementById("vehicle").innerHTML = data;
									});
				
				}

	populateVehicleDetail();

			
</script>
			
		

	</body>
</html>