package cardgame;

public class Main {
	public static void main(String[] args) {
		int numPlayers = 4;
		System.out.println("Starting a tournament with " + numPlayers + " players.");
		PlayerStrategy[] players = new PlayerStrategy[numPlayers];
		for (int i = 0; i < numPlayers - 1; i++) {
			players[i] = new StandardPlayer();
		}
		players[numPlayers - 1] = new SmarterPlayer();

		Tournament tournament = new Tournament(players);
		int winner = tournament.play();
		System.out.println("Player " + winner + " has won!");
	}
}
