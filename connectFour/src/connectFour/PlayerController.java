package connectFour;

public class PlayerController {

	private Player player;

	public PlayerController() {
	}

	public void initializePlayer(String name, boolean isHuman) {
		this.player = new Player(name, isHuman);
	}

	public Player getPlayer() {
		return this.player;
	}
}