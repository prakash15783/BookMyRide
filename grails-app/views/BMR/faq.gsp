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
						<br /> <br />
						<h2>Frequently Asked Questions</h2>
						<table>
							<tr>

								<td>What does jiffgo do?</td>

							</tr>
							<tr>

								<td>jiffgo is a great tool that helps you schedule your Uber rides. No more being late for your meetings or flights just because you didn't request your ride at the right time.

To get started, simply login using your Uber account.</td>

							</tr>
							
							
							<tr>

								<td>Where is jiffgo available?</td>

							</tr>
							<tr>

								<td>Currently, jiffgo is only available in INDIA during the preview release.</td>

							</tr>
							
							
							<tr>

								<td>How do I schedule a ride?</td>

							</tr>
							<tr>

								<td>There's a simple process to schedule your Uber ride. Simply login to your dashboard and start by selecting your pickup and destination locations. You can search for addresses or click and drag the red pin on the map. Next, choose the Uber vehicle that you'd like to request and the date and time of the request.</td>

							</tr>
							
							
							<tr>

								<td>What is the "request time"?</td>

							</tr>
							<tr>

								<td>Request time is the date and time when the ride will be requested. This is not the time when the ride will pickup you up (although for high-demand areas this might be true). The timezone of the date is Indian Standard Time (IST).</td>

							</tr>
							
							<tr>

								<td>Is my ride guaranteed?</td>

							</tr>
							<tr>

								<td>Due to the "on-demand" nature of Uber, availability of rides varies from time to time. Your request will be successful if there are available vehicles in your pickup location.</td>

							</tr>
							
							
							<tr>

								<td>How far in the future can the request be for? What's the earliest request I can make?</td>

							</tr>
							<tr>

								<td>The request can be made for up to a week in advance.</td>

							</tr>
							
							
							<tr>

								<td>How does scheduling work?</td>

							</tr>
							<tr>

								<td>Your ride is requested to UBER as per the time set in the request.

When the request is made, you will receive notification from Uber and the Uber app on your mobile device will show the status of your ride.</td>

							</tr>
							
							<tr>

								<td>How do I cancel a scheduled ride?</td>

							</tr>
							<tr>

								<td>To cancel a scheduled ride, simply visit the queue page by clicking "Queue" in the top header. Scroll to the ride you want to cancel and click on the ride request id. This will open another page showing the details of the ride. You can cancel your ride by clicking on the "Cancel Request" button.</td>

							</tr>
							
							<tr>

								<td>What do these words on the queue page mean?</td>

							</tr>
							<tr>

								<td>Scheduled - The ride is scheduled and will be requested at the "Request time".
<br/>Completed - The request went through and was completed.
<br/>Cancelled - The ride was cancelled.</td>

							</tr>
							
							<tr>

								<td>How do I get notifications about my ride request?</td>

							</tr>
							<tr>

								<td>To get notified, you need to have Uber app installed and running on your smartphone. 
								<br/>Notifications about your ride will be sent by Uber. </td>

							</tr>
							
							
							
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