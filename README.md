##Description
This is a Java boilerplate project that provides a basic structure for building RESTful APIs with Spring

Requirements
Java 17+
Maven 3.9+
MySQL 8.x
Git

Technologies
Spring Boot (Web, Security, Data JPA)
JWT (com.auth0)
MySQL
Dotenv for environment variables


Project Structure
src/main/java/com/example/java_boilerplate/
│
├── config/          # General configurations (Beans, CORS, etc.)
├── controller/      # REST controllers
├── dto/             # Data Transfer Objects
├── exception/       # Custom exceptions
├── filter/          # Security filters (JWT Filter)
├── model/           # JPA entities
├── repository/      # Data access layer (JPA Repositories)
├── security/        # Security configuration and JWT utilities
├── service/         # Business logic layer
└── util/            # Utility classes

Environment Variables
MY_SECRET_KEY=your_secret_key
MYSQL_DATABASE=auth_db
MYSQL_USER=root
MYSQL_PASSWORD=secret

Application Properties
spring.application.name=jwt-auth-service
spring.secret=${MY_SECRET_KEY}
spring.expiration=900000
spring.filesRoute=/absolute/path/to/store/files
spring.datasource.url=jdbc:mysql://localhost:3306/${MYSQL_DATABASE}
spring.datasource.username=${MYSQL_USER}
spring.datasource.password=${MYSQL_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

Installation
git clone https://github.com/your-username/java-boilerplate.git
cd java-boilerplate
mvn clean install

Run the Application
mvn spring-boot:run


Main Endpoints
| Method | Endpoint       | Description         |
| ------ | -------------- | ------------------- |
| POST   | /auth/login    | User authentication |
| POST   | /auth/register | User registration   |
| GET    | /users         | Get all users       |
| GET    | /users/{id}    | Get user by ID      |
| PUT    | /users/{id}    | Update user         |
| DELETE | /users/{id}    | Delete user         |


Running test
mvn test

Docker
docker run --name mysql-auth \
-e MYSQL_ROOT_PASSWORD=secret \
-e MYSQL_DATABASE=auth_db \
-p 3306:3306 -d mysql:8