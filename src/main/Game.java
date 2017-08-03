package main;

public class Game {

	public PlayerController[] playerController;
	public BoardController boardController;
	public int turn;
	public boolean gameOver = false;

	public Game(PlayerController[] playerController, int rows, int columns, int winLength) {
		this.playerController = playerController;
		boardController = new BoardController(rows, columns, winLength);
		turn = pickFirstPlayer();
	}

	private int pickFirstPlayer() {
		Double rand = Math.random() * playerController.length;
		return rand.intValue();
	}

	public Player getCurrentPlayer() {
		return playerController[turn].getPlayer();
	}

	public String getCurrentPlayerName() {
		return playerController[turn].getPlayer().getName();
	}

	public String getCurrentPlayerToken() {
		return playerController[turn].getPlayer().getToken();
	}

}