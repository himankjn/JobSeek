## Spring Job Portal

Job portal for job seekers and companies, developed in Spring and Hibernate.

### Installing and Running the application

* Perform "mvn clean install" for downloading dependencies and generating target folder.
* "docker build -t himankjn/jobportal ."
	for building jobportal image containing backend and front end.
* "docker push himankjn/jobportal:latest"  to push image to dockerhub.


Note: You can use jenkins pipeline script to run above steps for automation

* run docker-compose.yml to run mysql-jobportal(db) and jobportal(backend and frontend) images in the same network.

* Type http://localhost:8095/findjobs in your browser to open the application.
* project email : org.jobseek@gmail.com
* project password: Password@123
* Demo recruiters email id: amazon369123@gmail.com, flipkart369123@gmail.com
* Password for all the dummy recruiters mail: Password@123
