package mineopoly.graphics;

import mineopoly.game.GameBoard;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

/**
 * A panel on the JFrame for the main.game which can display the current GameBoard
 */
public class GameBoardDisplayPanel extends JPanel implements Observer {

    private GameBoard boardToRender;
    private ImageManager imageManager;
    private int imageWidth;
    private int imageHeight;
    private int preferredSize;

    public GameBoardDisplayPanel(int preferredSize, GameBoard board, ImageManager imageManager) {
        super();
        this.boardToRender = board;
        this.setLayout(new BorderLayout());
        this.setDoubleBuffered(true);
        this.setVisible(true);

        this.preferredSize = preferredSize;
        imageWidth = this.preferredSize / board.getSize();
        imageHeight = this.preferredSize / board.getSize();
        imageManager.rescaleImages(imageWidth, imageHeight);
        this.imageManager = imageManager;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        boardToRender.paint((Graphics2D) g, imageManager);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(preferredSize, preferredSize);
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
