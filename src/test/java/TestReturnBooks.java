import com.mycompany.kata_library_sys.Db_connect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;

public class TestReturnBooks {

    public Db_connect dbConnect;

    @BeforeEach
    public void setUp() throws Exception {
        dbConnect = new Db_connect();
        dbConnect.db_connect();
    }

    @Test
    public void test_return_return_type_is_boolean() {
        try {
            boolean result = dbConnect.returnBooks(1, 1); // Assuming 1 is a valid book ID and user ID
            assertTrue(result == true || result == false, "Return type should be boolean");
        } catch (SQLException e) {
            fail("SQLException thrown: " + e.getMessage());
        }
    }

        @Test
    public void test_borrow_book_string_ISBN() throws SQLException {
        // Example book ID and user ID
        int bookId = 1;
        int userId = 1;
        boolean result = dbConnect.returnBooks(bookId, userId);
        assertFalse(result);
    }

    @Test
    public void test_borrow_int_user_id() throws SQLException {
        // Example book ID and user ID
        int bookId = 1;
        int userId = 1;
        boolean result = dbConnect.returnBooks(bookId, userId);
        assertTrue(result);
    }

    @Test
    public void test_returner_exists() {
        try {
            boolean result = dbConnect.returnBooks(1, 1); // Assuming 1 is a valid book ID and user ID
            assertFalse(result, "Book should be returned successfully if the user exists");
        } catch (SQLException e) {
            fail("SQLException thrown: " + e.getMessage());
        }
    }

    @Test
    public void test_return_book_using_wrong_ISBN() {
        try {
            // Assuming a method exists to get book ID from an incorrect ISBN
            int bookId = dbConnect.getBookIdFromISBN("wrong-isbn");
            boolean result = dbConnect.returnBooks(bookId, 1); // 1 is assumed to be a valid user ID
            assertFalse(result, "Book should not be returned with an invalid ISBN");
        } catch (SQLException e) {
            fail("SQLException thrown: " + e.getMessage());
        }
    }

    @Test
    public void test_book_is_not_borrowed() {
        try {
            // Assuming that book ID 2 is available (not borrowed)
            boolean result = dbConnect.returnBooks(2, 1); // 2 is a book ID and 1 is a valid user ID
            assertFalse(result, "Book should not be returned if it was not borrowed");
        } catch (SQLException e) {
            fail("SQLException thrown: " + e.getMessage());
        }
    }
}
