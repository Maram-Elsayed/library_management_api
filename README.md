Library Management System

Project Description

The Library Management System API is built using Spring Boot to manage books, patrons, and borrowing records. It allows librarians to perform CRUD operations on books and patrons, as well as manage borrowing records.

Features

- Book Management:
  - Retrieve a list of all books (GET /api/books)
  - Retrieve details of a specific book by ID (GET /api/books/{id})
  - Add a new book (POST /api/books)
  - Update an existing book's information (PUT /api/books/{id})
  - Remove a book from the library (DELETE /api/books/{id})
- Patron Management:
  - Retrieve a list of all patrons (GET /api/patrons)
  - Retrieve details of a specific patron by ID (GET /api/patrons/{id})
  - Add a new patron (POST /api/patrons)
  - Update an existing patron's information (PUT /api/patrons/{id})
  - Remove a patron from the system (DELETE /api/patrons/{id})
- Borrowing Records:
  - Allow a patron to borrow a book (POST /api/borrow/{bookId}/patron/{patronId})
  - Record the return of a borrowed book by a patron (PUT /api/return/{bookId}/patron/{patronId})
 
Data Storage
- Database: MySQL
- Entities:
  - Book: ID, title, author, publication year, ISBN.
  - Patron: ID, name, phone.
  - Borrowing Record: ID, bookId, patronId, borrow date, return date.
 
Security
- Authentication: Basic Auth
  - Username: admin
  - Password: admin
  - Authentication details are present in the application.properties file
 
Caching
- Cache Type: Simple
- Caching is used to improve performance for frequently accessed data, such as book details and patron information.


Transaction Management
- Declarative transaction management is implemented using Spring’s @Transactional annotation to ensure data integrity during critical operations.

Testing
- Unit Tests: Written using Mockito
- Frameworks: JUnit, Mockito

Documentation

To run the application and interact with the API endpoints:

1. Setup:
  - Ensure you have Java 17 or higher installed.
  - Set up MySQL database and configure it in application.properties.
2. Run the Application:
  - Use `mvn spring-boot:run` to start the application
3. API Interaction:
  - Use a tool like Postman or Curl to test the API endpoints.
  - Basic Auth credentials are `admin/admin`.
4. Run Unit Tests:
  - Use `mvn test` to execute unit tests.
