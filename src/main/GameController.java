package main;

public class GameController {

	private PlayerController[] playerController;
	private BoardController boardController;
	private int turn;
	private boolean gameOver = false;

	public GameController(PlayerController[] playerController, int rows, int columns) {
		this.playerController = playerController;
		this.boardController = new BoardController(rows, columns);
		this.turn = pickFirstPlayer();
	}

	private int pickFirstPlayer() {
		Double rand = Math.random() * playerController.length;
		return rand.intValue();
	}

	public void play() {
		do {
			int selectedColumn;
			int tryMoveResponse;

			// Print the board
			boardController.printBoard(playerController);

			// Print the prompt
			System.out.print("Your turn, " + playerController[turn].getPlayer().getName() + " ("
					+ playerController[turn].getPlayer().getToken() + "). Pick a column (1-" + boardController.width()
					+ "): ");

			// Handle the input
			selectedColumn = Utilities.makeUserInputANumber() - 1;

			// Get and interpret the response
			tryMoveResponse = handleTryMoveResponse(
					boardController.tryMove(playerController[turn].getPlayer(), selectedColumn));

			if (tryMoveResponse == 1) {
				// If the game is over, change the boolean so the program can exit
				gameOver = true;
			} else if (tryMoveResponse == 0) {
				// If the move was valid, swap turns
				incrementOrResetTurn();
			}

		} while (!gameOver);

	}

	public int handleTryMoveResponse(int response) {
		// The return value of this function is 0 for continue, -1 for retry, and 1 win
		// or draw detected.
		int retVal;

		System.out.println();

		if (response == 0) {
			// Game continues
			retVal = 0;
		} else if (response == -3) {
			System.out.println("Sorry, but that column is full.");
			retVal = -1;
		} else if (response == -2) {
			System.out.println("Sorry, but that isn't a valid column selection.");
			retVal = -1;
		} else if (response == -1) {
			System.out.println("Looks like a draw to me. Game over, losers.");
			retVal = 1;
		} else {
			displayWinMessage(response);
			retVal = 1;
		}

		System.out.println();
		return retVal;
	}

	private void displayWinMessage(int winner) {
		boardController.printBoard(playerController);
		System.out.println();
		System.out.println("Congratulations on your win, " + playerController[winner - 1].getPlayer().getName() + "!");
	}

	private void incrementOrResetTurn() {
		if (turn == (playerController.length - 1)) {
			turn = 0;
		} else {
			turn += 1;
		}
	}
}