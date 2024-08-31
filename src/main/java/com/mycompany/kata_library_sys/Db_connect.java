/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kata_library_sys;

import java.sql.*;
import java.time.LocalDate;
public class Db_connect{
     
    // Use correct MySQL driver
            
            // Establish database connection
    protected Connection con;
    Statement stmt;

    
//    connect to the database for operation
    protected boolean db_connect() throws Exception
    {   
        Class.forName("com.mysql.cj.jdbc.Driver"); 
        this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kata_library", "root", "");
        this.stmt = con.createStatement();
        if (con != null) {
                System.out.println("Connected to the database successfully!");
                return true;
            } else {
                System.out.println("Failed to connect to the database.");
                 return false;
            }

    }
    
    
//    add books
    protected int addBook(String isbn, String title, String author, int p_year, boolean is_available) {
        try {
            // Load MySQL JDBC driver
//           check for all valid data
            if(isbn != null && title != null && author != null && p_year != 0)
            {
                // Corrected SQL insert query with proper syntax
                String Insert_query = "INSERT INTO `books` (`isbn`, `title`, `author`, `publication_year`, `is_available`) VALUES ('" 
                                       + isbn + "','" + title + "','" + author + "'," + p_year + "," + (is_available ? 1 : 0) + ")";

                // Execute the query and check the result
                int rowsInserted = stmt.executeUpdate(Insert_query);
                
                    return rowsInserted;
                
            }
            return 0;
        }
        catch(SQLException e)
        {
             e.printStackTrace();
             return 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        
        
    }
    
//    return book 
    protected boolean returnBooks(int bookid,int userid) throws SQLException
    {
        if(validateUser(userid) && validateBook(bookid))
        {
                String update_book = "UPDATE `books` SET `is_available` = true where book_id = "+bookid;
                String insert_query = "delete from borrowed_books where bookid = "+bookid; 
        
                int rowsInserted = stmt.executeUpdate(insert_query);
                int rowsupdated = stmt.executeUpdate(update_book);
                
                if(rowsInserted > 0 && rowsupdated > 0)
                {
                    return true;
                }
                
        }
        return false;
    }
//    borrow books
    
    protected boolean borrowBooks(int bookid,int userid) throws SQLException
    {
        if(validateUser(userid) && validateBook(bookid))
        {
                String update_book = "UPDATE `books` SET `is_available` = false where book_id = "+bookid;
                String insert_query = "INSERT INTO `borrowed_books`(`book_id`, `user_id`, `borrow_date`, `return_date`) VALUES ("+bookid+","+userid+",'"+bookid+"',"+LocalDate.now()+")"; 
        
                int rowsInserted = stmt.executeUpdate(insert_query);
                int rowsupdated = stmt.executeUpdate(update_book);
                
                if(rowsInserted > 0 && rowsupdated > 0)
                {
                    return true;
                }
                
        }
        return false;
    }
    
    protected boolean validateUser(int userid) throws SQLException
    {
        String query = "select * from users where userid = '"+userid+"';";
        
         var rowsInserted = stmt.execute(query);
         if(rowsInserted)
         {
             return true;
         }
         return false;
    }
    
    protected boolean validateBook(int bookid) throws SQLException
    {
        String query = "select * from users where userid = '"+bookid+"';";
        
         boolean rowsInserted = stmt.execute(query);
         if(rowsInserted)
         {
             return true;
         }
         return false;
    }
    
}
