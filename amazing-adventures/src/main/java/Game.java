import com.google.gson.Gson;
import java.util.*;

public class Game {
	private Layout adventure;
	private HashMap<String, Room> roomMap = new HashMap<String, Room>();

	public Game(String json) {
		Gson gson = new Gson();
		adventure = gson.fromJson(json, Layout.class);
		if (adventure == null) {
			System.out.println("Invalid JSON file or NULL object.");
			System.exit(1);
		}
		for (Room r : adventure.getRooms()) {
			roomMap.put(r.getName(), r);
		}
	}

	public void startGame() {
		// Keep currentRoom as a local variable to prevent tampering.
		Room currentRoom = roomMap.get(adventure.getStartingRoom());
		Scanner scanner = new Scanner(System.in);
		while (!currentRoom.getName().equalsIgnoreCase(adventure.getEndingRoom())) {
			printDescription(currentRoom);
			String inputString = "";
			if (scanner.hasNextLine()) {
				inputString = scanner.nextLine();
			} else {
				scanner.close();
				return;
			}
			String[] input = inputString.split("\\s+");

			if (input.length == 1) {
				if (input[0].equalsIgnoreCase("quit") || input[0].equalsIgnoreCase("exit")) {
					scanner.close();
					return;
				} else if (input[0].equalsIgnoreCase("examine")) {

				} else {
					System.out.println("I don't understand '" + inputString + "'");
				}
			} else if (input.length > 1) {
				if (input[0].equalsIgnoreCase("go")) {
					currentRoom = goToRoom(currentRoom, inputString.substring(3));
				} else if (input[0].equalsIgnoreCase("pickup")) {
					pickup(currentRoom, inputString.substring(7));
				} else if (input[0].equalsIgnoreCase("drop")) {
					drop(currentRoom, inputString.substring(5));
				} else if (input[0].equalsIgnoreCase("use")) {
					use(currentRoom, input);
				} else if (input[0].equalsIgnoreCase("teleport") && input[1].equalsIgnoreCase("to") && input.length > 2) {
					currentRoom = teleport(currentRoom, input[2]);
				} else {
					System.out.println("I don't understand '" + inputString + "'");
				}
			} else {
				System.out.println("I don't understand '" + inputString + "'");
			}
		}

		System.out.println("You've won!\nYou've reached " + adventure.getEndingRoom());
		scanner.close();
	}

	private Room teleport(Room currentRoom, String destination) {
		Room destinationRoom;
		if (adventure.getPlayer().hasItem("Teleporter") || adventure.getPlayer().hasItem("MagicWand")) {
			if ((destinationRoom = roomMap.get(destination)) != null) {
				System.out.println("Teleportation succeeded! You are now in " + destination);
				return destinationRoom;
			} else {
				System.out.println("Teleportation failed! Destination " + destination + " invalid.");
				return currentRoom;
			}
		} else {
			System.out.println("Teleportation failed! You don't have a teleporter");
			return currentRoom;
		}
	}

	private void use(Room currentRoom, String[] args) {
		if (args.length < 4) {
			System.out.println("use format: use <Item> with <Direction>");
			return;
		}

		if (adventure.getPlayer().hasItem(args[1])) {
			Direction[] directions = currentRoom.getDirections();
			for (int i = 0; i < directions.length; i++) {
				if (args[3].equalsIgnoreCase(directions[i].getDirectionName())) {
					if (directions[i].enable(args[1])) {
						System.out.println("Successfully unlocked " + args[3]);
					} else {
						System.out.println("Unlock failed: wrong key.");
					}

					return;
				}
			}

			System.out.println("Direction " + args[3] + " not found.");
		} else {
			System.out.println("You don't have " + args[1]);
		}
	}

	private void drop(Room currentRoom, String itemName) {
		Item item;
		if ((item = adventure.getPlayer().removeItem(itemName)) != null) {
			currentRoom.addItem(item);
			System.out.println("Dropped " + itemName);
		} else {
			System.out.println("I can't drop " + itemName + ". Item doesn't exist.");
		}
	}

	private void pickup(Room currentRoom, String itemName) {
		Item item;
		if ((item = currentRoom.removeItem(itemName)) != null) {
			adventure.getPlayer().addItem(item);
			System.out.println("Picked up " + itemName);
		} else {
			System.out.println("I can't pickup " + itemName + ". Item doesn't exist.");
		}
	}

	private Room goToRoom(Room currentRoom, String direction) {
		for (Direction d : currentRoom.getDirections()) {
			if (direction.equalsIgnoreCase(d.getDirectionName())) {
				if (d.isEnabled()) {
					return roomMap.getOrDefault(d.getRoom(), currentRoom);
				} else {
					System.out.println("I can't go " + direction + " because this direction is locked. Use one of the following items to unlock:");
					for (String k : d.getValidKeyNames()) {
						System.out.print(k + ", ");
					}
					System.out.println();
					return currentRoom;
				}
			}
		}

		System.out.println("I can't go '" + direction + "'");
		return currentRoom;
	}

	private void printDescription(Room currentRoom) {
		System.out.println(currentRoom.getDescription());

		System.out.print("You can see ");
		for (Item i : currentRoom.getItems()) {
			System.out.print(i.getName() + ", ");
		}

		System.out.print("\nFrom here, you can go: ");
		for (Direction d : currentRoom.getDirections()) {
			System.out.print(d.getDirectionName() + ", ");
		}

		System.out.print("\nAnd you have: ");
		for (Item i : adventure.getPlayer().getItems()) {
			System.out.print(i.getName() + ", ");
		}

		System.out.print("\n> ");
	}
}
