<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta name="author" content="">
<title>Register | FindJobs.com</title>
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

#team {
	margin-top: 10px;
	padding-top: 50px;
	padding-bottom: 50px;
	color: #fff;
	background-color: #2E2E2E;
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
				<a class="navbar-brand" href="/findjobs">FindJobs.com</a>
			</div>
		</div>
	</nav>
	<div class="container-fluid">
		<div class="container">
			<div class="well">
				<h1>Complete Registration</h1>

			</div>
			<div class="col-lg-12 well" style="box-shadow: 0 0 20px black">
				<h4 class="text-center">Job seeker registration</h4>
				<br>
				<div class="row">
					<form action="/update" method="post">

						<input type="hidden" name="id" value="${seeker.jobseekerId}"></input>
						<input type="hidden" name="password" value="123"></input>


						<div class="col-sm-12">
							<div class="row">
								<div class="col-sm-6 form-group">
									<label>First Name</label> <input type='text' name="firstname"
										value='${ seeker.firstName} ' readonly class='form-control'>
								</div>
								<div class="col-sm-6 form-group">
									<label>Last Name</label> <input type='text' name="lastname"
										value='${seeker.lastName }' readonly class='form-control'>
								</div>
							</div>
							<div class="form-group">
								<label>Email Address</label> <input type='text' name="emailid"
									value='${seeker.emailId }' readonly class='form-control'>
							</div>

							<div class="row">
								<div class="col-sm-6 form-group">
									<label>Work experience (in years)</label> <input type="text"
										name="workex" placeholder="Enter number of years Here.."
										value="${seeker.workEx }" class="form-control">
								</div>
								<div class="col-sm-6 dropdown form-group">
									<div>
										<label>Highest education</label> <label
											class="btn btn-default"><input type="radio"
											name="highesteducation" id="seeker" value="0"
											autocomplete="off"
											<c:if test="${seeker.highestEducation == 0}">	
												<c:out value="checked" />
											</c:if>>
											Master's degree </label> 
											<label class="btn btn-default"> <input
											type="radio" name="highesteducation" id="recruiter" value="1"
											autocomplete="off" <c:if test="${seeker.highestEducation == 1}">	
												<c:out value="checked" />
											</c:if>> Bachelor's degree
										</label>
										<label class="btn btn-default"> <input
											type="radio" name="highesteducation" id="recruiter" value="2"
											autocomplete="off" <c:if test="${seeker.highestEducation == 2}">	
												<c:out value="checked" />
											</c:if>> Ph.D.
										</label>

									</div>



								</div>

							</div>
							<div class="form-group">
								<label>Skills (comma separated)</label>
								<textarea rows="3" name="skills" class="form-control"
									>${seeker.skills }</textarea>
							</div>


							<button type="submit" class="btn btn-lg btn-primary">Submit</button>
						</div>
					</form>
				</div>
			</div>
		</div>






		<div id="team" class="container-fluid text-center">
			<h1>Team:</h1>
			<p>Amay</p>
			<p>Ashay</p>
			<p>Avdeep</p>
			<p>Surendra</p>
			<p>Surendra</p>
		</div>

	</div>



</body>

</html>