 ----------------
|Design Folder: |
 ----------------
The design folder contains Class Diagrams of Java Classes and Generated Java Classes from Schema.


 ----------------

|JavaDoc Folder |
 ----------------
This folder contains generated Java Documentation.


 ----------------

|Target Folder: |
 ----------------
This folder is used by maven script to auto generate the java files and performing build tasks.

	
----------------

|Additional Features: |
 ----------------
The project is compatible with Eclipse and it can be either run using Maven command prompt and also from Eclipse with few changes for eclipse project.
The project is compatible with Gradle. 

 ----------
|How to run Using Maven from Command Prompt|
 ----------
 Minimum requirement:
	 -Maven should be installed and working properly.
	 -Java version used 1.8.0_201
	 
Please extract the zip file to appropriate folder. For Example: It is extracted at C:\

Please goto command line run appropriate command as required:
cd BankBalanceDispensing

Maven Command to build & run the application. 
------------------
mvn clean install

The maven clears the \BankBalanceDispensing\target directory and regenerates compiled java class files inside folder \BankBalanceDispensing\target
It runs the Junit Test Case and prints the test result.


 ----------
|How to run using Eclipse|
 ----------
  Minimum requirement:
	 -Maven should be installed and working properly inside eclipse.
	 -Java version used 1.8.0_201

 Open Eclipse -> File -> Import -> Import existing Maven project into workspace -> Select the \BankBalanceDispensing file -> Finish
 
 please run following command:
 mvn clean install.
 
  
 Right click on project -> Run as Spring Boot App
 
 
 
 
