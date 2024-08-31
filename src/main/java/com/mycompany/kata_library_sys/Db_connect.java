/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kata_library_sys;

import java.sql.*;
import java.util.*;
import java.time.LocalDate;
public class Db_connect{
     
    // Use correct MySQL driver
            
            // Establish database connection
    protected Connection con;
    protected Statement stmt;
    protected ResultSet resultSet;
    
//    connect to the database for operation
    protected boolean db_connect() throws Exception {   
        Class.forName("com.mysql.cj.jdbc.Driver"); 
        this.con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/kata_library", "root", "");
          
        this.stmt = con.createStatement();
        if (con != null) {
            System.out.println("Connected to the database successfully!");
            return true;
        } else {
            System.out.println("Failed to connect to the database.");
            return false;
        }
    }
    
    // Add a book
    protected int addBook(String isbn, String title, String author, int p_year, boolean is_available) {
        try {
            // Check for all valid data
            if(isbn != null && title != null && author != null && p_year != 0) {
                // Corrected SQL insert query with proper syntax
                String Insert_query = "INSERT INTO `books` (`isbn`, `title`, `author`, `publication_year`, `is_available`) VALUES ('" 
                                       + isbn + "','" + title + "','" + author + "'," + p_year + "," + (is_available ? 1 : 0) + ")";
                
                // Execute the query and check the result
                int rowsInserted = stmt.executeUpdate(Insert_query);
                return rowsInserted;
            }
            return 0;
        } catch(SQLException e) {
            e.printStackTrace();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // View all books
    public List<Book> getAllBooks() {
        List<Book> booksList = new ArrayList<>();

        try {
            // SQL query to fetch all books
            String query = "SELECT * FROM books";

            // Execute the query
            resultSet = stmt.executeQuery(query);

            // Process the result set and transfer data to the List<Book>
            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                String isbn = resultSet.getString("isbn");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int publicationYear = resultSet.getInt("publication_year");
                boolean isAvailable = resultSet.getBoolean("is_available");

                // Create a new Book object and add it to the list
                Book book = new Book(bookId, isbn, title, author, publicationYear, isAvailable);
                booksList.add(book);
            }

        } catch (SQLException e) {
            // Handle SQL exceptions
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (resultSet != null) resultSet.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Return the List<Book> containing all books
        return booksList;
    }

    // Return a book
    protected boolean returnBooks(int bookid, int userid) throws SQLException {
        if (validateUser(userid) && validateBook(bookid)) {
            String update_book = "UPDATE `books` SET `is_available` = true WHERE `book_id` = " + bookid;
            String delete_query = "DELETE FROM `borrowed_books` WHERE `book_id` = " + bookid; 

            int rowsDeleted = stmt.executeUpdate(delete_query);
            int rowsUpdated = stmt.executeUpdate(update_book);
            
            return rowsDeleted > 0 && rowsUpdated > 0;
        }
        return false;
    }

    // Borrow a book
    protected boolean borrowBooks(int bookid, int userid) throws SQLException {
        if (validateUser(userid) && validateBook(bookid)) {
            String update_book = "UPDATE `books` SET `is_available` = false WHERE `book_id` = " + bookid;
            String insert_query = "INSERT INTO `borrowed_books`(`book_id`, `user_id`, `borrow_date`, `return_date`) VALUES (" + bookid + "," + userid + ",'" + LocalDate.now() + "',NULL)"; 

            int rowsInserted = stmt.executeUpdate(insert_query);
            int rowsUpdated = stmt.executeUpdate(update_book);
            
            return rowsInserted > 0 && rowsUpdated > 0;
        }
        return false;
    }

    // Validate user
    protected boolean validateUser(int userid) throws SQLException {
        String query = "SELECT * FROM users WHERE user_id = " + userid;
        ResultSet rs = stmt.executeQuery(query);
        return rs.next();
    }

    // Validate book
    protected boolean validateBook(int bookid) throws SQLException {
        String query = "SELECT * FROM books WHERE book_id = " + bookid;
        ResultSet rs = stmt.executeQuery(query);
        return rs.next();
    }
}