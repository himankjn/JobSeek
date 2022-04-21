<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta name="author" content="">
<title>JobSeek</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
	#team {
		margin-top: 5px;
		padding-top: 20px;
		padding-bottom: 20px;
		color: #fff;
		background-color: #2E2E2E;
	}
</style>
</head>

<body id="pagetop">

	<div class="container-fluid">

		<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="/findjobs">JobSeek</a>
				</div>
				<ul class="nav navbar-nav navbar-right">
					<li class="active"><a href="/company/profile/${company.companyId}">Dashboard</a></li>

					<li class="dropdown"><a class="dropdown-toggle"
											data-toggle="dropdown" href="#">logged in as
						${company.companyName} <span class="caret"></span>
					</a>
						<ul class="dropdown-menu">
							<%--								<li><a href="/company/profile/${company.companyId}">Profile</a></li>--%>
							<li><a href="/findjobs">Logout</a></li>
						</ul></li>
				</ul>
			</div>
		</nav>

		<div class="jumbotron">
			<div class="container text-center">

<%--				<h2>--%>
<%--					<img src="http://qspear.com/admin/images/news_events_images/partner_news.png" height="80" width="auto">FindJobs.com--%>
<%--				</h2>--%>
				<h2>Message</h2>

				<h1>${message}</h1>
			</div>
		</div>
		


	</div>

	<script>
		// Get the modal
		var modal = document.getElementById('id01');

		// When the user clicks anywhere outside of the modal, close it
		window.onclick = function(event) {
			if (event.target == modal) {
				modal.style.display = "none";
			}
		}
	</script>
	<footer id="team" class="text-center navbar-fixed-bottom">
		<p> &copy; 2022 JobSeek Company</p>
	</footer>
</body>

</html>