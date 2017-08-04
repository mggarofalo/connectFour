package main;

public class Game {

	public PlayerController[] playerController;
	public BoardController boardController;
	private final int winLength;
	public int turn;
	public boolean gameOver = false;

	public Game(PlayerController[] playerController, int rows, int columns, int winLength) {
		this.playerController = playerController;
		boardController = new BoardController(rows, columns, winLength);
		this.winLength = winLength;
		turn = pickFirstPlayer();
	}

	private int pickFirstPlayer() {
		Double rand = Math.random() * playerController.length;
		return rand.intValue();
	}

	public int getWinLength() {
		return winLength;
	}

	public PlayerController getCurrentPlayerController() {
		return playerController[turn];
	}

	public String getCurrentPlayerName() {
		return playerController[turn].getName();
	}

	public String getCurrentPlayerToken() {
		return playerController[turn].getToken();
	}

}