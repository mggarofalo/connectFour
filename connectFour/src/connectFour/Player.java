package connectFour;

public class Player {

	private String name;
	private boolean isHuman;

	public Player(String name, boolean isHuman) {
		this.name = name;
		this.isHuman = isHuman;
	}

	public String getName() {
		return name;
	}

	public boolean isHuman() {
		return isHuman;
	}
}