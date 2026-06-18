# Nano URL

NanoURL is URL shortening project. It takes a long URL and returns a shortened version of it which you can use to redirect 
to original URL. Built for my amusement.

## Features

- Only 6 character URLs
- Over 56.8 billion URLs possible
- MVC architecture
- RESTful API

## Prerequisites

Java 21+, Maven, MySQL, Spring Boot (4.0.6+)

## Quick Start

1. Clone this repository:
    ```bash
    git clone https://github.com/garvmathur7700/nano-url.git
    cd nano-url
    ```
   
2. Create a MySQL DB, [see the schema here](docs/database-schema.md)

3. Configure the `application.properties` file:
   ```
    spring.datasource.url=jdbc:mysql://localhost:3306/url_shortener // change DB name, if needed
    spring.datasource.username=root // replace with your username
    spring.datasource.password=root // replace with you password
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

    spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
    spring.jpa.hibernate.ddl-auto=validate // use validate for prod; update for dev
   ```

4. Run the application: `mvn spring-boot:run`

# Architecture

Currently follows a monolithic architecture. [See the architecture here](docs/architecture.md)

# To-Do (Future Plans)

- [ ] Tests
- [ ] Caching (Redis/Valkey)
- [ ] Rate limiting
- [ ] Link analytics
- [ ] In-memory buffer analytics
- [ ] Containerization
- [ ] Docker Compose
- [ ] CI/CD
- [ ] API Gateway
- [ ] Separate services
- [ ] Cloud deployment
- [ ] Link expiration
- [x] Latency
- [x] Throughput
- [x] Error rate
- [x] Swap out H2 with MySQL
- [x] Fix `SecureRandom` way of generating short URLs
