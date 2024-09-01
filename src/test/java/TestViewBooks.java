import com.mycompany.kata_library_sys.Book;
import com.mycompany.kata_library_sys.Db_connect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.util.List;

public class TestViewBooks {
    
    // Instance of Db_connect to interact with the database
    private Db_connect dbConnect;

    // Set up the database connection before each test case
    @BeforeEach
    public void setUp() throws Exception {
        dbConnect = new Db_connect();
        dbConnect.db_connect();
    }

    // Test to verify that the getAllBooks method returns a list of books
    @Test
    public void test_view_books_returns_list() {
        try {
            List<Book> books = dbConnect.getAllBooks();
            // Ensure the list is not null and contains books
            assertNotNull(books, "Book list should not be null");
            assertTrue(books.size() > 0, "Book list should not be empty");
        } catch (Exception e) {
            // Fail the test if an exception is thrown
            fail("Exception thrown while fetching books: " + e.getMessage());
        }
    }

    // Test to check that the books returned contain valid data
    @Test
    public void test_view_books_contains_valid_data() {
        try {
            List<Book> books = dbConnect.getAllBooks();
            assertNotNull(books, "Book list should not be null");
            assertTrue(books.size() > 0, "Book list should not be empty");

            // Validate each book's fields
            for (Book book : books) {
                assertNotNull(book.getIsbn(), "ISBN should not be null");
                assertNotNull(book.getTitle(), "Title should not be null");
                assertNotNull(book.getAuthor(), "Author should not be null");
                assertTrue(book.getPublicationYear() > 0, "Publication year should be greater than 0");
            }
        } catch (Exception e) {
            // Fail the test if an exception is thrown
            fail("Exception thrown while validating book data: " + e.getMessage());
        }
    }

    // Test to check the behavior when there are no books in the database
    @Test
    public void test_view_books_empty_database() throws SQLException {
        // Assuming the database is empty (no books in the table)
        List<Book> books = dbConnect.getAllBooks();
        
        // Ensure the list is not null but empty
        assertNotNull(books, "Book list should not be null");
        assertTrue(books.isEmpty(), "Book list should be empty when no books are in the database");
    }
}
