package mineopoly.strategy;

import java.awt.*;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import mineopoly.game.TurnAction;
import mineopoly.item.InventoryItem;
import mineopoly.item.ResourceType;
import mineopoly.tiles.TileType;

public class MyStrategyTest {
	public static void main(String[] args) throws Exception {
		testGoToMarketBlue();
		testGoToMarketRed();
		testGetTurnActionPickup();
		testGetTurnActionMine();
	}
	
	public static void testGoToMarketBlue() throws Exception {
		MyStrategy myStrategy = new MyStrategy();
		Point location = new Point(10, 9);
		SecureRandom secureRandom = new SecureRandom();
		Random random = new Random(secureRandom.nextLong());
		myStrategy.initialize(20, 5, 12000, location, false, random);
		location.x = 0;
		location.y = 0;
		assert myStrategy.goToMarket(location) == TurnAction.MOVE_RIGHT;
		location.x = 18;
		assert myStrategy.goToMarket(location) == TurnAction.MOVE_LEFT;
		location.x = 10;
		location.y = 5;
		assert myStrategy.goToMarket(location) == TurnAction.MOVE_UP;
		location.y = 17;
		assert myStrategy.goToMarket(location) == TurnAction.MOVE_DOWN;
	}
	
	public static void testGoToMarketRed() throws Exception {
		MyStrategy myStrategy = new MyStrategy();
		Point location = new Point(9, 9);
		SecureRandom secureRandom = new SecureRandom();
		Random random = new Random(secureRandom.nextLong());
		myStrategy.initialize(20, 5, 12000, location, true, random);
		location.x = 0;
		location.y = 0;
		assert myStrategy.goToMarket(location) == TurnAction.MOVE_RIGHT;
		location.x = 18;
		assert myStrategy.goToMarket(location) == TurnAction.MOVE_LEFT;
		location.x = 9;
		location.y = 5;
		assert myStrategy.goToMarket(location) == TurnAction.MOVE_UP;
		location.y = 17;
		assert myStrategy.goToMarket(location) == TurnAction.MOVE_DOWN;
	}
	
	public static void testGetTurnActionPickup() throws Exception {
		MyStrategy myStrategy = new MyStrategy();
		Map<InventoryItem, Point> itemsOnGround = new HashMap<InventoryItem, Point>();
		Point location = new Point(1, 2);
		SecureRandom secureRandom = new SecureRandom();
		Random random = new Random(secureRandom.nextLong());
		myStrategy.initialize(20, 5, 12000, location, true, random);
		InventoryItem item = new InventoryItem(ResourceType.DIAMOND);
		itemsOnGround.put(item, location);
		int boardSize = 20;
		TileType[][] tilesOnBoard = new TileType[boardSize][boardSize];
		for (int i = 0; i < tilesOnBoard.length; i++) {
			for (int j = 0; j < tilesOnBoard[i].length; j++) {
				tilesOnBoard[i][j] = TileType.EMPTY;
			}
		}
		PlayerBoardView boardView = new PlayerBoardView(tilesOnBoard, itemsOnGround, location, new Point(0, 0), 0);
		assert myStrategy.getTurnAction(boardView, null, true) == TurnAction.PICK_UP;
	}
	
	public static void testGetTurnActionMine() throws Exception {
		MyStrategy myStrategy = new MyStrategy();
		Map<InventoryItem, Point> itemsOnGround = new HashMap<InventoryItem, Point>();
		Point location = new Point(1, 2);
		SecureRandom secureRandom = new SecureRandom();
		Random random = new Random(secureRandom.nextLong());
		myStrategy.initialize(20, 5, 12000, location, true, random);
		InventoryItem item = new InventoryItem(ResourceType.DIAMOND);
		int boardSize = 20;
		TileType[][] tilesOnBoard = new TileType[boardSize][boardSize];
		for (int i = 0; i < tilesOnBoard.length; i++) {
			for (int j = 0; j < tilesOnBoard[i].length; j++) {
				tilesOnBoard[i][j] = TileType.EMPTY;
			}
		}
		tilesOnBoard[1][2] = TileType.RESOURCE_DIAMOND;
		PlayerBoardView boardView = new PlayerBoardView(tilesOnBoard, itemsOnGround, location, new Point(0, 0), 0);
		assert myStrategy.getTurnAction(boardView, null, true) == TurnAction.MINE;
	}
}
