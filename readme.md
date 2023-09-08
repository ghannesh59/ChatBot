Spring Boot Chat Bot

I created a simple chatbot that interacts with the user: asks questions and process responses. Based on the response, the chatbot will perform an action. The chatbot will be able to handle erroneous responses by clarifying and restating the previous question.
The chatbot possesses the capability to manage concurrent interactions with multiple users. Furthermore, it has the ability to record and store user responses in a database.

Prerequisites:  
JDK 17  
Maven  
PostgreSQL


I am hosting the database in AWS. Don't 

Using Spring Boot Maven Plugin:

Open a terminal or command prompt.

Navigate to the root directory of your Spring Boot project (where the pom.xml file is located).

Run the following command:

mvn spring-boot:run


This command will compile your project and start the Spring Boot application.

Using the Main Class:

If you prefer to use the command line, you can compile the project  by navigating to the root directory of project and run

mvn clean install

Then, navigate to the target directory created by Maven (usually target/) and run:

java -jar ChatBot.jar

After starting the Spring Boot application, we can access it in a web browser  by navigating to  http://localhost:8080 . 

