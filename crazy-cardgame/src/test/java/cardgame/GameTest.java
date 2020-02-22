package cardgame;
import org.junit.*;
import static org.junit.Assert.*;
public class GameTest {
	@Test
	public void test2Players() throws Exception {
		int numPlayers = 2;
		PlayerStrategy[] players = new PlayerStrategy[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			players[i] = new StandardPlayer();
		}
		Game game = new Game(players);
		int[] scores = game.play();
		assertEquals(numPlayers, scores.length);
		for (int i : scores) {
			assertTrue(i < numPlayers * 50);
			assertTrue(i >= 0);
		}
	}

	@Test
	public void test3Players() throws Exception {
		int numPlayers = 3;
		PlayerStrategy[] players = new PlayerStrategy[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			players[i] = new StandardPlayer();
		}
		Game game = new Game(players);
		int[] scores = game.play();
		assertEquals(numPlayers, scores.length);
		for (int i : scores) {
			assertTrue(i < numPlayers * 50);
			assertTrue(i >= 0);
		}
	}

	@Test
	public void test4Players() throws Exception {
		int numPlayers = 4;
		PlayerStrategy[] players = new PlayerStrategy[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			players[i] = new StandardPlayer();
		}
		Game game = new Game(players);
		int[] scores = game.play();
		assertEquals(numPlayers, scores.length);
		for (int i : scores) {
			assertTrue(i < numPlayers * 50);
			assertTrue(i >= 0);
		}
	}

	@Test
	public void test5Players() throws Exception {
		int numPlayers = 5;
		PlayerStrategy[] players = new PlayerStrategy[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			players[i] = new StandardPlayer();
		}
		Game game = new Game(players);
		int[] scores = game.play();
		assertEquals(numPlayers, scores.length);
		for (int i : scores) {
			assertTrue(i < numPlayers * 50);
			assertTrue(i >= 0);
		}
	}

	@Test
	public void test6Players() throws Exception {
		int numPlayers = 6;
		PlayerStrategy[] players = new PlayerStrategy[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			players[i] = new StandardPlayer();
		}
		Game game = new Game(players);
		int[] scores = game.play();
		assertEquals(numPlayers, scores.length);
		for (int i : scores) {
			assertTrue(i < numPlayers * 50);
			assertTrue(i >= 0);
		}
	}

	@Test
	public void test7Players() throws Exception {
		int numPlayers = 7;
		PlayerStrategy[] players = new PlayerStrategy[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			players[i] = new StandardPlayer();
		}
		Game game = new Game(players);
		int[] scores = game.play();
		assertEquals(numPlayers, scores.length);
		for (int i : scores) {
			assertTrue(i < numPlayers * 50);
			assertTrue(i >= 0);
		}
	}
}
