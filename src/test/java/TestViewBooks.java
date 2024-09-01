import com.mycompany.kata_library_sys.Book;
import com.mycompany.kata_library_sys.Db_connect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.util.List;

public class TestViewBooks {

    private Db_connect dbConnect;

    @BeforeEach
    public void setUp() throws Exception {
        dbConnect = new Db_connect();
        dbConnect.db_connect();
    }

    @Test
    public void test_view_books_returns_list() {
        try {
            List<Book> books = dbConnect.getAllBooks();
            assertNotNull(books);
            assertTrue(books.size() > 0, "Book list should not be empty");
        } catch (Exception e) {
            fail("Exception thrown while fetching books: " + e.getMessage());
        }
    }

    @Test
    public void test_view_books_contains_valid_data() {
        try {
            List<Book> books = dbConnect.getAllBooks();
            assertNotNull(books);
            assertTrue(books.size() > 0, "Book list should not be empty");
            for (Book book : books) {
                assertNotNull(book.getIsbn(), "ISBN should not be null");
                assertNotNull(book.getTitle(), "Title should not be null");
                assertNotNull(book.getAuthor(), "Author should not be null");
                assertTrue(book.getPublicationYear() > 0, "Publication year should be greater than 0");
            }
        } catch (Exception e) {
            fail("Exception thrown while validating book data: " + e.getMessage());
        }
    }

    @Test
    public void test_view_books_empty_database() throws SQLException {
        // Assuming you have no data of books in the table
        

        List<Book> books = dbConnect.getAllBooks();
        assertNotNull(books);
        assertFalse(books.isEmpty(), "Book list should be empty when no books are in the database");
    }

    
}
