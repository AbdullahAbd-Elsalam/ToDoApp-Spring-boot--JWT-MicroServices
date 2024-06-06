# ToDo App with Microservices Architecture

## Overview
- This project is a ToDo App built using a microservices architecture.
- It consists of two microservices: User Service and ToDo Service.
- Utilizes various Spring frameworks and other technologies to provide robust functionality, security, and documentation.

## Technologies Used
- Spring Framework: Core, MVC, Boot, REST, Security, Data JPA
- JWT: For authentication and authorization
- Java Mail: For sending OTPs for account verification
- MySQL: Database for storing user and to-do information
- Swagger: For API documentation
- JUnit: For testing APIs
- Global Exception Handling: For managing exceptions

## Microservices
### User Service
- Functionality: Manages user authentication and authorization using Spring Security and JWT. Handles account verification via OTP sent to the user's email using Java Mail.
- Database: Connects to UserDB (MySQL)

### ToDo Service
- Functionality: Allows users to add, update, delete, and search for to-do items using a valid token for authentication.
- Database: Connects to ToDoDB (MySQL)

## Architecture Diagram
[Insert architecture diagram here]

## Features
- Spring REST: For building RESTful APIs
- Spring Data JPA: For database interactions
- Hibernate: For ORM
- Security: JWT for secure communication between services
- Java Mail: For sending verification emails
- Swagger: For API documentation
- JUnit: For comprehensive testing
- Global Exception Handling: For consistent error management

## How to Run
1. Clone the repository:
    ```bash
    git clone https://github.com/AbdullahAbd-Elsalam/ToDoApp-Spring-boot--JWT-MicroServices
    ```
2. Build the project:
    ```bash
    mvn clean install
    ```
3. Run the services:
    ```bash
    java -jar user-service/target/user-service.jar
    java -jar todo-service/target/todo-service.jar
    ```
4. Access the API documentation:
    - Swagger UI for User Service
    - Swagger UI for ToDo Service
