package cardgame;
import java.util.ArrayList;
import java.util.List;

public class Tournament {
	private PlayerStrategy[] players;
	private int[] scores;
	private int winningScore;

	public Tournament(PlayerStrategy[] players) {
		this.players = players;
		scores = new int[players.length];
		winningScore = players.length * 50;
		for (int i = 0; i < players.length; i++) {
			List<Integer> opponentIds = new ArrayList<Integer>(players.length);
			for (int j = 0; j < players.length; j++) {
				opponentIds.add(j);
			}

			opponentIds.remove(i);
			players[i].init(i, opponentIds);
		}
	}

	public int play() {
		int winner = -1;
		int highestScore = 0;
		while (winner < 0) {
			for (int i = 0; i < players.length; i++) {
				players[i].reset();
			}

			Game game = new Game(players);
			int[] gameScore = game.play();
			for (int i = 0; i < scores.length && i < gameScore.length; i++) {
				scores[i] += gameScore[i];
				if (scores[i] >= winningScore) {
					if (scores[i] > highestScore) {
						winner = i;
						highestScore = scores[i];
					} else if (scores[i] == highestScore) {
						winner = -1;
					}
				}
			}
		}

		return winner;
	}
}
