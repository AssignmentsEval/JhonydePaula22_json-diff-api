WAES Test - Assignment Scalable Web
==============

#### Small context

It is a REST API responsible for storing and comparing Base64 encoded JSON.

#### API Instructions

##### Store JsonRecord Data
To store data you must provide an encoded Base64 JSON string in the requestBody

>POST: http://localhost:8080/v1/diff/{id}/{side}

##### Get JsonRecord Diff
To get the differences between the JSONs, both sides (left and right) must be present under the same ID
>GET http://localhost:8080/v1/diff/{ID}

For more detailed info access the [Swagger](http://localhost:8080/swagger-ui.html) after running the project.

It is also possible to generate the last version of Javadoc. Just run the command below and the java doc will be available on the [target folder](target/site/apidocs/index.html)
> mvn javadoc:javadoc

#### Languages / Frameworks / Tools

- Java 11 (Language)
- Swagger Code Gen (Open API - API First)
- Spring Boot (Framework)
- H2 (Database)
- Flyway (Database Migration)
- Swagger (Documentation)
- JUnit (Tests)
- Jacoco (Code Coverage)
- Rest Assured (Integration Tests)

#### Requirements

- JDK Java 11
- Maven 3.6

# Running the project

#### Compiling and Running the project

 On the project's folder, run the command below to compile and start the application.
 > mvn clean spring-boot:run
 
 The command will also start the H2 in-memory database and create the json_record schema.
 
#### Swagger UI

 You can check the swagger on the endpoint below.
- [Swagger](http://localhost:8080/swagger-ui.html)

 The contract of the api, is on the file [swagger.yml](/src/main/resources/swagger.yaml)

#### H2 Console

 After starting the application the H2 Console will be available on the endpoint bellow, using the data source url "jdbc:h2:mem:waestestdb"
- [Database console](http://localhost:8080/h2-console/) 

#### Running Tests

To run the unit and integration tests, just run the command below. 
The Jacoco report will be generated and available on the [target folder](target/site/jacoco/index.html)
> mvn test

# Project Info

I have used Spring Boot to build this REST API.
I have decided to divide the project into three layers of responsibility. 
Following the KISS principle, I have tried to keep the project as simple as possible.
I also tried to follow the clean code principles, which I in my opinion makes it very easy for other developers to understand my code, and also to increase the maintainability of the code.  
We have three layers in this project:
* The REST Controller layer - Responsible for receiving the requests and handing the data to the business layer to process it.
* The Business layer (Services) - The application logic is stored in this layer, which will process the data and respond to the clients. It is also responsible for calling the Persistency layer.
* The Persistency layer (Repositories)- All the data stored is handled by this layer, making usage of the spring data JPA 

#### Decisions Made

1. H2 Database - Trying to keep it very simple, I added this in-memory database. If necessary it would be simple to migrate to another database of choice.
2. Flyway - Simple setup and very friendly to use while versioning and migrating databases.
3. Rest Assured - Easy way to implement integration tests.
4. Jacoco - Easy way to verify how much of the code is covered by unit/integration tests.
5. Swagger Code Gen - The API first strategy is being used a lot among the developers, and it avoids having to create tons of code.
6. Comments - I think that a well-written code must not have comments unless it is not that simple to explain. It must be understandable by itself. With that in mind, I did not add too many comments over the code while coding this assignment, except by the JavaDoc comments.
7. Swagger - There is no need for further explanation.
8. Exclusions on coverage - I have excluded the JsonDiffApiApplication, Auto-Generated Models (Simple POJOs), and all the Constants files from Jacoco coverage, since those classes are not meant to be tested. Anyway, JsonDiffApiApplication is tested when we set up the Spring Boot context while running the integration tests.  


#### Improvement Suggestions

1. Return the actual difference between the JSONS
2. Add circuit-breaker to increase the application resilience (Could make usage of the resilience4j). I would add that while trying to request the database. If for some reason the database is out of service, we would give it a proper time to recover without keep sending tons of additional requests.
3. Add Cache (local or shared if it is in a cloud scalable environment) to avoid requests that have already been made and had no changes so far to keep hitting the database. Of course, we would have to add some mechanism to handle the cache invalidation whenever a new change was made on the JsonRecord.
4. Add a file to handle message properties in case of need for internationalization. As it is not needed right now, I left the messages in some constant files. 