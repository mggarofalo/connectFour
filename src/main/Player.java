package main;

public class Player {

	String name;
	String token;
	int index;
	boolean isHuman;
	boolean isFemale;

	public Player(String name, String token, int index, boolean isHuman, boolean isFemale) {
		this.name = name;
		this.token = token;
		this.index = index;
		this.isHuman = isHuman;
		this.isFemale = isFemale;
	}
}