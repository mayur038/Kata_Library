import com.mycompany.kata_library_sys.Db_connect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;

public class TestBorrowBook {
    // Instance of Db_connect to interact with the database
    private Db_connect dbConnect;

    // Set up the database connection before each test case
    @BeforeEach
    public void setUp() throws Exception {
        dbConnect = new Db_connect();
        dbConnect.db_connect();
    }

    // Test to verify the borrowBooks method returns a boolean indicating success or failure
    @Test
    public void test_borrow_return_type_is_boolean() throws SQLException {
        boolean result = dbConnect.borrowBooks(1234567890, 31); // Example book ID and user ID
        assertFalse(result);
    }

    // Test to ensure a valid book ID and user ID are required for borrowing a book
    @Test
    public void test_borrow_book_string_ISBN() throws SQLException {
        int bookId = 1;
        int userId = 1;
        boolean result = dbConnect.borrowBooks(bookId, userId);
        assertFalse(result);
    }

    // Test to check if a valid integer user ID is required for borrowing a book
    @Test
    public void test_borrow_int_user_id() throws SQLException {
        int bookId = 1;
        int userId = 1;
        boolean result = dbConnect.borrowBooks(bookId, userId);
        assertTrue(result);
    }

    // Test to confirm that the user exists before borrowing a book
    @Test
    public void test_borrower_exists() throws SQLException {
        int userId = 1;
        boolean userExists = dbConnect.validateUser(userId);
        assertTrue(userExists);
    }

    // Test to check the behavior when trying to borrow a book using an incorrect ISBN
    @Test
    public void test_borrow_book_using_wrong_ISBN() throws SQLException {
        int wrongBookId = 9999;
        int userId = 1;
        boolean result = dbConnect.borrowBooks(wrongBookId, userId);
        assertFalse(result);
    }

    // Test to ensure a book that is already borrowed cannot be borrowed again
    @Test
    public void test_book_already_borrowed() throws SQLException {
        int bookId = 24;
        int userId = 1;
        dbConnect.borrowBooks(bookId, userId); // Ensure the book is borrowed first
        boolean result = dbConnect.borrowBooks(bookId, userId); // Try borrowing the same book again
        assertFalse(result);
    }
}
