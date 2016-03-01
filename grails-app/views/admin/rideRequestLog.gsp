
<html>
	<head>
		<title>jiffgo | RidRequestLog</title>
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

								<section class="box">
									<br/>
									<br/>
									<h2>Ride Request Log</h2>
									
									<form method="post" action="/admin/rideRequestLog" id="requestForm">

							<div class="row uniform 50%">
								<div class="4u 12u(mobilep)">
									From Date: <input name="fromdate" id="fromdate"
										type="text" value="${params['fromdate']}">
								</div>
								<div class="4u 12u(mobilep)">
									To Date: <input name="todate" id="todate"
										type="text" value="${params['todate']}">
								</div>
								<div class="4u 12u(mobilep)">
									<br/>
										<input type="submit" value="Filter" />
									
								</div>
							</div>
							
						</form>
									<div style="overflow-x:scroll">
									<div class="table-wrapper">
									<table class="alt"><th>RequestId</th><th>UberRequestId</th> <th>StartTime</th> <th>EndTime</th><th>Status</th>
									<th>Requester</th><th>Details</th>
									<g:each in="${requests}" var="rideRequest">
										<tr>
										<td>
										
											<g:link controller="RideRequest" action="show" params='[id:"${rideRequest.getRideRequest().getId()}"]'>
												${rideRequest.getRideRequest().getRequestId()} 	
											</g:link>
											</td>
											
											<td>
										
												${rideRequest.getRideRequest().getUberRequestId()} 	
											</td>
											
											<td>
										
												${rideRequest.getStartTime()} 	
											</td>
											
											<td>
										
												${rideRequest.getEndTime()} 	
											</td>
			
									
											<td>
												
												<g:if test="${rideRequest.getRideRequest().getRequestStatus().getName().equalsIgnoreCase('Scheduled')}">
													<font color="blue">${rideRequest.getRideRequest().getStatus()}</font>
												</g:if>
												
												<g:elseif test="${rideRequest.getRideRequest().getRequestStatus().getName().equalsIgnoreCase('Completed')}">
													<font color="green">${rideRequest.getRideRequest().getStatus()}</font>
												</g:elseif>
												<g:elseif test="${rideRequest.getRideRequest().getRequestStatus().getName().equalsIgnoreCase('Cancelled')}">
													<font color="red">${rideRequest.getRideRequest().getStatus()}</font>
												</g:elseif>
												<g:else>
													${rideRequest.getRideRequest().getStatus()}
												</g:else>
												
												 	
											</td>
											<td>
												${rideRequest.getRideRequest().getRequester().getName()} 	
											</td>
											<td>
												${rideRequest.getDetails()} 	
											</td>
											
										</tr>
									</g:each>
									</table>
									</div>
									</div>
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
<script src="../../js/jquery.datetimepicker.full.min.js"></script>
			<script>
			jQuery('#fromdate').datetimepicker({
				 timepicker:false,
				  format:'Y/m/d'
					
				});
			
			jQuery('#todate').datetimepicker({
				  timepicker:false,
				  format:'Y/m/d',
				});

			
			

</script>
	
	</body>
</html>
