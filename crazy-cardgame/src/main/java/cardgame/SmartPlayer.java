package cardgame;
import java.util.List;

public class SmartPlayer implements PlayerStrategy {
	private List<Card> cards;
	private Card.Rank rank2Match;
	private Card.Suit suit2Match;
	private int[] suitsNextPlayerPlayed;
	private List<Integer> opponentIds;

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
		suitsNextPlayerPlayed = new int[Card.Suit.values().length];
	}

	/**
	 * Called once at the beginning of o game to deal the player their initial cards.
	 *
	 * @param cards The initial list of cards dealt to this player
	 */
	@Override
	public void receiveInitialCards(List<Card> cards) {
		this.cards = cards;
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
		if (rank2Match != Card.Rank.EIGHT && (indexOfCard2Play = findRank(rank2Match)) >= 0) {
			return cards.remove(indexOfCard2Play);
		} else if ((indexOfCard2Play = findSuit(suit2Match)) >= 0) {
			return cards.remove(indexOfCard2Play);
		} else {
			return cards.remove(findRank(rank2Match));
		}
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
		return Card.Suit.values()[max(suitsNextPlayerPlayed)];
	}

	/**
	 * Called at the very beginning of this player's turn to give it context of what its opponents
	 * chose to do on each of their turns.
	 *
	 * @param opponentActions A list of what the opponents did on each of their turns
	 */
	@Override
	public void processOpponentActions(List<PlayerTurn> opponentActions) {
		if (opponentActions.size() < opponentIds.size()) {
			return;
		}

		Card cardNextPlayerPlayed = opponentActions.get(0).playedCard;
		if (cardNextPlayerPlayed != null) {
			suitsNextPlayerPlayed[cardNextPlayerPlayed.getSuit().ordinal()]++;
		}
	}

	/**
	 * Called before a game begins, to allow for resetting any state between games.
	 */
	@Override
	public void reset() {

	}

	private int findRank(Card.Rank rank) {
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).getRank() == rank) {
				return i;
			}
		}

		return -1;
	}

	private int findSuit(Card.Suit suit) {
		int maxIndex = -1;
		int maxPointValue = -1;
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).getSuit() == suit && cards.get(i).getPointValue() > maxPointValue) {
				maxIndex = i;
				maxPointValue = cards.get(i).getPointValue();
			}
		}

		return maxIndex;
	}

	private int max(int[] array) {
		if (array.length == 0) {
			return 0;
		}

		int maxIndex = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] > array[maxIndex]) {
				maxIndex = i;
			}
		}

		return maxIndex;
	}
}
