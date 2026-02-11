# Task Management Application

## Overview
A Spring Boot backend for managing tasks with login/logout and role-based APIs.  
Features include:
- User authentication with JWT
- Create, update, assign, view, and delete tasks
- PostgreSQL database integration
- Validation and error handling

---

## Requirements
- Java 17+
- Maven 3+
- PostgreSQL
- Postman (for API testing)

---

## Setup Instructions

## Clone the Repository
```bash
git clone https://github.com/HazimHamal/task-management.git
cd task-management

## Update application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

## Build and run
mvn clean install
mvn spring-boot:run

Apps runs at http://localhost:8080

## Task APIs
- Create Task:	POST	/tasks/create
- Update Task:	PUT 	/tasks/update/{id}
- View Task: 	GET 	/tasks (view all tasks)
						OR /tasks/{id} (view selected tasks)
						OR /tasks/view?ids={id},{id} (view multiple selected tasks)
- Delete Task: 	DELETE 	/tasks/delete/{id}