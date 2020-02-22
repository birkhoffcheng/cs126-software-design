import com.google.gson.Gson;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestDirection {
	@Test
	public void testGetDirectionName() throws Exception {
		Gson gson = new Gson();
		Direction direction = gson.fromJson("{\"directionName\":\"East\",\"room\":\"SiebelEntry\"}", Direction.class);
		assertEquals("East", direction.getDirectionName());
		direction = gson.fromJson("{\"directionName\":\"Up\",\"room\":\"Sky\"}", Direction.class);
		assertEquals("Up", direction.getDirectionName());
		direction = gson.fromJson("{\"directionName\":\"Left\",\"room\":\"Some room\"}", Direction.class);
		assertEquals("Left", direction.getDirectionName());
	}

	@Test
	public void testGetRoom() throws Exception {
		Gson gson = new Gson();
		Direction direction = gson.fromJson("{\"directionName\":\"East\",\"room\":\"SiebelEntry\"}", Direction.class);
		assertEquals("SiebelEntry", direction.getRoom());
		direction = gson.fromJson("{\"directionName\":\"Up\",\"room\":\"Sky\"}", Direction.class);
		assertEquals("Sky", direction.getRoom());
		direction = gson.fromJson("{\"directionName\":\"Left\",\"room\":\"Some room\"}", Direction.class);
		assertEquals("Some room", direction.getRoom());
	}

	@Test
	public void testEnable() throws Exception {
		Gson gson = new Gson();
		String inputJson = "{\"directionName\":\"Down\",\"room\":\"Basement\",\"enabled\":false,\"validKeyNames\":[\"Lockpick\",\"Key3\"]}";
		Direction direction = gson.fromJson(inputJson, Direction.class);
		assertEquals(false, direction.isEnabled());
		assertEquals(true, direction.enable("Key3"));
		assertEquals(true, direction.isEnabled());
	}
}
