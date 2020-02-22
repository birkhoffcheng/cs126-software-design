package mineopoly.item;

public class InventoryItem {
    private ResourceType itemType;

    public InventoryItem(ResourceType itemType) {
        this.itemType = itemType;
    }

    public ResourceType getItemType() {
        return itemType;
    }
}
