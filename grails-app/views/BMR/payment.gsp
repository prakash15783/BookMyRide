
<html>
	<head>
		<title>BookMyRide | Payment</title>
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
							<li><a href="/BookMyRide/faq">FAQ</a></li>
							<li>
								<a href="#" class="icon fa-angle-down">${userProfile.getFirstName()}</a>
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
						<h2>Book My Ride</h2>
					</header>
					<div class="row">
					
						 <div class="12u">

							<!-- Form -->
								<section class="box">
									<br/>
									<br/>
									<h2>Payment</h2>
									This is where the payment gateway UI will be shown..
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

	</body>
</html>