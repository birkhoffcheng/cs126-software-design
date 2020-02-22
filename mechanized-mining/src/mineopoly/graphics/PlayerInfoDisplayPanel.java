package mineopoly.graphics;

import mineopoly.game.MinePlayer;
import mineopoly.item.InventoryItem;

import java.awt.*;
import java.util.List;

public class PlayerInfoDisplayPanel extends GameInfoDisplayPanel {
    private static final Color defaultRedFontColor = new Color(179, 0, 0);
    private static final Color defaultBlueFontColor = new Color(0, 0, 179);
    private static final int RESOURCE_IMAGE_SIZE = 64;

    private MinePlayer player;
    private String playerName;

    public PlayerInfoDisplayPanel(int preferredWidth, MinePlayer player, ImageManager imageManager) {
        super(preferredWidth, imageManager);
        this.player = player;
        this.playerName = getPlayerName(player);
        this.imageManager.rescaleImages(RESOURCE_IMAGE_SIZE, RESOURCE_IMAGE_SIZE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D brush = (Graphics2D) g;
        if (player.isRedPlayer()) {
            this.drawPlayerScores(brush, defaultRedFontColor, true);
        } else {
            this.drawPlayerScores(brush, defaultBlueFontColor, false);
        }
    }

    private void drawPlayerScores(Graphics2D brush, Color fontColor, boolean isRightAligned) {
        String playerScore = String.valueOf(player.getScore());

        brush.setFont(new Font("TimesRoman", Font.PLAIN, 32));
        brush.setColor(fontColor);

        if (isRightAligned) {
            FontMetrics fontMetrics = brush.getFontMetrics();
            int playerNameWidth = fontMetrics.stringWidth(playerName);
            int scorePixelWidth = fontMetrics.stringWidth(playerScore);

            // Red player info is aligned to the right
            brush.drawString(playerName, this.getWidth() - playerNameWidth - 10, 9 * this.getHeight() / 32);
            brush.drawString(playerScore, this.getWidth() - scorePixelWidth - 10, 19 * this.getHeight() / 32);

        } else {
            // Blue player info is aligned to the left
            brush.setColor(fontColor);
            brush.drawString(playerName, 10, 9 * this.getHeight() / 32);
            brush.drawString(playerScore, 10, 19 * this.getHeight() / 32);
        }

        // Can't use a for-each loop here because another thread may modify the inventory
        int itemXOffset = -8;
        List<InventoryItem> playerInventory = player.getInventory();
        for (int inventoryIndex = 0; inventoryIndex < player.getInventory().size(); inventoryIndex++) {
            InventoryItem currentItem = playerInventory.get(inventoryIndex);

            Image itemImage = imageManager.getScaledImage(currentItem.getItemType().getItemImageName());
            int itemXPosition = (isRightAligned) ? (this.getWidth() - itemXOffset - RESOURCE_IMAGE_SIZE) : (itemXOffset);
            brush.drawImage(itemImage, itemXPosition, 16 * this.getHeight() / 32, null);
            itemXOffset += 36;
        }
    }

    private String getPlayerName(MinePlayer player) {
        try {
            return player.getStrategy().getName().trim();
        } catch(Exception e) {
            // If you thrown an exception getting your name, that's just your name now
            return e.getClass().getSimpleName();
        }
    }
}
