# Company CRUD Application

A simple Company Management application built with:
- **Java 21**
- **Spring Boot**
- **PostgreSQL**
- **MapStruct**
- **Docker**

## Features
- Manage companies, departments, teams, projects, and managers.
- Full REST API with OpenAPI documentation.
- Integration and unit tests.

## Getting Started

### Prerequisites
- Docker
- Docker Compose

### Running the Application
1. Build and run the application using Docker Compose:
   ```bash
   docker-compose up --build

---------------------------------
Access the application:
API: http://localhost:8081

Swagger UI: http://localhost:8081/swagger-ui.html

Database:
Host: localhost
Port: 5433
User: postgres
Password: postgres
