package cardgame;
import org.junit.*;
import static org.junit.Assert.*;
public class PlayerTest {
	@Test
	public void testStandardPlayer() throws Exception {
		PlayerStrategy player = new StandardPlayer();
		int winner = testWhetherPlayerCheats(player);
		assertTrue(0 <= winner && winner < 4);
	}

	@Test
	public void testSmartPlayer() throws Exception {
		PlayerStrategy player = new SmartPlayer();
		int winner = testWhetherPlayerCheats(player);
		assertTrue(0 <= winner && winner < 4);
	}

	@Test
	public void testSmarterPlayer() throws Exception {
		PlayerStrategy player = new SmarterPlayer();
		int winner = testWhetherPlayerCheats(player);
		assertTrue(0 <= winner && winner < 4);
	}

	private int testWhetherPlayerCheats(PlayerStrategy player) throws Exception {
		int numPlayers = 4;
		PlayerStrategy[] players = new PlayerStrategy[numPlayers];
		for (int i = 0; i < numPlayers - 1; i++) {
			players[i] = new StandardPlayer();
		}
		players[numPlayers - 1] = player;

		Tournament tournament = new Tournament(players);
		return tournament.play();
	}
}
