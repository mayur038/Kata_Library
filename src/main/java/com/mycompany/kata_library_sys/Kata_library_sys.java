/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.kata_library_sys;

/**
 *
     * @author PTL
 */
import java.util.*;
public class Kata_library_sys extends Db_connect{

    public static void main(String[] args) {
        try
        {
             Db_connect db = new Db_connect();
        boolean status = db.db_connect();
        int s = db.addBook("1232", "book name", "name", 2021, true);
        
        
            List<Book> books = db.getAllBooks();

                // Print each book's details
                for (Book book : books) {
                    System.out.println("Book ID: " + book.getBookId());
                    System.out.println("ISBN: " + book.getIsbn());
                    System.out.println("Title: " + book.getTitle());
                    System.out.println("Author: " + book.getAuthor());
                    System.out.println("Publication Year: " + book.getPublicationYear());
                    System.out.println("Is Available: " + book.isAvailable());
                    System.out.println("---------------------------");
                }
        // Print the HashMap
       
        if(status)
        {
           
            System.out.println("congrats"+s);
        }
        }catch(Exception e)
        {
            System.out.println(""+e);
        }
       
    }
}
