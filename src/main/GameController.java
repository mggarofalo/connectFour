package main;

import java.util.ArrayList;

public class GameController {
	private Game game;
	private String prompt;

	public GameController(PlayerController[] playerController, int rows, int columns, int winLength) {
		game = new Game(playerController, rows, columns, winLength);
	}

	public void play() {
		do {
			if (game.getCurrentPlayer().isHuman()) {
				handleHumanMove();
			} else {
				handleAIMove();
			}
		} while (!game.gameOver);
	}

	private void handleHumanMove() {
		// Initialize the response variable
		int tryMoveResponse;

		// If the last move was not played by a human player, display a message to this
		// user detailing all AI moves since the last human player move
		ArrayList<BoardMove> movesByAI = game.boardController.readLog().getLastAIMoves();
		if (movesByAI.size() > 0) {
			// Print a spacer
			Utilities.println();

			// Print the moves
			for (int i = 0; i < movesByAI.size(); i++) {
				Utilities.println(movesByAI.get(i).getPlayer().getName() + " played at "
						+ movesByAI.get(i).getBoardSquare().coordinatesReadable() + ".");
			}

			// Print a spacer
			Utilities.println();
		}

		// Set up the prompt so that we can measure it for the spacer
		prompt = "Your turn, " + game.getCurrentPlayerName() + " (" + game.getCurrentPlayerToken()
				+ "). Pick a column (1-" + game.boardController.width() + "): ";

		// Print the space and new board
		printSpacer(prompt, "-");
		game.boardController.printBoard(game.playerController);

		// Print the prompt
		Utilities.print(prompt);

		// Get the input and try to move
		tryMoveResponse = handleTryMoveErrors(getInputAndTryToMove());

		// Handle successful move
		game.gameOver = handleSuccessfulMove(tryMoveResponse);
	}

	private void handleAIMove() {
		// Initialize the response variable
		int tryMoveResponse;

		BoardSquare move = new BoardSquare(0, 0);
		// Check the board for threats (open 2 or closed 3 where four are possible). If
		// multiple are found, pick a random one and play on it to block.
		// continue;

		// Find the move that will make the longest line where four are possible and
		// play on it.
		// continue;

		// Try blocking a single opponent token where four are possible.
		// continue;

		// Try playing in a random open space where four are possible.
		// continue;

		// If no such move is possible, play in a random open space.
		// continue;

		// Check the board for threats (open 2 or closed 3)
		BoardSquare[] threat = BoardChecker.checkForThreats(game.boardController, move);

		// Check the board for line extensions

		// Try the move
		tryMoveResponse = handleTryMoveErrors(game.boardController.tryMove(game.getCurrentPlayer(), move.col()));

		game.gameOver = handleSuccessfulMove(tryMoveResponse);
	}

	private void printSpacer(String s, String c) {
		for (int i = 0; i < (s.length() + String.valueOf(game.boardController.width() - 1).length()); i++) {
			Utilities.print(c);
		}
		Utilities.println();
	}

	private int getInputAndTryToMove() {
		int selectedColumn = Utilities.makeUserInputAPositiveNumber() - 1;
		return game.boardController.tryMove(game.getCurrentPlayer(), selectedColumn);
	}

	private int handleTryMoveErrors(int tryMoveResponse) {
		while (isInvalidMove(tryMoveResponse)) {
			if (tryMoveResponse == -2) {
				Utilities.print("Sorry, but that isn't a valid column selection. Try again: ");
			} else if (tryMoveResponse == -3) {
				Utilities.print("Sorry, but that column is full. Try again: ");
			}

			tryMoveResponse = getInputAndTryToMove();
		}

		return tryMoveResponse;
	}

	private boolean isInvalidMove(int tryMoveResponse) {
		return (tryMoveResponse == -2 || tryMoveResponse == -3);
	}

	private boolean handleSuccessfulMove(int tryMoveResponse) {
		if (tryMoveResponse == 0) {
			// If the move was valid, swap turns
			incrementOrResetTurn();
		} else if (tryMoveResponse == -1) {
			// If there's a draw, let the players know
			Utilities.println("Looks like a draw to me. Game over, losers.");
		} else {
			// If the game is over, change the boolean so the program can exit
			game.gameOver = true;
			displayWinMessage(tryMoveResponse);
		}

		return game.gameOver;
	}

	private void incrementOrResetTurn() {
		if (game.turn == (game.playerController.length - 1)) {
			game.turn = 0;
		} else {
			game.turn += 1;
		}
	}

	private void displayWinMessage(int winner) {
		printSpacer(prompt, "=");
		game.boardController.printBoard(game.playerController);
		Utilities.println();
		Utilities.println("Congratulations on your win, " + game.getCurrentPlayerName() + "!");
	}
}