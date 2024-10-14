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
<h2>Triggering the GET API with default filter conditions</h2><br/>

Once the application is running, you can access the filtered transactions API at the following URL:

```bash
http://localhost:8080/api/transactions/filtered
```
Example Request

Use curl or a browser to trigger the API:

``` bash
curl -X GET http://localhost:8080/api/transactions/filtered
```
<h2>Triggering the POST API with custom filter conditions</h2><br/>

You can customize the filter conditions by triggering API at the following URL:

```bash
 http://localhost:8080/api/transactions/customFiltered
```
Example RequestPayload
```json
[
    {
        "fieldName": "premiumAmount",
        "value":      600.0000,
        "comparisonType": "NOT_EQUALS"
    },
    {
        "fieldName": "premiumAmount",
        "value":      500.0000,
        "comparisonType": "NOT_EQUALS"
    }
]
```
<h3>JSON Field Validations</h3><br/>
Acceptable values for fieldName are buyer_party,seller_party,premium_amount,premium_currency. <br/>
Acceptable values for comparisonType are EQUALS,NOT_EQUALS<br/>
<h3>Technologies Used</h3>

OpenJDK Runtime Environment (build 21.0.1+12-29)<br/>
Spring Boot<br/>
Spring Data JPA<br/>
H2 Database (for in-memory testing)<br/>
Maven<br/>

<h3>Future Enhancements</h3>
Containerization<br/>
Apache Kafka
