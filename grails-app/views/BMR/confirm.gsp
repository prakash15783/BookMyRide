
<html>
	<head>
		<title>BookMyRide | Request</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<!--[if lte IE 8]><script src="js/ie/html5shiv.js"></script><![endif]-->
		<link rel="stylesheet" href="css/main.css" />
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
									<li><a href="generic.html">New Ride</a></li>
									<li><a href="contact.html">Queue</a></li>
									<li><a href="elements.html">History</a></li>
									
								</ul>
							</li>
							<li><a href="/BookMyRide/logout" class="button">Sign Out</a></li>
						</ul>
					</nav>
				</header>

			<!-- Main -->
				<section id="main" class="container">
					<header>
						<h2>Request A Ride</h2>
					</header>
						 <div class="12u">

							<!-- Form -->
								<section class="box">
									<br/>
									<br/>
									<h2>Request A Ride</h2>
									<form method="post" action="/BookMyRide/show">
										
										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
											Pickup Address: <input type="text" id="pickup_address" value="" placeholder="Pickup Address"/>	
											</div>
										</div>
										
										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
												Pickup Map: <div id="pickup_map" style="width: 400px; height: 300px;"></div>
											</div>
										</div>
														
										<input type="hidden" id="pickup_latitude"/>
										<input type="hidden" id="pickup_longitude"/>

										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
												Dropoff Address: <input type="text" id="drop_address" value="" placeholder="Dropoff Address"/>
											</div>
										</div>
										
										<div class="row uniform 50%">
											<div class="6u 12u(mobilep)">
												Dropoff Map: <div id="drop_map" style="width: 400px; height: 300px;"></div>
											</div>
										</div>
														
										<input type="hidden" id="drop_latitude"/>
										<input type="hidden" id="drop_longitude"/>
										
													
										<div class="row uniform">
											<div class="12u">
												<ul class="actions">
													<li><input type="submit" value="Submit Request" /></li>
												</ul>
											</div>
										</div>
									</form>
								</section>
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
			<script src='http://maps.google.com/maps/api/js?sensor=false&libraries=places'></script>
			<script src="js/locationpicker.jquery.js"></script>
			<script>$('#pickup_map').locationpicker({
			location: {latitude: 12.9128449, longitude: 77.63792250000006},	
			radius: 30,
			inputBinding: {
		        latitudeInput: $('#pickup_latitude'),
		        longitudeInput: $('#pickup_lonitude'),
		        locationNameInput: $('#pickup_address')
		    },
		    enableAutocomplete: true
	});

			$('#drop_map').locationpicker({
				location: {latitude: 13.2044921, longitude: 77.70769070000006},	
				radius: 30,
				inputBinding: {
			        latitudeInput: $('#drop_latitude'),
			        longitudeInput: $('#drop_lonitude'),
			        locationNameInput: $('#drop_address')
			    },
			    enableAutocomplete: true
				});

</script>
			
		

	</body>
</html>