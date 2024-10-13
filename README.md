<h1>Trade Reporting Engine</h1>

<h2>Description:</h2>

This project is a Spring Boot-based Trade Reporting Engine. It uses JPA for data persistence and provides an API to filter and retrieve transaction data.

<h2>Prerequisites:</h2>

Java 17 or above Installed<br/>
Apache Maven 3.9.9 installed<br/>
<h2>How to Run the Project</h2>

After cloning the repository, follow these steps to build and run the project:

Build the Project:
``` bash
mvn clean package
```
Navigate to the target directory:
``` bash
cd target
```
Run the JAR file:
``` bash
java -jar TradeReportingEngine-0.0.1-SNAPSHOT.jar.original
```
<h2>Triggering the API</h2><br/>

Once the application is running, you can access the filtered transactions API at the following URL:

```bash
http://localhost:8080/api/transactions/filtered
```
Example Request

Use curl or a browser to trigger the API:

``` bash
curl -X GET http://localhost:8080/api/transactions/filtered
```
Technologies Used

OpenJDK Runtime Environment (build 21.0.1+12-29)<br/>
Spring Boot<br/>
Spring Data JPA<br/>
H2 Database (for in-memory testing)<br/>
Maven<br/>
