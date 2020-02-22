package cardgame;
import java.util.*;

public class SmarterPlayer implements PlayerStrategy {
	private List<Card> cards;
	private Card.Rank rank2Match;
	private Card.Suit suit2Match;
	private int[] suitsPlayed;
	private int[] ranksPlayed;
	private HashMap<Integer, boolean[]> ranksPlayerDoesNotHave;
	private HashMap<Integer, boolean[]> suitsPlayerDoesNotHave;
	private HashMap<Integer, Integer> playerNumCards;
	private List<Integer> opponentIds;
	private Card discardTop;

	/**
	 * Gives the player their assigned id, as well as a list of the opponents' assigned ids.
	 *
	 * This method will be called by the game engine once at the very beginning (before any games
	 * are started), to allow the player to set up any initial state.
	 *
	 * @param playerId    The id for this player, assigned by the game engine
	 * @param opponentIds A list of ids for this player's opponents
	 */
	@Override
	public void init(int playerId, List<Integer> opponentIds) {
		this.opponentIds = opponentIds;
		suitsPlayed = new int[Card.Suit.values().length];
		ranksPlayed = new int[Card.Rank.values().length];
		playerNumCards = new HashMap<Integer, Integer>();
		ranksPlayerDoesNotHave = new HashMap<Integer, boolean[]>();
		for (int opponentId : opponentIds) {
			boolean[] ranksArray = new boolean[Card.Rank.values().length];
			ranksPlayerDoesNotHave.put(opponentId, ranksArray);
		}

		suitsPlayerDoesNotHave = new HashMap<Integer, boolean[]>();
		for (int opponentId : opponentIds) {
			boolean[] suitsArray = new boolean[Card.Suit.values().length];
			suitsPlayerDoesNotHave.put(opponentId, suitsArray);
		}
	}

	/**
	 * Called once at the beginning of o game to deal the player their initial cards.
	 *
	 * @param cards The initial list of cards dealt to this player
	 */
	@Override
	public void receiveInitialCards(List<Card> cards) {
		this.cards = cards;
		int numInitialCards = cards.size();
		for (int opponentId : opponentIds) {
			playerNumCards.put(opponentId, numInitialCards);
		}
	}

	/**
	 * Called to ask whether the player wants to draw this turn. Gives this player the top card of
	 * the discard pile at the beginning of their turn, as well as an optional suit for the pile in
	 * case a "8" was played, and the suit was changed.
	 * By having this return true, the game engine will then call receiveCard() for this player.
	 * Otherwise, playCard() will be called.
	 *
	 * @param topPileCard The card currently at the top of the pile
	 * @param changedSuit The suit that the pile was changed to as the result of an "8" being
	 *                    played. Will be null if no "8" was played.
	 * @return whether or not the player wants to draw
	 */
	@Override
	public boolean shouldDrawCard(Card topPileCard, Card.Suit changedSuit) {
		rank2Match = topPileCard.getRank();
		if (rank2Match == Card.Rank.EIGHT) {
			suit2Match = changedSuit;
		} else {
			suit2Match = topPileCard.getSuit();
		}
		discardTop = topPileCard;

		return findSuit(suit2Match) < 0 && findRank(rank2Match) < 0;
	}

	/**
	 * Called when this player has chosen to draw a card from the deck.
	 *
	 * @param drawnCard The card that this player has drawn
	 */
	@Override
	public void receiveCard(Card drawnCard) {
		cards.add(drawnCard);
	}

	/**
	 * Called when this player is ready to play a card (will not be called if this player drew on
	 * their turn).
	 *
	 * This will end this player's turn.
	 *
	 * @return The card this player wishes to put on top of the pile
	 */
	@Override
	public Card playCard() {
		int indexOfCard2Play;
		Card card2Play;
		if (rank2Match != Card.Rank.EIGHT && (indexOfCard2Play = findRank(rank2Match)) >= 0) {
			card2Play = cards.remove(indexOfCard2Play);
		} else if ((indexOfCard2Play = findSuit(suit2Match)) >= 0) {
			card2Play = cards.remove(indexOfCard2Play);
		} else {
			card2Play = cards.remove(findRank(rank2Match));
		}
		discardTop = card2Play;

		return card2Play;
	}

	/**
	 * Called if this player decided to play a "8" card to ask the player what suit they would like
	 * to declare.
	 *
	 * This player should then return the Card.Suit enum that it wishes to set for the discard
	 * pile.
	 */
	@Override
	public Card.Suit declareSuit() {
		int playerToTarget = playerWithLeastNumCards();
		boolean[] suitPlayerDoesNotHave = suitsPlayerDoesNotHave.get(playerToTarget);
		for (int i = 0; i < suitPlayerDoesNotHave.length; i++) {
			if (suitPlayerDoesNotHave[i]) {
				discardTop = new Card(Card.Suit.values()[i], Card.Rank.EIGHT);
				return Card.Suit.values()[i];
			}
		}

		int mostPlayedSuitIndex = max(suitsPlayed);
		discardTop = new Card(Card.Suit.values()[mostPlayedSuitIndex], Card.Rank.EIGHT);
		return Card.Suit.values()[mostPlayedSuitIndex];
	}

	/**
	 * Called at the very beginning of this player's turn to give it context of what its opponents
	 * chose to do on each of their turns.
	 *
	 * @param opponentActions A list of what the opponents did on each of their turns
	 */
	@Override
	public void processOpponentActions(List<PlayerTurn> opponentActions) {
		if (opponentActions == null || opponentActions.size() == 0) {
			return;
		}

		for (int i = 0; i < opponentActions.size(); i++) {
			PlayerTurn turn = opponentActions.get(i);
			if (turn.drewACard) {
				int numCards = playerNumCards.get(turn.playerId);
				playerNumCards.put(turn.playerId, numCards + 1);
				if (discardTop != null) {
					ranksPlayerDoesNotHave.get(turn.playerId)[discardTop.getRank().ordinal()] = true;
					suitsPlayerDoesNotHave.get(turn.playerId)[discardTop.getSuit().ordinal()] = true;
				}
			} else {
				suitsPlayed[turn.playedCard.getSuit().ordinal()]++;
				ranksPlayed[turn.playedCard.getRank().ordinal()]++;
				int numCards = playerNumCards.get(turn.playerId);
				playerNumCards.put(turn.playerId, numCards - 1);
				if (turn.playedCard.getRank() == Card.Rank.EIGHT && discardTop != null) {
					ranksPlayerDoesNotHave.get(turn.playerId)[discardTop.getRank().ordinal()] = true;
					suitsPlayerDoesNotHave.get(turn.playerId)[discardTop.getSuit().ordinal()] = true;
				}

				discardTop = turn.playedCard;
			}
		}
	}

	/**
	 * Called before a game begins, to allow for resetting any state between games.
	 */
	@Override
	public void reset() {

	}

	private int findRank(Card.Rank rank) {
		int maxSuitPlayed = -1;
		int maxIndex = -1;
		for (int i = 0; i < cards.size(); i++) {
			Card card = cards.get(i);
			if (card.getRank() == rank && suitsPlayed[card.getSuit().ordinal()] > maxSuitPlayed) {
				maxSuitPlayed = suitsPlayed[card.getSuit().ordinal()];
				maxIndex = i;
			}
		}

		return maxIndex;
	}

	private int findSuit(Card.Suit suit) {
		int maxRankPlayed = -1;
		int maxIndex = -1;
		for (int i = 0; i < cards.size(); i++) {
			Card card = cards.get(i);
			if (card.getSuit() == suit && ranksPlayed[card.getRank().ordinal()] > maxRankPlayed) {
				maxRankPlayed = ranksPlayed[card.getRank().ordinal()];
				maxIndex = i;
			}
		}

		return maxIndex;
	}

	private int playerWithLeastNumCards() {
		int playerId = opponentIds.get(0);
		int min = Integer.MAX_VALUE;
		for (int opponentId : opponentIds) {
			if (playerNumCards.get(opponentId) < min) {
				min = playerNumCards.get(opponentId);
				playerId = opponentId;
			}
		}

		return playerId;
	}

	private static int max(int[] array) {
		if (array.length == 0) {
			return 0;
		}

		int maxIndex = 0;
		int max = array[0];
		for (int i = 0; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
				maxIndex = i;
			}
		}

		return maxIndex;
	}
}
