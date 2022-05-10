FROM openjdk:8
ADD ./target/jobboard-0.0.1-SNAPSHOT.war ./jobportal.war
ENTRYPOINT ["java","-jar","jobportal.war"]
EXPOSE 8095
