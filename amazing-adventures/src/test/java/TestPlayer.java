import com.google.gson.Gson;
import org.junit.Test;
import static org.junit.Assert.*;
public class TestPlayer {
	@Test
	public void testGetItems() throws Exception {
		String inputJson = "{\"items\":[{\"name\":\"Lockpick\"}]}";
		Gson gson = new Gson();
		Player player = gson.fromJson(inputJson, Player.class);
		String expectedJson = "[{\"name\":\"Lockpick\"}]";
		assertEquals(expectedJson, gson.toJson(player.getItems()));
	}

	@Test
	public void testHasItem() throws Exception {
		String inputJson = "{\"items\":[{\"name\":\"Lockpick\"}]}";
		Gson gson = new Gson();
		Player player = gson.fromJson(inputJson, Player.class);
		assertEquals(true, player.hasItem("Lockpick"));
		assertEquals(false, player.hasItem("Unicorn"));
	}

	@Test
	public void testRemoveItem() throws Exception {
		String inputJson = "{\"items\":[{\"name\":\"Lockpick\"}]}";
		Gson gson = new Gson();
		Player player = gson.fromJson(inputJson, Player.class);
		String expectedJson = "{\"name\":\"Lockpick\"}";
		assertEquals(true, player.hasItem("Lockpick"));
		assertEquals(expectedJson, gson.toJson(player.removeItem("Lockpick")));
		assertEquals(false, player.hasItem("Lockpick"));
	}

	@Test
	public void testAddItem() throws Exception {
		String inputJson = "{\"items\":[{\"name\":\"Lockpick\"}]}";
		Gson gson = new Gson();
		Player player = gson.fromJson(inputJson, Player.class);
		String unicornJson = "{\"name\":\"Unicorn\"}";
		Item unicorn = gson.fromJson(unicornJson, Item.class);
		assertEquals(false, player.hasItem("Unicorn"));
		player.addItem(unicorn);
		assertEquals(true, player.hasItem("Unicorn"));
	}
}
