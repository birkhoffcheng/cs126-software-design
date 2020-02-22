public class Direction {
	private String directionName;
	private String room;
	private boolean enabled;
	private String[] validKeyNames;

	public boolean enable(String key) {
		for (String validKeyName : validKeyNames) {
			if (validKeyName.equalsIgnoreCase(key)) {
				enabled = true;
				break;
			}
		}
		return enabled;
	}

	public String getDirectionName() {
		return directionName;
	}

	public String getRoom() {
		return room;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public String[] getValidKeyNames() {
		return validKeyNames;
	}
}
