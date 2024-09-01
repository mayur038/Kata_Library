# Kata Library Management System

## Overview

The **Kata Library Management System** is a straightforward application designed to facilitate basic library operations. It allows users to manage books through various functionalities including adding new books, borrowing books, returning books, and viewing available books.

## Objectives

- **Add Books**: Add new books with a unique identifier (e.g., ISBN), title, author, and publication year.
- **Borrow Books**: Borrow books if they are available. The system checks availability and raises errors if the book is not available.
- **Return Books**: Return borrowed books and update the book's availability accordingly.
- **View Available Books**: View a list of all books that are currently available in the library.

## Package i Used

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
    git clone https://github.com/mayur038/kata_library
    ```
2. **Navigate to the Project Directory**:
    ```bash
    cd kata_library_sys
    ```
3. **Set Up MySQL Database**:
    - Create a MySQL database named `kata_library`.
    - Import the provided SQL schema named kata_library.sql in src/main/java/com/mycompany/kata_library_sys to create the necessary tables (`books`, `borrowed_books`, `users`).

4. **Configure Database Connection**:
    - Update database connection details in the `Db_connect` class if required.

5. **Build and Run the Project**:
    - Use NetBeans or your preferred IDE to build and run the project.

## Test Cases

The following test cases have been implemented to ensure the functionality and reliability of the Kata Library Management System:

1. **TestAddBook**:
   - This test case verifies the successful addition of a book to the library's database. It checks that all required fields, such as ISBN, title, author, and publication year, are correctly validated and inserted into the system.

2. **TestBorrowBook**:
   - This test case ensures that a book can be borrowed only if it is available. It validates the process of marking a book as borrowed and records the borrowing transaction, ensuring that no duplicate or unavailable books can be borrowed.

3. **TestReturnBooks**:
   - This test case checks the return functionality by verifying that a borrowed book is correctly marked as available again. It also ensures that the associated borrowing record is removed from the database upon return.

4. **TestViewBooks**:
   - This test case validates the ability to retrieve and display a list of all available books in the library. It ensures that the system accurately shows only those books that are currently available for borrowing.

## Project Requirements

- **mysql-connector-j-8.3.0 jar**: Project requires connector jar file for performing database operation in Java. file is already provided if you want to add externally you can download from https://dev.mysql.com/downloads/connector/j/
- **Availability Checks**: Ensures books are available before borrowing and updates availability after returning.
- **Error Handling**: Logs and handles SQL exceptions to ensure the system operates smoothly.

## Contact

For further information or questions, please contact mayur gohil at mkg15122003@gmail.com.


