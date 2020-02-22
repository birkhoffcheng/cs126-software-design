import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * DON'T PUT YOUR TESTS IN THIS CLASS
 * Please make your own testing classes for your code
 */
public class DataTest {

    @Test
    public void canReadFileFromFolder() throws Exception {
        String testFileContents = Data
            .getFileContents("src", "test", "test_resources", "test_file_contents.txt");
        assertEquals("Gone, reduced to atoms", testFileContents);
    }
}
