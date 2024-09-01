# Kata Library Management System

## Overview

The **Kata Library Management System** is a straightforward application designed to facilitate basic library operations. It allows users to manage books through various functionalities including adding new books, borrowing books, returning books, and viewing available books.

## Objectives

- **Add Books**: Add new books with a unique identifier (e.g., ISBN), title, author, and publication year.
- **Borrow Books**: Borrow books if they are available. The system checks availability and raises errors if the book is not available.
- **Return Books**: Return borrowed books and update the book's availability accordingly.
- **View Available Books**: View a list of all books that are currently available in the library.

## Technologies Used

- **Java**: Primary programming language for implementing the system.
- **MySQL**: Database system used for storing and managing data.
- **JUnit**: Framework for writing and running test cases.
- **Mockito**: Mocking framework used for unit testing in Java.

## Features

- **Database Connection**: Manages database connections and operations using MySQL.
- **Book Management**: Handles adding, borrowing, returning, and viewing books.
- **Validation**: Ensures data integrity with input validation and error checking.
- **Exception Handling**: Catches and logs SQL exceptions to maintain smooth operation.

## Setup and Installation

1. **Clone the Repository**:
    ```bash
    git clone <repository-url>
    ```
2. **Navigate to the Project Directory**:
    ```bash
    cd kata_library_sys
    ```
3. **Set Up MySQL Database**:
    - Create a MySQL database named `kata_library`.
    - Import the provided SQL schema to create the necessary tables (`books`, `borrowed_books`, `users`).

4. **Configure Database Connection**:
    - Update database connection details in the `Db_connect` class if required.

5. **Build and Run the Project**:
    - Use NetBeans or your preferred IDE to build and run the project.

## Test Cases Implemented

- **TestAddBook**: Validates that books are added correctly to the database.
- **TestBorrowBook**: Ensures books can be borrowed if available and handles errors if not.
- **TestReturnBooks**: Confirms that returned books are updated appropriately in the database.
- **TestViewBooks**: Verifies that the list of available books is correctly retrieved and displayed.

## Validation Methods

- **Input Validation**: Checks for null values, special characters, and valid data ranges before performing operations.
- **Availability Checks**: Ensures books are available before borrowing and updates availability after returning.
- **Error Handling**: Logs and handles SQL exceptions to ensure the system operates smoothly.

## Contact

For further information or questions, please contact [Your Name] at [Your Email].

