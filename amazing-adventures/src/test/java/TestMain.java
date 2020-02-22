import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;

public class TestMain {
	@Before
	public void setup() {
		try {
			Runtime.getRuntime().exec("bash src/test/test_resources/setup.sh");
		} catch (IOException e) {
			System.exit(1);
		}
	}

	@Test
	public void testExit() throws Exception {
		Process process = Runtime.getRuntime().exec("bash src/test/test_resources/test_exit.sh");
		assertEquals(0, process.waitFor());
	}

	@Test
	public void testGo() throws Exception {
		Process process = Runtime.getRuntime().exec("bash src/test/test_resources/test_go.sh");
		assertEquals(0, process.waitFor());
	}

	@Test
	public void testExamine() throws Exception {
		Process process = Runtime.getRuntime().exec("bash src/test/test_resources/test_examine.sh");
		assertEquals(0, process.waitFor());
	}

	@Test
	public void testDontUnderstand() throws Exception {
		Process process = Runtime.getRuntime().exec("bash src/test/test_resources/test_dont_understand.sh");
		assertEquals(0, process.waitFor());
	}

	@Test
	public void testPickup() throws Exception {
		Process process = Runtime.getRuntime().exec("bash src/test/test_resources/test_pickup.sh");
		assertEquals(0, process.waitFor());
	}

	@Test
	public void testDrop() throws Exception {
		Process process = Runtime.getRuntime().exec("bash src/test/test_resources/test_drop.sh");
		assertEquals(0, process.waitFor());
	}

	@Test
	public void testUse() throws Exception {
		Process process = Runtime.getRuntime().exec("bash src/test/test_resources/test_use.sh");
		assertEquals(0, process.waitFor());
	}

	@Test
	public void testTeleport() throws Exception {
		Process process = Runtime.getRuntime().exec("bash src/test/test_resources/test_teleport.sh");
		assertEquals(0, process.waitFor());
	}

	@Test
	public void testCommandLineArguments() throws Exception {
		Process process = Runtime.getRuntime().exec("bash src/test/test_resources/test_cli.sh");
		assertEquals(0, process.waitFor());
	}
}