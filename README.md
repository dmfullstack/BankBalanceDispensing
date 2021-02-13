# Bank Balance and Dispensing System
@Developer: Dinesh Metkari dineshmetkari@gmail.com

This is java application based on given requirment specifications. 

#Technology stack
Java 1.8
Maven
H2 Database
Spring Framework
Rest API Design
Microservices 


#Running the Application using Maven

mvn clean install

mvn spring-boot:run

Open the browser and go to url: http://localhost:8080



# Additional Features: Gradle integration

Gradle compatible, added gradle feature. 
./gradlew clean build bootRun

Open the browser and go to url: http://localhost:8080

# Additional Features: Docker integration
Simply run below script to run the application from docker:

docker run -p 8080:8080 dineshmetkari/bankbalancedispensing:1.0

Open the browser and go to url: http://localhost:8080

# Additional Features: Junit Tests integration
run tests using command:
mvn test

# Additional Features: Design Documents
Class Diagrams in .\Design folder


# Additional Features: Java Documentation

Java Documentation available in folder: .\JavaDoc


# Additional Features: Can be implemented:
- Integration with Jenkins for CI CD Pipiline
- Integration with SONARQUBE for static code analysis
- Deployment on AWS using Kubernetes



