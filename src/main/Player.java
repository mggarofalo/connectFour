package main;

public class Player {

	String name;
	String token;
	int index;
	boolean isHuman;

	public Player(String name, String token, int index, boolean isHuman) {
		this.name = name;
		this.token = token;
		this.index = index;
		this.isHuman = isHuman;
	}
}