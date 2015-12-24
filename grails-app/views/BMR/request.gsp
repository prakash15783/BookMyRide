
<html>
	<head>
		<title>BookMyRide | Request</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<!--[if lte IE 8]><script src="js/ie/html5shiv.js"></script><![endif]-->
		<link rel="stylesheet" href="css/main.css" />
		<link rel="stylesheet" href="css/jquery.datetimepicker.css"/ >
		<!--[if lte IE 8]><link rel="stylesheet" href="css/ie8.css" /><![endif]-->
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
								<a href="#" class="icon fa-angle-down">${userProfile.getFirstName()}</a>
								<ul>
									<li><a href="/BookMyRide/request">New Ride</a></li>
									<li><a href="/BookMyRide/queue">Queue</a></li>
									<li><a href="/BookMyRide/history">History</a></li>
									<li><a href="/BookMyRide/faq">FAQ</a></li>
								</ul>
							</li>
							<li><a href="/BookMyRide/logout" class="button">Sign Out</a></li>
						</ul>
					</nav>
				</header>

			<!-- Main -->
				<section id="main" class="container">
					<header>
						<h2>Request Ride</h2>
					</header>
					<div class="row">
					
						 <div class="12u">

							<!-- Form -->
								<section class="box">
									<br/>
									<br/>
									<h2>Request A Ride</h2>
									<form method="post" action="/BookMyRide/confirm" id="requestForm">
										
										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
											Pickup Address: <input type="text" name="pickup_address" id="pickup_address" value="${params['pickup_address']}" placeholder="Pickup Address"/>	
											</div>
										</div>
										
										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
												Pickup Map: <div id="pickup_map" style="width: 400px; height: 300px;"></div>
											</div>
										</div>
														
										<input type="hidden" name="pickup_latitude" id="pickup_latitude" value="${params['pickup_latitude']}"/>
										<input type="hidden" name="pickup_longitude" id="pickup_longitude" value="${params['pickup_longitude']}"/>

										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
												Dropoff Address: <input type="text" name="drop_address" id="drop_address" value="${params['drop_address']}" placeholder="Dropoff Address"/>
											</div>
										</div>
										
										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
												Dropoff Map: <div id="drop_map" style="width: 400px; height: 300px;"></div>
											</div>
										</div>
														
										<input type="hidden" name="drop_latitude" id="drop_latitude" value="${params['drop_latitude']}"/>
										<input type="hidden" name="drop_longitude" id="drop_longitude" value="${params['drop_longitude']}"/>
										
										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
											<a class="button special icon fa-search" onclick="populateVehicles();">Find Vehicles</a>
											</div>
										</div>
										<br/>
										<div id="vehicles"></div>
										
										
										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
												Pickup Date and Time: <input name=datetime id="datetime" type="text" value="${params['datetime']}" >
											</div>
										</div>
										
													
										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
												<ul class="actions">
													<li><input type="submit" value="Continue" /></li>
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
			<script src="js/jquery.min.js"></script>
			<script src="js/jquery.dropotron.min.js"></script>
			<script src="js/jquery.scrollgress.min.js"></script>
			<script src="js/skel.min.js"></script>
			<script src="js/util.js"></script>
			<!--[if lte IE 8]><script src="js/ie/respond.min.js"></script><![endif]-->
			<script src="js/main.js"></script>
			<script src='https://maps.google.com/maps/api/js?sensor=false&libraries=places'></script>
			<script src="js/locationpicker.jquery.js"></script>
			<script src="js/jquery.datetimepicker.full.min.js"></script>
			<script>$('#pickup_map').locationpicker({
			location: {latitude: 12.9128449, longitude: 77.63792250000006},	
			radius: 30,
			inputBinding: {
		        latitudeInput: $('#pickup_latitude'),
		        longitudeInput: $('#pickup_longitude'),
		        locationNameInput: $('#pickup_address')
		    },
		    enableAutocomplete: true
	});

			$('#drop_map').locationpicker({
				location: {latitude: 13.2044921, longitude: 77.70769070000006},	
				radius: 30,
				inputBinding: {
			        latitudeInput: $('#drop_latitude'),
			        longitudeInput: $('#drop_longitude'),
			        locationNameInput: $('#drop_address')
			    },
			    enableAutocomplete: true
				});

			jQuery('#datetime').datetimepicker();


			function populateVehicles(){
				

				var pickup_latitude = $('#pickup_latitude').val();
				var pickup_longitude = $('#pickup_longitude').val();
				var pickup_address = $('#pickup_address').val();
				
					//Ajax call to send data to the server,
					$.get("http://localhost:8080/BookMyRide/products",
									{
										pickup_latitude : pickup_latitude,
										pickup_longitude : pickup_longitude,
										pickup_address : pickup_address
									},
									function(data) {
										document.getElementById("vehicles").innerHTML = data;
									});
				
				}

			$(document).ready(function(){
			    $('#requestForm').on('submit', function(e){
			        e.preventDefault();
			        
				    var vehicle_select = $("[name='vehicle-select']");
				    var num = $( "input:checked" ).length;
					    if(vehicle_select.length == 0 || num == 0)
				        {
					        alert("Please tell us the vehicle you want to use for your ride.");
					        return;
				        }

					    
					var datetime = $('#datetime').val();
			        	if(datetime == '')
				        {
					        alert("Please tell us the pickup date and time.");
					        return;
				        } 

				        this.submit();
			    });
			});

</script>
			
		

	</body>
</html>