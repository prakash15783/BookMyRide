
<html>
	<head>
		<title>jiffgo | Queue</title>
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
									<g:if test="${requests.size()>0}">	
									<h2>Request Queue</h2>
									<table><th>Detail</th> <th>Pickup Address</th> <th>Dropoff Address</th> <th>Date Time</th>
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
											
										</tr>
									</g:each>
									</table>
									</g:if>
									<g:else>
										<h2>There are no requests in the queue.</h2>
									</g:else>
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