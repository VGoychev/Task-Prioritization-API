# Task Prioritization API

## Overview
The **Task Prioritization API** is a RESTful service that enables users to create, retrieve, update, and delete tasks. It also provides a **smart priority system** that assigns priorities to tasks based on predefined business rules and user-defined parameters.

## Features Implemented
- **Task Creation**: Users can create new tasks with attributes such as:
  - Title
  - Description
  - Due date
  - Completion status
  - Urgency and criticality
- **Task Retrieval**:
  - Retrieve all tasks.
  - Retrieve tasks sorted by priority.
  - Retrieve tasks sorted by due date.
  - Retrieve tasks filtered by completion status.
- **Task Update**: Users can update task details (title, description, due date, status, urgency, and criticality).
- **Task Deletion**: Users can delete tasks by ID.
- **Priority Calculation**: Tasks are automatically assigned a priority level (**HIGH, MEDIUM, LOW**) based on:
  - Whether they are **critical** or **urgent**.
  - Whether they are already **completed**.
  - The **due date** compared to other tasks.

---

## API Endpoints

### Task Management

| HTTP Method | Endpoint                 | Description               |
|------------|--------------------------|---------------------------|
| `POST`     | `/tasks/create-task`      | Create a new task.        |
| `GET`      | `/tasks`                  | Retrieve all tasks.       |
| `PUT`      | `/tasks/{taskID}`         | Update an existing task.  |
| `DELETE`   | `/tasks/delete/{taskID}`  | Delete a task by ID.      |

### Task Sorting & Filtering

| HTTP Method | Endpoint                    | Description                        |
|------------|-----------------------------|------------------------------------|
| `GET`      | `/tasks/sorted-by-priority`  | Retrieve tasks sorted by priority.|
| `GET`      | `/tasks/sorted-by-date`      | Retrieve tasks sorted by due date.|
| `GET`      | `/tasks/filter-completion`   | Retrieve completed tasks only.    |

---

## Technologies Used
- **Spring Boot** - Java-based framework for building REST APIs.
- **Spring Data JPA** - Simplifies database interactions.
- **H2 / MySQL** - Database for storing tasks.
- **Lombok** - Reduces boilerplate code.
- **ModelMapper** - Facilitates object mapping.

---

## How to Run the Project

### Prerequisites
- Java **11+**
- Maven
- MySQL (if using a persistent database)

### Steps to Run
1. Clone the repository:  
```sh
git clone https://github.com/yourusername/task-prioritization-api.git
```
2. Navigate to the project directory:
```sh
cd task-prioritization-api
```
3. Configure application properties in:
```sh
src/main/resources/application.properties
```
4. Build and run the application using Maven:
```sh
mvn spring-boot:run
```
5. The API will be available at:
```sh
http://localhost:8080/tasks
```
## Future Enhancements
- Implement user authentication and authorization.
- Add pagination and filtering capabilities.
- Improve error handling and logging.

### Author
Vladimir Goychev

