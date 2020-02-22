package cardgame;
import java.util.*;

public class Game {
	public static final int NUM_INITIAL_CARDS = 5;
	public static final int MAXIMUM_PLAYER_FOR_ONE_DECK = 5;
	private List<Card> deck;
	private List[] playerCards;
	private Card discardTop;
	private PlayerStrategy[] players;
	private Card.Suit currentSuit;
	private List<PlayerTurn> playerTurns;

	public Game(PlayerStrategy[] players) {
		this.players = players;
		deck = Card.getDeck();
		if (players.length > MAXIMUM_PLAYER_FOR_ONE_DECK) {
			deck.addAll(Card.getDeck());
		}

		Card.shuffle(deck);
		playerCards = dealInitialCards(deck, players);
		discardTop = deck.remove(0);
		while (discardTop.getRank() == Card.Rank.EIGHT) {
			deck.add(discardTop);
			Card.shuffle(deck);
			discardTop = deck.remove(0);
		}

		currentSuit = discardTop.getSuit();
		playerTurns = new ArrayList<PlayerTurn>();
	}

	public int[] play() {
		int winner = -1;
		while (deck.size() > 0 && winner < 0) {
			winner = playRound();
		}

		// Scoring
		int[] scores = new int[players.length];
		if (winner >= 0) {
			scores[winner] = playerScore(winner);
		} else {
			for (int i = 0; i < scores.length; i++) {
				scores[i] = playerScore(i);
			}
		}

		return scores;
	}

	private int playerScore(int playerId) {
		int score = 0;
		for (int i = 0; i < players.length; i++) {
			if (i != playerId) {
				score += sumPointValues(playerCards[i]);
			}
		}

		return score;
	}

	private int sumPointValues(List<Card> cardList) {
		int sum = 0;
		for (Card card : cardList) {
			sum += card.getPointValue();
		}

		return sum;
	}

	private int playRound() {
		for (int i = 0; i < players.length; i++) {
			Card cardDrawn = null;
			players[i].processOpponentActions(playerTurns);
			if (players[i].shouldDrawCard(discardTop, currentSuit)) {
				if (deck.size() == 0) {
					return -1;
				}
				cardDrawn = deck.remove(0);
				players[i].receiveCard(cardDrawn);
				playerCards[i].add(cardDrawn);
			} else {
				Card cardPlayed = players[i].playCard();
				if (cardPlayed == null || cardPlayed.getRank() != discardTop.getRank() && cardPlayed.getSuit() != currentSuit && cardPlayed.getRank() != Card.Rank.EIGHT) {
					System.out.println("Player " + i + " cheated by playing an invalid card!");
					System.exit(1);
				} else if (!playerCards[i].remove(cardPlayed)) {
					System.out.println("Player " + i + " cheated by playing a nonexistent card!");
					System.exit(1);
				} else {
					discardTop = cardPlayed;
				}

				if (playerCards[i].isEmpty()) {
					return i;
				}

				if (discardTop.getRank() == Card.Rank.EIGHT) {
					if ((currentSuit = players[i].declareSuit()) == null) {
						System.out.println("Player " + i + " cheated by declaring NULL!");
						System.exit(1);
					}
				} else {
					currentSuit = discardTop.getSuit();
				}
			}

			PlayerTurn playerTurn = constructPlayerTurn(i, cardDrawn, discardTop, currentSuit);
			if (playerTurns.size() >= players.length - 1) {
				playerTurns.remove(0);
			}
			playerTurns.add(playerTurn);
		}

		return -1;
	}

	private static PlayerTurn constructPlayerTurn(int id, Card cardDrawn, Card cardPlayed, Card.Suit declaredSuit) {
		PlayerTurn turn = new PlayerTurn();
		turn.playerId = id;
		turn.drewACard = cardDrawn != null;
		if (turn.drewACard) {
			turn.playedCard = null;
			turn.declaredSuit = null;
		} else {
			turn.playedCard = cardPlayed;
			if (cardPlayed.getRank() == Card.Rank.EIGHT) {
				turn.declaredSuit = declaredSuit;
			} else {
				turn.declaredSuit = null;
			}
		}

		return turn;
	}

	private static List[] dealInitialCards(List<Card> deck, PlayerStrategy[] players) {
		int numInitialCards = NUM_INITIAL_CARDS;
		if (players.length < 3) {
			numInitialCards += 2;
		}

		List[] playerCards = new List[players.length];
		for (int i = 0; i < players.length; i++) {
			playerCards[i] = new ArrayList<Card>();
			List<Card> initialCards = new ArrayList<Card>(numInitialCards);
			for (int j = 0; j < numInitialCards; j++) {
				Card dealtCard = deck.remove(0);
				playerCards[i].add(dealtCard);
				initialCards.add(dealtCard);
			}

			players[i].receiveInitialCards(initialCards);
		}

		return playerCards;
	}
}
