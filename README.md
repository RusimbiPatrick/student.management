# Student Management API

This API provides endpoints to manage student records. Below is the documentation for all available endpoints, including their inputs, responses, and status codes.

## Base URL
```
http://localhost:8080/api/students
```

## Endpoints

### 1. Get All Students
Retrieve a list of all students.

- **Method**: `GET`
- **URL**: `/api/students`
- **Response**: 
  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    [
        {
            "id": "b820397c-2361-4ec6-b178-904c6f4132cd",
            "firstName": "Aline",
            "lastName": "Mukeshimana",
            "email": "aline.mukeshimana@example.com",
            "location": "Nyagatare"
        },
        {
            "id": "bd53550d-a7a8-4c77-abd1-b1a5fca7cb65",
            "firstName": "Claudia",
            "lastName": "Muganza",
            "email": "claudia.muganza@example.com",
            "location": "Nyarugenge"
        }
    ]
    ```

### 2. Get Student by ID
Retrieve a specific student by their ID.

- **Method**: `GET`
- **URL**: `/api/students/{id}`
- **Response**:
  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    {
        "id": "b820397c-2361-4ec6-b178-904c6f4132cd",
        "firstName": "Aline",
        "lastName": "Mukeshimana",
        "email": "aline.mukeshimana@example.com",
        "location": "Nyagatare"
    }
    ```
  - **Error Response**:
    - **Status Code**: `404 Not Found`
    - **Body**:
      ```json
      {
          "timestamp": "2025-02-16T17:49:12.050431227",
          "status": 404,
          "error": "Not Found",
          "message": "Student with ID {id} not found"
      }
      ```

### 3. Create a New Student
Create a new student record.

- **Method**: `POST`
- **URL**: `/api/students`
- **Request Body**:
  ```json
  {
      "firstName": "Mugabo",
      "lastName": "Enock",
      "email": "mugabo.enock@example.com",
      "location": "Kigali"
  }
  ```
- **Response**:
  - **Status Code**: `201 Created`
  - **Body**:
    ```json
    {
        "id": "3dc087f2-02a3-4027-b0e9-42b3571c9ef6",
        "firstName": "Mugabo",
        "lastName": "Enock",
        "email": "mugabo.enock@example.com",
        "location": "Kigali"
    }
    ```
  - **Error Response**:
    - **Status Code**: `400 Bad Request`
    - **Body**:
      ```json
      {
          "timestamp": "2025-02-16T17:49:12.050431227",
          "status": 400,
          "error": "Bad Request",
          "message": "A student with this email already exists"
      }
      ```
    - **Status Code**: `500 Internal Server Error`
    - **Body**:
      ```json
      {
          "timestamp": "2025-02-16T17:49:12.050431227",
          "status": 500,
          "error": "Internal Server Error",
          "message": "Error creating student: {error_message}"
      }
      ```

### 4. Update a Student
Update an existing student record.

- **Method**: `PUT`
- **URL**: `/api/students/{id}`
- **Request Body**:
  ```json
  {
      "firstName": "Claudia",
      "lastName": "Muganza",
      "email": "claudia.muganza@example.com",
      "location": "Nyarugenge"
  }
  ```
- **Response**:
  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    {
        "id": "bd53550d-a7a8-4c77-abd1-b1a5fca7cb65",
        "firstName": "Claudia",
        "lastName": "Muganza",
        "email": "claudia.muganza@example.com",
        "location": "Nyarugenge"
    }
    ```
  - **Error Response**:
    - **Status Code**: `404 Not Found`
    - **Body**:
      ```json
      {
          "timestamp": "2025-02-16T17:49:12.050431227",
          "status": 404,
          "error": "Not Found",
          "message": "Student with ID {id} not found"
      }
      ```
    - **Status Code**: `400 Bad Request`
    - **Body**:
      ```json
      {
          "timestamp": "2025-02-16T17:49:12.050431227",
          "status": 400,
          "error": "Bad Request",
          "message": "Email already exists"
      }
      ```
    - **Status Code**: `500 Internal Server Error`
    - **Body**:
      ```json
      {
          "timestamp": "2025-02-16T17:49:12.050431227",
          "status": 500,
          "error": "Internal Server Error",
          "message": "Error updating student: {error_message}"
      }
      ```

### 5. Delete a Student
Delete a student record by ID.

- **Method**: `DELETE`
- **URL**: `/api/students/{id}`
- **Response**:
  - **Status Code**: `204 No Content`
  - **Body**: None
  - **Error Response**:
    - **Status Code**: `404 Not Found`
    - **Body**:
      ```json
      {
          "timestamp": "2025-02-16T17:49:12.050431227",
          "status": 404,
          "error": "Not Found",
          "message": "Student with ID {id} not found"
      }
      ```
    - **Status Code**: `500 Internal Server Error`
    - **Body**:
      ```json
      {
          "timestamp": "2025-02-16T17:49:12.050431227",
          "status": 500,
          "error": "Internal Server Error",
          "message": "Error deleting student: {error_message}"
      }
      ```

## Error Handling
All error responses include a JSON object with the following fields:
- `timestamp`: The date and time when the error occurred.
- `status`: The HTTP status code.
- `error`: A brief description of the error.
- `message`: A detailed error message.

## Example Usage

### Create a Student
```bash
curl -X POST http://localhost:8080/api/students \
-H "Content-Type: application/json" \
-d '{
    "firstName": "Mugabo",
    "lastName": "Enock",
    "email": "mugabo.enock@example.com",
    "location": "Kigali"
}'
```

### Get All Students
```bash
curl -X GET http://localhost:8080/api/students
```

### Get a Student by ID
```bash
curl -X GET http://localhost:8080/api/students/b820397c-2361-4ec6-b178-904c6f4132cd
```

### Update a Student
```bash
curl -X PUT http://localhost:8080/api/students/bd53550d-a7a8-4c77-abd1-b1a5fca7cb65 \
-H "Content-Type: application/json" \
-d '{
    "location": "Nyarugenge"
}'
```

### Delete a Student
```bash
curl -X DELETE http://localhost:8080/api/students/b820397c-2361-4ec6-b178-904c6f4132cd
```
