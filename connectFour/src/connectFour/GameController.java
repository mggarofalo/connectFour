package connectFour;

import java.util.Random;

public class GameController {

	private PlayerController[] playerController;
	private BoardController game;
	private int turn;

	public GameController(PlayerController[] playerController, int rows, int columns) {
		this.playerController = playerController;
		this.game = new BoardController(rows, columns);
		this.turn = pickFirstPlayer();
	}

	private int pickFirstPlayer() {
		Random r = new Random();
		return r.nextDouble() >= 0.5 ? 1 : 0;
	}
}