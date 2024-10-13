Trade Reporting Engine

Description

This project is a Spring Boot-based Trade Reporting Engine. It uses JPA for data persistence and provides an API to filter and retrieve transaction data.

Prerequisites

Java Installed
Maven installed
How to Run the Project

After cloning the repository, follow these steps to build and run the project:

Build the Project:
bash
Copy code
mvn clean package
Navigate to the target directory:
bash
Copy code
cd target
Run the JAR file:
bash
Copy code
java -jar TradeReportingEngine-0.0.1-SNAPSHOT.jar.original
Triggering the API

Once the application is running, you can access the filtered transactions API at the following URL:

bash
Copy code
http://localhost:8080/api/transactions/filtered
Example Request

Use curl or a browser to trigger the API:

bash
Copy code
curl -X GET http://localhost:8080/api/transactions/filtered
Technologies Used

Java 8+
Spring Boot
Spring Data JPA
H2 Database (for in-memory testing)
Maven
