package main;

public class PlayerController {

	private Player player;

	public PlayerController() {
	}

	public PlayerController(String name, boolean isHuman) {
		this.player = new Player(name, isHuman);
	}

	public Player getPlayer() {
		return this.player;
	}
}