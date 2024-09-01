package com.mycompany.kata_library_sys;

import java.sql.*;
import java.util.*;
import java.time.LocalDate;
import java.sql.Date;

public class Db_connect {

    // Database connection components
    protected Connection con;
    protected Statement stmt;
    protected ResultSet resultSet;

    /**
     * Establishes a connection to the database.
     * 
     * @return true if connection is successful, false otherwise
     * @throws Exception if an error occurs during connection
     */
    public boolean db_connect() throws Exception {
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Connect to the database
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kata_library", "root", "");
            this.stmt = con.createStatement();

            if (con != null) {
                System.out.println("Connected to the database successfully!");
                return true;
            } else {
                System.out.println("Failed to connect to the database.");
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds a new book to the library database.
     * 
     * @param isbn          the ISBN of the book
     * @param title         the title of the book
     * @param author        the author of the book
     * @param p_year        the publication year of the book
     * @param is_available  availability status of the book
     * @return the number of rows inserted
     */
    public int addBook(String isbn, String title, String author, int p_year, boolean is_available) {
        try {
            // Validate book data
            if (isValidBookData(isbn, title, author, p_year)) {
                String insertQuery = "INSERT INTO `books` (`isbn`, `title`, `author`, `publication_year`, `is_available`) VALUES (?, ?, ?, ?, ?)";

                // Use PreparedStatement to prevent SQL injection
                try (PreparedStatement preparedStatement = con.prepareStatement(insertQuery)) {
                    preparedStatement.setString(1, isbn);
                    preparedStatement.setString(2, title);
                    preparedStatement.setString(3, author);
                    preparedStatement.setInt(4, p_year);
                    preparedStatement.setBoolean(5, is_available);

                    return preparedStatement.executeUpdate();
                }
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Retrieves a list of all available books from the database.
     * 
     * @return a list of available books
     */
    public List<Book> getAllBooks() {
        List<Book> booksList = new ArrayList<>();
        String query = "SELECT * FROM books WHERE is_available = true";

        try (ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Book book = new Book(
                    rs.getInt("book_id"),
                    rs.getString("isbn"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("publication_year"),
                    rs.getBoolean("is_available")
                );
                booksList.add(book);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }

        return booksList;
    }

    /**
     * Returns a borrowed book to the library.
     * 
     * @param bookId  the ID of the book to return
     * @param userId  the ID of the user returning the book
     * @return true if the book is successfully returned, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean returnBooks(int bookId, int userId) throws SQLException {
        if (validateUser(userId) && validateBook(bookId)) {
            String updateBookQuery = "UPDATE `books` SET `is_available` = true WHERE `book_id` = ?";
            String deleteBorrowedBookQuery = "DELETE FROM `borrowed_books` WHERE `book_id` = ?";

            try (PreparedStatement updateBookStmt = con.prepareStatement(updateBookQuery);
                 PreparedStatement deleteBorrowedBookStmt = con.prepareStatement(deleteBorrowedBookQuery)) {

                updateBookStmt.setInt(1, bookId);
                deleteBorrowedBookStmt.setInt(1, bookId);

                int rowsDeleted = deleteBorrowedBookStmt.executeUpdate();
                int rowsUpdated = updateBookStmt.executeUpdate();

                return rowsDeleted > 0 && rowsUpdated > 0;
            }
        }
        return false;
    }

    /**
     * Borrows a book from the library.
     * 
     * @param bookId  the ID of the book to borrow
     * @param userId  the ID of the user borrowing the book
     * @return true if the book is successfully borrowed, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean borrowBooks(int bookId, int userId) throws SQLException {
    if (!validateUser(userId) || !validateBook(bookId) || !isBookAvailable(bookId)) {
        return false; // User does not exist, book does not exist, or book is not available
    }

    String updateBookQuery = "UPDATE `books` SET `is_available` = false WHERE `book_id` = ?";
    String insertBorrowedBookQuery = "INSERT INTO `borrowed_books`(`book_id`, `user_id`, `borrow_date`, `return_date`) VALUES (?, ?, ?, NULL)";

    try (PreparedStatement updateBookStmt = con.prepareStatement(updateBookQuery);
         PreparedStatement insertBorrowedBookStmt = con.prepareStatement(insertBorrowedBookQuery)) {

        updateBookStmt.setInt(1, bookId);
        insertBorrowedBookStmt.setInt(1, bookId);
        insertBorrowedBookStmt.setInt(2, userId);
        insertBorrowedBookStmt.setDate(3, java.sql.Date.valueOf(LocalDate.now())); // Use fully qualified name

        int rowsInserted = insertBorrowedBookStmt.executeUpdate();
        int rowsUpdated = updateBookStmt.executeUpdate();

        return rowsInserted > 0 && rowsUpdated > 0;
    }
}


    /**
     * Checks if a book is available for borrowing.
     * 
     * @param bookId  the ID of the book
     * @return true if the book is available, false otherwise
     * @throws SQLException if a database error occurs
     */
    private boolean isBookAvailable(int bookId) throws SQLException {
        String query = "SELECT `is_available` FROM `books` WHERE `book_id` = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("is_available");
                }
            }
        }
        return false; // Book does not exist
    }

    /**
     * Validates if a user exists in the database.
     * 
     * @param userId  the ID of the user
     * @return true if the user exists, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean validateUser(int userId) throws SQLException {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Validates if a book exists in the database.
     * 
     * @param bookId  the ID of the book
     * @return true if the book exists, false otherwise
     * @throws SQLException if a database error occurs
     */
    protected boolean validateBook(int bookId) throws SQLException {
        String query = "SELECT * FROM books WHERE book_id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Checks if the input string contains any special characters.
     * 
     * @param input  the string to check
     * @return true if the string contains special characters, false otherwise
     */
    protected static boolean specialchar(String input) {
        // Regular expression to match any character that is not a letter, digit, or space
        String regex = "[^a-zA-Z0-9 ]";
        return input.matches(".*" + regex + ".*");
    }

    /**
     * Validates the input data for adding a book.
     * 
     * @param isbn    the ISBN of the book
     * @param title   the title of the book
     * @param author  the author of the book
     * @param p_year  the publication year of the book
     * @return true if the data is valid, false otherwise
     */
    private boolean isValidBookData(String isbn, String title, String author, int p_year) {
        return p_year < 2024 && isbn != null && title != null && author != null && p_year != 0 && !specialchar(title) && !specialchar(isbn);
    }

    /**
     * Retrieves a book ID based on its ISBN.
     * 
     * @param isbn  the ISBN of the book
     * @return the ID of the book, or -1 if not found
     * @throws SQLException if a database error occurs
     */
    public int getBookIdFromISBN(String isbn) throws SQLException {
        int bookId = -1; // Default value if ISBN is not found
        String query = "SELECT book_id FROM books WHERE isbn = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, isbn);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    bookId = rs.getInt("book_id");
                }
            }
        }
        return bookId;
    }
}
