
<html>
	<head>
		<title>jiffgo | Admin</title>
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
							<li><a href="#" class="icon fa-angle-down">Admin</a>
								<ul>
									<li><a href="/admin/history">History</a></li>
									<li><a href="/admin/rideRequestLog">Request Processing</a></li>
									<li><a href="/admin/webhookLog">Webhooks</a></li>
									<li><a href="/admin/messages">Messages</a></li>
									<li><a href="/admin/users">Users</a></li>
								</ul></li>
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

								<section class="box">
									<br/>
									<br/>
									<h2>Request History</h2>
									<table><th>Detail</th> <th>Pickup Address</th> <th>Dropoff Address</th> <th>Date Time</th> <th>Status</th>
									<g:each in="${requests}" var="request">
										<tr>
										<td>
										
											<g:link controller="RideRequest" action="show" params='[id:"${request.getId()}"]'>
												Details
											</g:link>
											</td>
											
											<td>
										
												${request.getStartAddress()} 	
											</td>
											
											<td>
										
												${request.getEndAddress()} 	
											</td>
			
											<td>
												${request.getRequestDate()} 	
											</td>
											
											<td>
												
												<g:if test="${request.getRequestStatus().getName().equals('Scheduled')}">
													<font color="blue">${request.getRequestStatus()}</font>
												</g:if>
												
												<g:elseif test="${request.getRequestStatus().getName().equals('Completed')}">
													<font color="green">${request.getRequestStatus()}</font>
												</g:elseif>
												<g:elseif test="${request.getRequestStatus().getName().equals('Cancelled')}">
													<font color="red">${request.getRequestStatus()}</font>
												</g:elseif>
												<g:else>
													${request.getRequestStatus()}
												</g:else>
												
												 	
											</td>
											
										</tr>
									</g:each>
									</table>
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
