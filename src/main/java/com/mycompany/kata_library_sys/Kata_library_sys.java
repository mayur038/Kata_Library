package com.mycompany.kata_library_sys;

import java.util.Scanner;
import java.util.List;

public class Kata_library_sys extends Db_connect {

    public static void main(String[] args) {
        try {
            // Create an instance of Db_connect to perform database operations
            Db_connect db = new Db_connect();
            
            // Connect to the database
            boolean isConnected = db.db_connect();
            if (!isConnected) {
                System.out.println("Failed to connect to the database. Exiting...");
                return;
            }

            // Scanner for user input
            Scanner scanner = new Scanner(System.in);
            int choice;

            do {
                clearScreen();
                printWelcomeMessage();

                // Display menu and get user's choice
                System.out.println("1. Add a Book");
                System.out.println("2. Borrow a Book");
                System.out.println("3. Return a Book");
                System.out.println("4. View All Books");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addBookProcess(db, scanner);
                        break;
                    case 2:
                        borrowBookProcess(db, scanner);
                        break;
                    case 3:
                        returnBookProcess(db, scanner);
                        break;
                    case 4:
                        viewBooksProcess(db);
                        break;
                    case 5:
                        System.out.println("Exiting the system. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice! Please select a valid option.");
                }

                if (choice != 5) {
                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                }

            } while (choice != 5);

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to clear the screen
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Method to print a welcome message
    private static void printWelcomeMessage() {
        System.out.println("===========================================");
        System.out.println("          Welcome to the Library           ");
        System.out.println("===========================================");
    }

    // Method to process adding a book
    private static void addBookProcess(Db_connect db, Scanner scanner) {
        System.out.println("\n--- Add a New Book ---");

        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Enter Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Author: ");
        String author = scanner.nextLine();

        System.out.print("Enter Publication Year: ");
        int publicationYear = scanner.nextInt();

        System.out.print("Is the book available? (true/false): ");
        boolean isAvailable = scanner.nextBoolean();

        int result = db.addBook(isbn, title, author, publicationYear, isAvailable);
        if (result > 0) {
            System.out.println("Book added successfully!");
        } else {
            System.out.println("Failed to add the book.");
        }
    }

    // Method to process borrowing a book
    private static void borrowBookProcess(Db_connect db, Scanner scanner) {
        System.out.println("\n--- Borrow a Book ---");

        System.out.print("Enter Book ID: ");
        int bookId = scanner.nextInt();

        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();

        try {
            boolean result = db.borrowBooks(bookId, userId);
            if (result) {
                System.out.println("Book borrowed successfully!");
            } else {
                System.out.println("Failed to borrow the book. It might already be borrowed or the IDs are incorrect.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while borrowing the book: " + e.getMessage());
        }
    }

    // Method to process returning a book
    private static void returnBookProcess(Db_connect db, Scanner scanner) {
        System.out.println("\n--- Return a Book ---");

        System.out.print("Enter Book ID: ");
        int bookId = scanner.nextInt();

        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();

        try {
            boolean result = db.returnBooks(bookId, userId);
            if (result) {
                System.out.println("Book returned successfully!");
            } else {
                System.out.println("Failed to return the book. It might not be borrowed or the IDs are incorrect.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while returning the book: " + e.getMessage());
        }
    }

    // Method to process viewing all books
    private static void viewBooksProcess(Db_connect db) {
        System.out.println("\n--- View All Books ---");

        List<Book> books = db.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
        } else {
            for (Book book : books) {
                System.out.println("Book ID: " + book.getBookId());
                System.out.println("ISBN: " + book.getIsbn());
                System.out.println("Title: " + book.getTitle());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("Publication Year: " + book.getPublicationYear());
                System.out.println("Is Available: " + book.isAvailable());
                System.out.println("---------------------------");
            }
        }
    }
}
