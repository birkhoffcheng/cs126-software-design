package mineopoly.replay;

import mineopoly.game.Economy;
import mineopoly.game.TurnAction;
import mineopoly.item.InventoryItem;
import mineopoly.strategy.PlayerBoardView;
import mineopoly.strategy.MinePlayerStrategy;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ReplayStrategy implements MinePlayerStrategy {
    private Replay gameToReplay;
    private Queue<TurnAction> actionsToReplay;
    private boolean exceptionThrown;
    private boolean isRedPlayer;

    public ReplayStrategy(Replay gameToReplay) {
        this.gameToReplay = gameToReplay;
    }

    @Override
    public void initialize(int boardSize, int maxInventorySize, int winningScore, Point startTileLocation,
                           boolean isRedPlayer, Random random) {
        if(isRedPlayer) {
            actionsToReplay = new LinkedList<>(gameToReplay.getRedPlayerActions());
            exceptionThrown = gameToReplay.redThrewException();
        } else {
            actionsToReplay = new LinkedList<>(gameToReplay.getBluePlayerActions());
            exceptionThrown = gameToReplay.blueThrewException();
        }

        this.isRedPlayer = isRedPlayer;
    }

    @Override
    public TurnAction getTurnAction(PlayerBoardView boardView, Economy economy, boolean isRedTurn) {
        if(actionsToReplay.isEmpty() && exceptionThrown) {
            String exceptionPlayer = isRedPlayer ? "Red" : "Blue";
            throw new RuntimeException("An exception from the " + exceptionPlayer + " Player happened on this turn");
        }

        return actionsToReplay.poll();
    }

    @Override
    public String getName() {
        return "ReplayBot";
    }

    @Override
    public void onReceiveItem(InventoryItem itemReceived) {
        // Don't care
    }

    @Override
    public void onSoldInventory(int totalSellPrice) {
        // Doesn't matter
    }

    @Override
    public void endRound(int totalRedPoints, int totalBluePoints) {
        // Don't need it
    }
}
