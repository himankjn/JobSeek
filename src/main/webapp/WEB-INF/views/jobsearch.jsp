<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org"
	xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
	layout:decorator="layout/template">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<head>

<meta charset="utf-8">
<meta name="author" content="">
<title>Search jobs | JobSeek</title>
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
	margin-top: 5px;
	padding-top: 20px;
	padding-bottom: 20px;
	color: #fff;
	background-color: #2E2E2E;
}


.a1{
	font-size: 24px;
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

.results {
	margin: 0px 100px 0px 100px;
	padding: 50px 100px 10px 100px;
	border-right: 1px solid #bdbdbd;
	border-left: 1px solid #bdbdbd;
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

.groups{
	line-height: 90%;
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

<body id="pagetop">

	<div class="container-fluid">

		<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="/findjobs">JobSeek</a>
				</div>
				<ul class="nav navbar-nav navbar-right">
					<li class="active"><a href="/userprofile/${seeker.jobseekerId}">Dashboard</a></li>

					<li class="dropdown"><a class="dropdown-toggle"
											data-toggle="dropdown" href="#">logged in as
						${seeker.firstName} <span class="caret"></span>
					</a>
						<ul class="dropdown-menu">
							<%--							<li><a href="/userprofile/${seeker.jobseekerId}">Profile</a></li>--%>
							<%--							<li><a href="/searchjobs?userId=${seeker.jobseekerId}">Search Jobs</a></li>--%>
							<li><a href="/findjobs">Logout</a></li>
						</ul></li>
				</ul>
			</div>
		</nav>

		<div>
			<div class="container text-center" style="margin-top: 3%">
				<img src="data:image/png;base64,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">
			</div>
		</div>

		<div class="well">
			<form class="form-inline row well" style="margin: 5px"
				action="/searchjobs" method="get">
				<div class="col-sm-12">
					<label  for="cname">Search</label> <input
						type="text" class="form-control" name="searchString"
						placeholder="Enter Search Keywords" >
				</div>
				<input type="hidden" name="userId" value="${seeker.jobseekerId}"></input>
				<div class="form-group col-sm-4">
					<label class="col-sm-12" for="cname">Company name</label> <input
						type="text" class="form-control" name="companies"
						placeholder="comma separated">
				</div>
				<div class="form-group col-sm-4">
					<label class="col-sm-12" for="location">Job locations</label> <input
						type="text" class="form-control" name="locations"
						placeholder="comma separated">
				</div>
				<div class="form-group col-sm-4">
					<label class="col-sm-12">Salary $ p.a.:</label>
					<div class="form-group col-sm-12">
						<label for="min">Min</label> <input type="text" class="form-control" id="min" value="0" name="min">
						<label for="max">Max</label> <input  type="text" class="form-control" id="max" value="1000000" name="max">
					</div>
<%--					<div class="form-group col-sm-4">--%>
<%--						--%>
<%--					</div>--%>
				</div>

				<button type="submit" class="btn btn-primary">Search</button>
			</form>
		</div>
		<div class="results">
			<h2>Search Results:</h2>
			<p>${fn:length(jobs)} search results</p>

			

			<c:forEach items="${jobs}" var="job">
				<a class="a1" href="/showjob?userId=${seeker.jobseekerId}&jobId=${job[0]}">${job[1]}</a>
				<div class="row">
					<div class="col-sm-4 groups">
						<p>
							<b>jobId:</b> ${job[0]}
						</p>
						<p>
							<b>location:</b> ${job[4]}
						</p>
						<p>
							<b>Salary:</b> $ ${job[5]}
						</p>
						
						<p>
							<b>Posted by:</b> ${job[8]}
						</p>
					</div>
					<div class="col-sm-8">
						<p>
							<b>Status:</b>
							<c:if test="${job[7] == 0}">
								<c:out value="Open" />
							</c:if>
							<c:if test="${job[7] == 1}">
								<c:out value="Filled" />
							</c:if>
							<c:if test="${job[7] == 2}">
								<c:out value="Cancelled" />
							</c:if>
						</p>
						<p>
							<b>Responsibilities :</b> ${job[3]}
						</p>
						<p>
							<b>Description:</b> ${job[2]}
						</p>
					</div>
				</div>
				<hr />
			</c:forEach>
			<!-- <p>Posted by: <c:out value="${job.company}"></c:out></p>
    <p>Status: <c:out value="${job.location}"></c:out></p>
    <p>Status: <c:out value="${job.salary}"></c:out></p>
    <p>${job.description}</p>
     -->

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
	<footer id="team" class="text-center">
		<p> &copy; 2022 JobSeek Company</p>
	</footer>
</body>

</html>