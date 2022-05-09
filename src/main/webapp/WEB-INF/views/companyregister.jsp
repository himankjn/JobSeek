<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta name="author" content="">
<title>Register | JobSeek</title>
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

<style type="text/css">
#requirements {
	margin-top: 10px;
	padding-top: 50px;
	padding-bottom: 50px;
	color: #fff;
	background-color: #3F729B;
}

.jumbotron {
	margin-bottom: 10px;
}
/* Full-width input fields */
input[type=text], input[type=password] {
	width: 100%;
	padding: 12px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	box-sizing: border-box;
}

/* Set a style for all buttons */
button {
	background-color: #4CAF50;
	color: white;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	cursor: pointer;
	width: 100%;
}

button:hover {
	opacity: 0.8;
}

/* Extra styles for the cancel button */
.cancelbtn {
	width: auto;
	padding: 10px 18px;
	background-color: #f44336;
}

/* Center the image and position the close button */
.imgcontainer {
	text-align: center;
	margin: 24px 0 12px 0;
	position: relative;
}

img.avatar {
	width: 10%;
	border-radius: 50%;
}

.container {
	padding: 16px;
}

span.psw {
	float: right;
	padding-top: 16px;
}

/* The Modal (background) */
.modal {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
	padding-top: 60px;
}

/* Modal Content/Box */
.modal-content {
	background-color: #fefefe;
	margin: 5% auto 15% auto;
	/* 5% from the top, 15% from the bottom and centered */
	border: 1px solid #888;
	width: 40%; /* Could be more or less, depending on screen size */
}

/* The Close Button (x) */
.close {
	position: absolute;
	right: 25px;
	top: 0;
	color: #000;
	font-size: 35px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: red;
	cursor: pointer;
}

/* Add Zoom Animation */
.animate {
	-webkit-animation: animatezoom 0.6s;
	animation: animatezoom 0.6s
}

@
-webkit-keyframes animatezoom {
	from {-webkit-transform: scale(0)
}

to {
	-webkit-transform: scale(1)
}

}
@
keyframes animatezoom {
	from {transform: scale(0)
}

to {
	transform: scale(1)
}

}

/* Change styles for span and cancel button on extra small screens */
@media screen and (max-width: 300px) {
	span.psw {
		display: block;
		float: none;
	}
	.cancelbtn {
		width: 100%;
	}
}

.row.vertical-divider {
	overflow: hidden;
}

.row.vertical-divider>div[class^="col-"] {
	text-align: center;
	padding-top: 5%;
	/*padding-bottom: 100px;*/
	margin-top: 30px;
	/*margin-bottom: -100px;*/
	border-left: 3px solid #689f38;
	border-right: 3px solid #689f38;
}

.row1 {
	overflow: hidden;
}

.row2 {
	margin-bottom: -99999px;
	padding-bottom: 99999px;
}
#team {
	margin-top: 5px;
	padding-top: 20px;
	padding-bottom: 20px;
	color: #fff;
	background-color: #2E2E2E;
}

body {
	padding-top: 20px;
	padding-bottom: 20px;
}
</style>



</head>

<body>

	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="/findjobs">JobSeek</a>
			</div>
			<ul class="nav navbar-nav navbar-right">
				<form id="dashform" method="post" action="/company/profile">
					<input type="hidden" name="id" value="${company.companyId}"></input>
				</form>
				<li class="active"><a onclick="document.getElementById('dashform').submit();">Dashboard</a></li>

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
	<div class="container-fluid">
		<div class="container">
			<div class="well" style="margin-top: 3%">
				<h1>
					<strong class="text-center"><img src="https://i.pinimg.com/236x/b0/81/6b/b0816b0277a696f873c3d5db164de0ce.jpg" height="80" width="auto">Recruiter Profile
						</strong>
				</h1>

			</div>
			<div class="col-lg-12 well">
<%--				<h2 class="text-center">Recruiter Profile</h2>--%>
<%--				<br>--%>
				<div class="row">
					<form action="/update/company" method="post">
					<input type="hidden" name="id" value="${company.companyId}"></input>

						<div class="col-sm-12">
							<div class="row">
								<div class="col-sm-6 form-group">
									<label>Company Name</label>
									
									<input type='text' name="companyName" value='${company.companyName }' readonly class='form-control'>
								</div>
								
							
							<div class="col-sm-6 form-group">
								<label>Email Address</label>
								<input type='text' readonly name="companyUser" value='${company.companyUser }' readonly class='form-control'>
							</div>
							</div>
							<div class="form-group">
								<label>Headquarter location</label>
								<textarea rows="2" name="headquarters"
									class="form-control">${company.headquarters}</textarea>
							</div>

							<div class="form-group">
								<label>Description</label>
								<textarea  name="description"
									rows="3" class="form-control"> ${company.description}</textarea>
							</div>


							<button type="submit" class="btn btn-lg btn-primary">Submit</button>
						</div>
					</form>
				</div>
			</div>
		</div>






		<footer id="team" class="text-center navbar-fixed-bottom">
			<p> &copy; 2022 JobSeek Company</p>
		</footer>

	</div>



</body>

</html>