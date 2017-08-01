package main;

public class Player {

	private String name;
	private String token;
	private int index;
	private boolean isHuman;

	public Player(String name, String token, int index, boolean isHuman) {
		this.name = name;
		this.token = token;
		this.index = index;
		this.isHuman = isHuman;
	}

	public String getName() {
		return name;
	}

	public String getToken() {
		return token;
	}

	public int getIndex() {
		return index;
	}

	public boolean isHuman() {
		return isHuman;
	}
}