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

			// Get the input and try to move
			tryMoveResponse = getInputAndTryToMove();

			// Handle any errors
			while (tryMoveResponse == -2 || tryMoveResponse == -3) {
				if (tryMoveResponse == -2) {
					System.out.print("Sorry, but that isn't a valid column selection. Try again: ");
					tryMoveResponse = getInputAndTryToMove();
				} else if (tryMoveResponse == -3) {
					System.out.print("Sorry, but that column is full. Try again: ");
					tryMoveResponse = getInputAndTryToMove();
				}
			}

			// Handle successful move
			if (tryMoveResponse == 0) {
				// If the move was valid, swap turns
				incrementOrResetTurn();
			} else if (tryMoveResponse == -1) {
				// If there's a draw, let the players know
				System.out.println("Looks like a draw to me. Game over, losers.");
			} else {
				// If the game is over, change the boolean so the program can exit
				gameOver = true;
				displayWinMessage(tryMoveResponse);
			}

		} while (!gameOver);

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

	private int getInputAndTryToMove() {
		int selectedColumn = Utilities.makeUserInputANumber() - 1;
		return boardController.tryMove(playerController[turn].getPlayer(), selectedColumn);
	}
}