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

<!-- <link rel="stylesheet" href="css/style.css">
   
<script src="js/script.js"></script>-->
<style type="text/css">
#requirements {
	margin-top: 10px;
	padding-top: 50px;
	padding-bottom: 50px;
	color: #fff;
	background-color: #3F729B;
}

#team {
	margin-top: 5px;
	padding-top: 20px;
	padding-bottom: 20px;
	color: #fff;
	background-color: #2E2E2E;
}

.jumbotron {
	margin-bottom: 1px;
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
	border-left: 3px solid #B6FFCE;
	border-right: 3px solid #B6FFCE;
}

.row1 {
	overflow: hidden;
}

.row2 {
	margin-bottom: -99999px;
	padding-bottom: 99999px;
}

body {
	padding-top: 20px;
	/*padding-bottom: 10px;*/
}
</style>

</head>

<body id="pagetop">

	<div class="container-fluid">

		<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="#pagetop">JobSeek</a>
				</div>
				<ul class="nav navbar-nav navbar-right">
					<li class="active"><a href="#" onclick="document.getElementById('id01').style.display='block'">Login</a></li>
					<li class="active"><a href="/register">New User? Register</a></li>
				</ul>
			</div>
		</nav>

		<div class="container-fluid" style="margin-top: 40px">${message2}</div>

		<div class="jumbotron text-center">

			<img src="https://www.signitysolutions.com/images/services/job-portal/banner.png">
		</div>
		<div class="row picture row1">
			<img src=http://s3.amazonaws.com/fjwp/blog/wp-content/uploads/2016/03/04034507/Job-Searching-Online-8-Best-Practices-You-Need-to-Know.jpg class="img-responsive col-sm-6 cols"
				alt="Cinque Terre">
<%--			<img src="https://www.naylor.com/wp-content/uploads/2017/07/careers_career-fairs.gif" class="img-responsive col-sm-6 cols" width="50%">--%>
			<div class=" row vertical-divider col-sm-6 text-center cols row1 row2 well" style="background-color: #B6FFCE;">
				<h1>Start now</h1>
				<div class="col-xs-6"	>
					<h4><strong>Returning users</strong></h4>
					<button class="btn btn-primary btn-block"
						onclick="document.getElementById('id01').style.display='block'">Login</button>
				</div>
				<div class="col-xs-6">
					<h4><strong>New users</strong></h4>
					<form action="/register" method="get">
					<button type="submit" class="btn btn-danger btn-block">Register</button>
						</form>
				</div>


				<div id="id01" class="modal">

					<form class="modal-content animate" action="/login" method="post">
						<div class="imgcontainer">
							<span
								onclick="document.getElementById('id01').style.display='none'"
								class="close" title="Close Modal">&times;</span> <img
								src="https://cdn2.iconfinder.com/data/icons/website-icons/512/User_Avatar-512.png" alt="Avatar" class="avatar">
						</div>
						<div class="btn-group btn-group-justified" data-toggle="buttons">
								<label class="btn btn-default active"> <input
									type="radio" name="type" id="seeker" value="seeker"
									autocomplete="off" checked> Job Seeker
								</label> <label class="btn btn-default"> <input type="radio"
									name="type" id="recruiter" value="recruiter"
									autocomplete="off"> Recruiter
								</label>

							</div>

						<div class="container-fluid">
							<label><b>Email</b></label> <input type="text"
								placeholder="Enter Email" name="emailId" required> <label><b>Password</b></label>
							<input type="password" placeholder="Enter Password" name="password"
								required>

							<button type="submit">Login</button>
						</div>

					</form>
				</div>

			</div>
		</div>

	</div>
	<footer id="team" class="text-center navbar-fixed-bottom">
		<p> &copy; 2022 JobSeek Company</p>
	</footer>

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

</body>

</html>