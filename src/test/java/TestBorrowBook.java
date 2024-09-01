import com.mycompany.kata_library_sys.Db_connect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;

public class TestBorrowBook {

    private Db_connect dbConnect;

    @BeforeEach
    public void setUp() throws Exception {
        dbConnect = new Db_connect();
        dbConnect.db_connect();
    }

    @Test
    public void test_borrow_return_type_is_boolean() throws SQLException {
        boolean result = dbConnect.borrowBooks( 1234567890, 31); // Example book ID and user ID
        assertFalse(result);
    }

    @Test
    public void test_borrow_book_string_ISBN() throws SQLException {
        // Example book ID and user ID
        int bookId = 1;
        int userId = 1;
        boolean result = dbConnect.borrowBooks(bookId, userId);
        assertFalse(result);
    }

    @Test
    public void test_borrow_int_user_id() throws SQLException {
        // Example book ID and user ID
        int bookId = 1;
        int userId = 1;
        boolean result = dbConnect.borrowBooks(bookId, userId);
        assertTrue(result);
    }

    @Test
    public void test_borrower_exists() throws SQLException {
        // Example user ID
        int userId = 1;
        boolean userExists = dbConnect.validateUser(userId);
        assertTrue(userExists);
    }

    @Test
    public void test_borrow_book_using_wrong_ISBN() throws SQLException {
        // Example wrong book ID and user ID
        int wrongBookId = 9999;
        int userId = 1;
        boolean result = dbConnect.borrowBooks(wrongBookId, userId);
        assertFalse(result);
    }

    @Test
    public void test_book_already_borrowed() throws SQLException {
        // Ensure the book is borrowed first
        int bookId = 24;
        int userId = 1;
        dbConnect.borrowBooks(bookId, userId);
        // Try borrowing the same book again
        boolean result = dbConnect.borrowBooks(bookId, userId);
        assertFalse(result);
    }
}
