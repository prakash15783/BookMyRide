
<html>
<head>
<title>jiffgo | Request</title>
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
			<h1>
				<a href="/">jiffgo</a>
			</h1>
			<nav id="nav">
				<ul>
					<li><a href="/">Home</a></li>
					<li><a href="/faq">FAQ</a></li>
					<li><a href="#" class="icon fa-angle-down">
							${session["current_user"].getFirstName()}
					</a>
						<ul>
							<li><a href="/request">New Ride</a></li>
							<li><a href="/queue">Queue</a></li>
							<li><a href="/history">History</a></li>
						</ul></li>
					<g:if test="${session["current_user"].getAdmin() == true}">
									<li>
									<a href="#" class="icon fa-angle-down">Admin</a>
									<ul>
										<li><a href="/admin/history">History</a></li>
										<li><a href="/admin/rideRequestLog">Request Processing</a></li>
										<li><a href="/admin/webhookLog">Webhooks</a></li>
										<li><a href="/admin/messages">Messages</a></li>
										<li><a href="/admin/users">Users</a></li>
									</ul>
								</li>
							</g:if>
							<li><a href="/contactus">Contact Us</a></li>
							<li><a href="/logout" class="button">Sign Out</a></li>
						
			</nav>
		</header>

		<!-- Main -->
		<section id="main" class="container">
			<header>
				<h2>jiffgo</h2>
			</header>
			<div class="row">

				<div class="12u">

					<!-- Form -->
					<section class="box">
						<br /> <br />
						<h2>Request A Ride</h2>
						<form method="post" action="/confirm" id="requestForm">

							<div class="row uniform 50%">
								<div class="6u 12u(mobilep)">
									Pickup Address: <input type="text" name="pickup_address"
										id="pickup_address" value="${params['pickup_address']}"
										placeholder="Pickup Address" /> 
								</div>
							</div>


							<div class="row uniform 50%">
								<div class="6u 12u(mobilep)">
									Pickup Map:
									<div id="pickup_map" style="width: 99%; height: 300px;"></div>
								</div>
							</div>

							<input type="hidden" name="pickup_latitude" id="pickup_latitude"
								value="${params['pickup_latitude']}" /> <input type="hidden"
								name="pickup_longitude" id="pickup_longitude"
								value="${params['pickup_longitude']}" />

							<div class="row uniform 50%">
								<div class="6u 12u(mobilep)">
									Dropoff Address: <input type="text" name="drop_address"
										id="drop_address" value="${params['drop_address']}"
										placeholder="Dropoff Address" />
								</div>
							</div>

							<div class="row uniform 50%">
								<div class="6u 12u(mobilep)">
									Dropoff Map:
									<div id="drop_map" style="width: 99%; height: 300px;"></div>
								</div>
							</div>

							<input type="hidden" name="drop_latitude" id="drop_latitude"
								value="${params['drop_latitude']}" /> <input type="hidden"
								name="drop_longitude" id="drop_longitude"
								value="${params['drop_longitude']}" />

							<div class="row uniform 50%">
								<div class="6u 12u(mobilep)">
									<a class="button special icon fa-search"
										onclick="populateVehicles();">Find Vehicles</a>
								</div>
							</div>
							<br />
							<div id="vehicles"></div>


							<div class="row uniform 50%">
								<div class="6u 12u(mobilep)">
									Pickup Date and Time: <input name=datetime id="datetime"
										type="text" value="${params['datetime']}" readonly>
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
	<script
		src='https://maps.google.com/maps/api/js?sensor=false&libraries=places'></script>
	<script src="js/locationpicker.jquery.js"></script>
	<script src="js/jquery.datetimepicker.full.min.js"></script>
	<script>

			findCurrentLocationOnMap();
			
			$('#drop_map').locationpicker({
				location: {latitude: 13.2044921, longitude: 77.70769070000006},	
				radius: 30,
				scrollwheel: false,
				draggable: true,
				inputBinding: {
			        latitudeInput: $('#drop_latitude'),
			        longitudeInput: $('#drop_longitude'),
			        locationNameInput: $('#drop_address')
			    },
			    enableAutocomplete: true
				});

			jQuery('#datetime').datetimepicker({
				minDate: 0,
				maxDate: '+1970/01/07',
				step: 5
				});


			function populateVehicles(){
				var pickup_latitude = $('#pickup_latitude').val();
				var pickup_longitude = $('#pickup_longitude').val();
				var pickup_address = $('#pickup_address').val();
				
					//Ajax call to send data to the server,
					$.get("https://www.jiffgo.com/products",
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



			function findCurrentLocationOnMap() {
				  var startPos;
				  var geoSuccess = function(position) {
				    startPos = position;

				    $('#pickup_map').locationpicker({
						location: {latitude: startPos.coords.latitude, longitude: startPos.coords.longitude},	
						radius: 30,
						scrollwheel: false,
						draggable: true,
						inputBinding: {
					        latitudeInput: $('#pickup_latitude'),
					        longitudeInput: $('#pickup_longitude'),
					        locationNameInput: $('#pickup_address')
					    },
					    enableAutocomplete: true
						});
			    
				  };

				  function geoRejection(msg) {
					    switch(msg.code) {
					        case msg.PERMISSION_DENIED:
					        	$('#pickup_map').locationpicker({
					    			location: {latitude: 12.9128449, longitude: 77.63792250000006},	
					    			radius: 30,
					    			scrollwheel: false,
									draggable: true,
					    			inputBinding: {
					    		        latitudeInput: $('#pickup_latitude'),
					    		        longitudeInput: $('#pickup_longitude'),
					    		        locationNameInput: $('#pickup_address')
					    		    },
					    		    enableAutocomplete: true
					    		});
					    		
					            break;
					    }
					}
				  if (navigator.geolocation) {
				  navigator.geolocation.getCurrentPosition(geoSuccess, geoRejection,  {enableHighAccuracy: true
			              ,timeout : 5000});
				  }
				  else{
					  $('#pickup_map').locationpicker({
			    			location: {latitude: 12.9128449, longitude: 77.63792250000006},	
			    			radius: 30,
			    			scrollwheel: false,
							draggable: true,
			    			inputBinding: {
			    		        latitudeInput: $('#pickup_latitude'),
			    		        longitudeInput: $('#pickup_longitude'),
			    		        locationNameInput: $('#pickup_address')
			    		    },
			    		    enableAutocomplete: true
			    		});
					  }
				};

</script>



</body>
</html>
