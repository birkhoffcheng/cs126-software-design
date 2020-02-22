package mineopoly.tiles;

import mineopoly.graphics.ImageManager;
import mineopoly.graphics.TileRenderLayer;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class CrackedTile extends StoneTile {
    public CrackedTile(Point tileLocation) {
        super(tileLocation);
    }

    @Override
    public TileType getType() {
        return TileType.EMPTY;
    }

    @Override
    protected Image[] getImageOverlays(ImageManager imageManager) {
        Image[] imageOverlays = super.getImageOverlays(imageManager);
        Image crackOverlay = imageManager.getScaledImage("crack_3");
        int crackLayerIndex = TileRenderLayer.LAYER_CRACK.ordinal();
        imageOverlays[crackLayerIndex] = crackOverlay;
        return imageOverlays;
    }
}
