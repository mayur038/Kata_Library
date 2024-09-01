import com.mycompany.kata_library_sys.Db_connect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestAddbook   {
    public Db_connect db;

     @BeforeEach
    public void setUp() throws Exception {
        db = new Db_connect();
        db.db_connect();
    }

    @Test
    public void test_return_type_is_boolean() {
        int result = db.addBook("978-3-16-148410-0", "Test Title", "Test Author", 2023, true);
        assertTrue(result >= 0);
    }

    @Test
    public void test_check_for_null_values() {
        int result = db.addBook(null, "Test Title", "Test Author", 2023, true);
        assertEquals(0, result);
    }

    @Test
    public void test_add_book_string_ISBN() {
        int result = db.addBook("97-8316148-4100", "Test Title", "Test Author", 2023, true);
        assertEquals(0, result);
    }

    @Test
    public void test_future_publication_year() {
        int result = db.addBook("9783161484100", "Test Title", "Test Author", 2025, true);
        assertEquals(0, result);
    }

    @Test
        public void test_insert_book() {
            int result = db.addBook("9783161484100", "TestTitle!", "Test Author", 2023, true);
            assertTrue(result >= 0);
        }


    @Test
    public void test_special_characters_in_title() {
        int result = db.addBook("9783161484100", "Test@Title!", "Test Author", 2023, true);
        assertEquals(0, result);
    }
}
