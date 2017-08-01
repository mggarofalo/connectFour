package main;

public class PlayerController {

	private Player player;

	public PlayerController() {
	}

	public PlayerController(String name, String token, int index, boolean isHuman) {
		this.player = new Player(name, token, index, isHuman);
	}

	public Player getPlayer() {
		return this.player;
	}
}