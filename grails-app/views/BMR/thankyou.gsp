
<html>
<head>
<title>jiffgo | FAQ</title>
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
					<g:if test="${session != null && session["current_user"] != null}">
								<ul>
										<li><a href="/">Home</a></li>
										<li><a href="/faq">FAQ</a></li>
										<li>
											<a href="#" class="icon fa-angle-down">${session["current_user"].getFirstName()}</a>
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
									</ul>
								</li>
							</g:if>
							
											<li><a href="/contactus">Contact Us</a></li>
										<li><a href="/logout" class="button">Sign Out</a></li>
								</ul>
							</g:if>
							<g:else>
								<ul>
								<li><a href="/request" class="button">Sign In</a></li>
								<li><a href="/contactus" class="button">Contact Us</a></li>
							</ul>
							</g:else>
					</nav>
				</header>




		<!-- Main -->
		<section id="main" class="container">
			<header>
				<h2>jiffgo</h2>
			</header>
			<div class="row">

				<div class="12u">

					<section class="box">
						<br /> <br />
						<h2>Thank You for contacting us</h2>
					<p> We will get back to you shortly.</p>
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


</body>
</html>