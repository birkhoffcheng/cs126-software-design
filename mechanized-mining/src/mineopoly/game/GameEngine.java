package mineopoly.game;

import mineopoly.item.ResourceType;
import mineopoly.replay.Replay;
import mineopoly.strategy.MinePlayerStrategy;
import mineopoly.strategy.PlayerBoardView;
import mineopoly.tiles.Tile;

import java.awt.Point;
import java.util.Observable;
import java.util.Random;

public class GameEngine extends Observable {
    private static final int MAX_TURNS_PER_GAME = 1000;
    private static final double TURNS_PER_SECOND = 20;

    private long randomSeed;
    private GameBoard board;
    private MinePlayer redPlayer;
    private MinePlayer bluePlayer;
    private Economy economy;
    private boolean guiEnabled;
    private int minScoreToWin;

    // Variables to greatly simplify exception flow logic
    private MinePlayer playerWhoThrewException;
    private Exception exceptionThrown;

    public GameEngine(int boardSize, MinePlayerStrategy redPlayerStrategy, MinePlayerStrategy bluePlayerStrategy) {
        this(boardSize, redPlayerStrategy, bluePlayerStrategy, System.currentTimeMillis());
    }

    public GameEngine(int boardSize, MinePlayerStrategy redPlayerStrategy, MinePlayerStrategy bluePlayerStrategy, long randomSeed) {
        // Generate a random GameBoard and set player start tiles
        this.setupEngineForGame(boardSize, randomSeed);

        Tile redStartTile = board.getTileAtLocation(board.getRedStartTileLocation());
        Tile blueStartTile = board.getTileAtLocation(board.getBlueStartTileLocation());
        this.redPlayer = new MinePlayer(redPlayerStrategy, redStartTile, economy, true);
        this.bluePlayer = new MinePlayer(bluePlayerStrategy, blueStartTile, economy, false);
        this.guiEnabled = false;
    }

    private void setupEngineForGame(int boardSize, long randomSeed) {
        this.randomSeed = randomSeed;
        WorldGenerator worldGenerator = new WorldGenerator(randomSeed);
        this.board = worldGenerator.generateBoard(boardSize);
        this.economy = new Economy(ResourceType.values());
        this.minScoreToWin = 30 * boardSize * boardSize;

        this.playerWhoThrewException = null;
        this.exceptionThrown = null;
    }

    /**
     * Allows the same GameEngine object to be used for multiple games
     *
     * @param newBoardSize The size of the new game board to be generated
     * @param newSeed The new random seed value for world generation and the strategies to use
     * @param swapPlayers If true, the last red player will be the next blue player and vice versa
     */
    public void reset(int newBoardSize, long newSeed, boolean swapPlayers) {
        this.setupEngineForGame(newBoardSize, newSeed);

        MinePlayerStrategy redPlayerStrategy = this.redPlayer.getStrategy();
        MinePlayerStrategy bluePlayerStrategy = this.bluePlayer.getStrategy();
        Tile redStartTile = board.getTileAtLocation(board.getRedStartTileLocation());
        Tile blueStartTile = board.getTileAtLocation(board.getBlueStartTileLocation());
        if (swapPlayers) {
            this.redPlayer = new MinePlayer(bluePlayerStrategy, redStartTile, economy, true);
            this.bluePlayer = new MinePlayer(redPlayerStrategy, blueStartTile, economy, false);
        } else {
            this.redPlayer = new MinePlayer(redPlayerStrategy, redStartTile, economy, true);
            this.bluePlayer = new MinePlayer(bluePlayerStrategy, blueStartTile, economy, false);
        }
    }

    public GameBoard getBoard() {
        return board;
    }

    public MinePlayer getRedPlayer() {
        return redPlayer;
    }

    public MinePlayer getBluePlayer() {
        return bluePlayer;
    }

    public Economy getEconomy() {
        return economy;
    }

    public int getRedPlayerScore() {
        return redPlayer.getScore();
    }

    public int getBluePlayerScore() {
        return bluePlayer.getScore();
    }

    public int getMinScoreToWin() {
        // The minimum score to immediately end the game without finishing 1000 turns
        return minScoreToWin;
    }

    public Exception getExceptionThrown() {
        return exceptionThrown;
    }

    public long getRandomSeed() {
        return randomSeed;
    }

    public boolean isGuiEnabled() {
        return this.guiEnabled;
    }

    public void setGuiEnabled(boolean guiEnabled) {
        this.guiEnabled = guiEnabled;
    }

    public Replay getReplay() {
        boolean redThrewException = (playerWhoThrewException == redPlayer);
        boolean blueThrewException = (playerWhoThrewException == bluePlayer);
        return new Replay(board.getSize(), randomSeed, redPlayer.getAllTurnActions(), bluePlayer.getAllTurnActions(),
                          redThrewException, blueThrewException);
    }

    /**
     * Runs through a round of Mine-opoly until either the maximum number of turns is reached
     *  or a player achieves the score needed to win. If either player strategy throws an exception at any time,
     *  that strategy will receive a score of -1 and the game will end
     */
    public void runGame() {
        // Wait a few seconds at the start for graphical components to load
        delayBetweenGuiFrames(2000);

        try {
            runGameLoop();
        } catch(Exception e) {
            // It's generally bad practice to catch generic Exceptions, but because a strategy can throw an exception
            // of any type, it's unavoidable here
            playerWhoThrewException.setScore(-1);
            this.exceptionThrown = e;

            // Let anything watching update
            setChanged();
            notifyObservers();
            e.printStackTrace();
        }
    }

    private void runGameLoop() {
        initializePlayer(redPlayer, true);
        initializePlayer(bluePlayer, false);

        int turnNumber = 0;
        boolean isRedTurn = true;
        MinePlayer firstPlayer;
        MinePlayer secondPlayer;
        boolean roundHasWinner = false;

        while (turnNumber < MAX_TURNS_PER_GAME && !roundHasWinner) {
            delayBetweenGuiFrames((long) (1000 / TURNS_PER_SECOND));

            if (isRedTurn) {
                firstPlayer = redPlayer;
                secondPlayer = bluePlayer;
            } else {
                firstPlayer = bluePlayer;
                secondPlayer = redPlayer;
            }

            processTurn(firstPlayer, secondPlayer, isRedTurn);
            processTurn(secondPlayer, firstPlayer, isRedTurn);
            board.update();
            economy.increaseDemand();

            isRedTurn = !isRedTurn;
            turnNumber++;
            roundHasWinner = (redPlayer.getScore() >= minScoreToWin) || (bluePlayer.getScore() >= minScoreToWin);

            // The state of the engine has changed, let anything observing it (like the GUI) know
            this.setChanged();
            this.notifyObservers();
        }

        // End the round
        System.out.println("Turns: " + turnNumber);
        int redPlayerScore = redPlayer.getScore();
        int bluePlayerScore = bluePlayer.getScore();
        playerWhoThrewException = redPlayer;
        redPlayer.getStrategy().endRound(redPlayerScore, bluePlayerScore);
        playerWhoThrewException = bluePlayer;
        bluePlayer.getStrategy().endRound(bluePlayerScore, redPlayerScore);
        playerWhoThrewException = null;
    }

    private void initializePlayer(MinePlayer playerToInitialize, boolean isRedPlayer) {
        playerWhoThrewException = playerToInitialize; // If an exception gets thrown, we know who did it

        int boardSize = board.getSize();
        int maxInventorySize = MinePlayer.MAX_ITEMS;
        Point playerStartLocation = playerToInitialize.getCurrentTile().getLocation();
        Point playerStartCopy = new Point(playerStartLocation.x, playerStartLocation.y);
        playerToInitialize.getStrategy().initialize(boardSize, maxInventorySize, minScoreToWin,
                                                    playerStartCopy, isRedPlayer, new Random(randomSeed));

        Point startTileLocation;
        if (playerToInitialize.isRedPlayer()) {
            startTileLocation = board.getRedStartTileLocation();
        } else {
            startTileLocation = board.getBlueStartTileLocation();
        }

        board.getTileAtLocation(startTileLocation).onEnter(playerToInitialize);
    }

    private void processTurn(MinePlayer currentPlayer, MinePlayer otherPlayer, boolean isRedTurn) {
        playerWhoThrewException = currentPlayer; // If an exception gets thrown, we know who did it

        PlayerBoardView boardView = board.convertToView(currentPlayer, otherPlayer);
        TurnAction playerAction = currentPlayer.getStrategy().getTurnAction(boardView, economy, isRedTurn);
        currentPlayer.addTurnAction(playerAction);

        if (playerAction == null) {
            return;
        }

        switch (playerAction) {
            case MOVE_UP: handleMove(currentPlayer, 0, 1);
                          break;
            case MOVE_DOWN: handleMove(currentPlayer, 0, -1);
                            break;
            case MOVE_RIGHT: handleMove(currentPlayer, 1, 0);
                             break;
            case MOVE_LEFT: handleMove(currentPlayer, -1, 0);
                            break;
            case PICK_UP:
            case MINE: handleActionOnPlayerTile(currentPlayer, playerAction);
                       break;
            default: System.err.println("Unhandled TurnAction: " + playerAction);
        }
    }

    private void handleMove(MinePlayer player, int xChange, int yChange) {
        Tile currentTile = player.getCurrentTile();
        Point playerLocation = currentTile.getLocation();

        int nextX = playerLocation.x + xChange;
        int nextY = playerLocation.y + yChange;
        boolean nextXInBounds = (nextX >= 0 && nextX < board.getSize());
        boolean nextYInBounds = (nextY >= 0 && nextY < board.getSize());
        if (!nextXInBounds || !nextYInBounds) {
            // Can't step outside the world
            return;
        }

        Tile nextTile = board.getTileAtLocation(nextX, nextY);
        if (nextTile.getPlayerOnTile() != null) {
            // Can't step on a tile if there's another player there
            return;
        }

        currentTile.onExit(player);
        nextTile.onEnter(player);
        player.setCurrentTile(nextTile);
    }

    private void handleActionOnPlayerTile(MinePlayer playerPerformingAction, TurnAction action) {
        Tile currentPlayerTile = playerPerformingAction.getCurrentTile();
        Tile tileAfterAction = currentPlayerTile.interact(playerPerformingAction, action);

        if (tileAfterAction != currentPlayerTile) {
            // Tile has changed as a result of the action
            board.setTileAtTileLocation(tileAfterAction);
            playerPerformingAction.setCurrentTile(tileAfterAction);
        }
    }

    private void delayBetweenGuiFrames(long millisecondsToWait) {
        if(!guiEnabled) {
            return;
        }

        try {
            Thread.sleep(millisecondsToWait);
        } catch (InterruptedException e) {
            System.err.println("Sleeping in between turns failed");
        }
    }
}
