
<html>
	<head>
		<title>jiffgo | Confirm</title>
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
					<h1><a href="/">jiffgo</a></h1>
					<nav id="nav">
						<ul>
							<li><a href="/">Home</a></li>
							<li><a href="/faq">FAQ</a></li>
							<li>
								<a href="#" class="icon fa-angle-down">${userProfile.getFirstName()}</a>
								<ul>
									<li><a href="/request">New Ride</a></li>
									<li><a href="/queue">Queue</a></li>
									<li><a href="/history">History</a></li>
								</ul>
							</li>
							
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
						</ul>
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
									<br/>
									<br/>
									<h2>Confirm Ride</h2>
									<form method="post" action="/submitrequest">
										
										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
											<b>Pickup Address:</b> ${params['pickup_address']}	
											</div>
										</div>
										
										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
												<b>Pickup Map:</b> <div id="pickup_map" style="width: 99%; height: 300px;"></div>
											</div>
										</div>
														
										<input type="hidden" name="pickup_latitude" id="pickup_latitude" value="${params['pickup_latitude']}"/>
										<input type="hidden" name="pickup_longitude" id="pickup_longitude" value="${params['pickup_longitude']}"/>
										<input type="hidden" name="pickup_address" id="pickup_address" value="${params['pickup_address']}"/>

										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
												<b>Dropoff Address:</b> ${params['drop_address']}
											</div>
										</div>
										
										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
												<b>Dropoff Map:</b> <div id="drop_map" style="width: 99%; height: 300px;"></div>
											</div>
										</div>
														
										<input type="hidden" name="drop_latitude" id="drop_latitude" value="${params['drop_latitude']}"/>
										<input type="hidden" name="drop_longitude" id="drop_longitude" value="${params['drop_longitude']}"/>
										<input type="hidden" name="drop_address" id="drop_address" value="${params['drop_address']}"/>
										
										<input type="hidden" name="vehicle-select" id="vehicle-select" value="${params['vehicle-select']}">
										<input type="hidden" name="datetime" id="datetime" value="${params['datetime']}">
										<br/>
										
										
										
										<div id="vehicle"></div>
										
										
										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
												<b>Request Time:</b> ${params['datetime']}
											</div>
										</div>
										
													
										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
												<ul class="actions">
													<li><input type="submit" value="Submit Request" /></li>
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
			location: {latitude: $('#pickup_latitude').val(), longitude: $('#pickup_longitude').val()},	
			radius: 30,
			scrollwheel: false,
			draggable: false
	});

			$('#drop_map').locationpicker({
				location: {latitude: $('#drop_latitude').val(), longitude: $('#drop_longitude').val()},	
				radius: 30,
				scrollwheel: false,
				draggable: false
				});

			
	function populateVehicleDetail(){
		
				var product_id = $('#vehicle-select').val();
					//Ajax call to send data to the server,
					$.get("https://www.jiffgo.com/product",
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