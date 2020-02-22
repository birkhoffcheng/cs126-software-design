import java.util.ArrayList;
public class Room {
	private String name;
	private String description;
	private Direction[] directions;
	private ArrayList<Item> items;

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Item[] getItems() {
		Item[] itemsArray = new Item[items.size()];
		return items.toArray(itemsArray);
	}

	public Direction[] getDirections() {
		return directions;
	}

	public void addItem(Item item) {
		items.add(item);
	}

	public Item removeItem(String itemName) {
		for (int i = 0; i < items.size(); i++) {
			if (itemName.equalsIgnoreCase(items.get(i).getName())) {
				return items.remove(i);
			}
		}
		return null;
	}
}