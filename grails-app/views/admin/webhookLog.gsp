
<html>
	<head>
		<title>jiffgo | History</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<!--[if lte IE 8]><script src="js/ie/html5shiv.js"></script><![endif]-->
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
									</ul>
								</li>
							</g:if>
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
									<h2>Webhooks</h2>
									<%--<form method="post" action="/admin/webhookLog" id="requestForm">

							<div class="row uniform 50%">
								<div class="6u 12u(mobilep)">
									From Date: <input name="fromdate" id="fromdate"
										type="text" value="${params['fromdate']}">
								</div>
								<div class="6u 12u(mobilep)">
									To Date: <input name="todate" id="todate"
										type="text" value="${params['todate']}">
								</div>
							</div>
							<div class="row uniform 50%">
								<div class="6u 12u(mobilep)">
									<ul class="actions">
										<li><input type="submit" value="Filter" /></li>
									</ul>
								</div>
							</div>
						</form>
									
									--%>
									
									<table><th>ResourceId</th><th>EventTime</th> <th>EventType</th> <th>ResourceType</th> <th>Status</th> 
									<g:each in="${webhookEvents}" var="webhook">
										<tr>
										<td>
										
												${webhook.getMeta().getResourceId()} 	
											</td>
											<td>
										
												${webhook.getEventTime()} 	
											</td>
											
											<td>
										
												${webhook.getEventType()} 	
											</td>
			
											<td>
												${webhook.getMeta().getResourceType()} 	
											</td>
											<td>
												${webhook.getMeta().getStatus()} 	
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
			<script src="../../js/jquery.min.js"></script>
			<script src="../../js/jquery.dropotron.min.js"></script>
			<script src="../../js/jquery.scrollgress.min.js"></script>
			<script src="../../js/skel.min.js"></script>
			<script src="../../js/util.js"></script>
			<!--[if lte IE 8]><script src="../../js/ie/respond.min.js"></script><![endif]-->
			<script src="../../js/main.js"></script>

	
	</body>
</html>