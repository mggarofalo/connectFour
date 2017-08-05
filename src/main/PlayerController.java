package main;

public class PlayerController {

	private Player player;

	public PlayerController() {
	}

	public PlayerController(String name, String token, int index, boolean isHuman) {
		this.player = new Player(name, token, index, isHuman);
	}

	public PlayerController(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return this.player;
	}

	public String getName() {
		return this.player.name;
	}

	public String getToken() {
		return this.player.token;
	}

	public int getIndex() {
		return this.player.index;
	}

	public boolean isHuman() {
		return this.player.isHuman;
	}
}