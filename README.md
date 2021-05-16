# Application to manage Users

### Running the application locally
	Use the Spring Boot Maven plugin like:
	mvn spring-boot:run

### API Documentation

	After running the application use following URLs to access swagger documentation:
	* http://localhost:8080/swagger-ui.html
	* http://localhost:8080/v2/api-docs

|Methods	|Urls				|Actions               |
|-----------|-------------------|----------------------|
|GET		|/api/v1/users		|retrieve all users    |
|POST		|/api/v1/users		|create new user       |
|GET		|/api/v1/users/:id	|retrieve a user by :id|
|PUT		|/api/v1/users/:id	|update a user by :id  |
|DELETE		|/api/v1/users/:id	|delete a user by :id  |
