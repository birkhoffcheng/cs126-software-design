import com.google.gson.Gson;
import org.junit.Test;
import static org.junit.Assert.*;
public class TestRoom {
	@Test
	public void testGetItems() throws Exception {
		String inputJson = "{\"name\":\"Entrance\",\"description\":\"You are at the entrance\",\"directions\":" +
				"[{\"directionName\":\"Right\",\"room\":\"Stairs\",\"enabled\":true,\"validKeyNames\":" +
				"[\"Lockpick\",\"Key2\"]}],\"items\":[{\"name\":\"Key2\"},{\"name\":\"Teleporter\"}]}";
		Gson gson = new Gson();
		Room room = gson.fromJson(inputJson, Room.class);
		String expectedJson = "[{\"name\":\"Key2\"},{\"name\":\"Teleporter\"}]";
		assertEquals(expectedJson, gson.toJson(room.getItems()));
	}

	@Test
	public void testRemoveItem() throws Exception {
		String inputJson = "{\"name\":\"Entrance\",\"description\":\"You are at the entrance\",\"directions\":" +
				"[{\"directionName\":\"Right\",\"room\":\"Stairs\",\"enabled\":true,\"validKeyNames\":" +
				"[\"Lockpick\",\"Key2\"]}],\"items\":[{\"name\":\"Key2\"},{\"name\":\"Teleporter\"}]}";
		Gson gson = new Gson();
		Room room = gson.fromJson(inputJson, Room.class);
		String expectedJson = "{\"name\":\"Key2\"}";
		assertEquals(expectedJson, gson.toJson(room.removeItem("Key2")));
	}
}
