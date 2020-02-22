import java.util.ArrayList;
public class Player {
	private ArrayList<Item> items;

	public Item[] getItems() {
		Item[] itemsArray = new Item[items.size()];
		return items.toArray(itemsArray);
	}

	public boolean hasItem(String itemName) {
		for (int i = 0; i < items.size(); i++) {
			if (itemName.equalsIgnoreCase(items.get(i).getName())) {
				return true;
			}
		}

		return false;
	}

	public Item removeItem(String itemName) {
		for (int i = 0; i < items.size(); i++) {
			if (itemName.equalsIgnoreCase(items.get(i).getName())) {
				return items.remove(i);
			}
		}
		return null;
	}

	public void addItem(Item item) {
		items.add(item);
	}
}