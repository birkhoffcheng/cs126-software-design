package mineopoly;

import mineopoly.game.GameEngine;
import mineopoly.graphics.UserInterface;
import mineopoly.replay.Replay;
import mineopoly.replay.ReplayIO;
import mineopoly.strategy.*;
import mineopoly.competition.*;

import javax.swing.*;

public class MineopolyMain {
    private static final int DEFAULT_BOARD_SIZE = 20;
    private static final boolean DEFAULT_GUI_ENABLED = true; // Turn the GUI on or off when running through main method
    private static final int PREFERRED_GUI_WIDTH = 750; // Bump this up or down according to your screen size

    // Use this if you want to view a past match replay
    private static final String savedReplayFilePath = null;
    // Use this to save a replay of the current match
    private static final String replayOutputFilePath = null;

    public static void main(String[] args) {
        final GameEngine gameEngine;

        if(savedReplayFilePath == null) {
            MinePlayerStrategy redStrategy =  new MyStrategy();
            MinePlayerStrategy blueStrategy = new Omnibot();
            gameEngine = new GameEngine(DEFAULT_BOARD_SIZE, redStrategy, blueStrategy);
            gameEngine.setGuiEnabled(DEFAULT_GUI_ENABLED);
        } else {
            gameEngine = ReplayIO.setupEngineForReplay(savedReplayFilePath);
        }

        if(gameEngine == null) {
            return;
        }

        if(gameEngine.isGuiEnabled()) {
            // 500 is around the minimum value that keeps everything on screen
            assert PREFERRED_GUI_WIDTH >= 500;
            // Run the GUI code on a separate Thread (The event dispatch thread)
            SwingUtilities.invokeLater(() -> UserInterface.instantiateGUI(gameEngine, PREFERRED_GUI_WIDTH));
        }
        gameEngine.runGame();

        // Record the replay if the output path isn't null and we aren't already watching a replay
        if(savedReplayFilePath == null && replayOutputFilePath != null) {
            Replay gameReplay = gameEngine.getReplay();
            ReplayIO.writeReplayToFile(gameReplay, replayOutputFilePath);
        }
    }
}
