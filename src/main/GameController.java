package main;

import java.util.ArrayList;
import java.util.Date;

public class GameController {
	public Game game;
	private String prompt;

	public GameController(PlayerController[] playerController, int rows, int columns, int winLength) {
		game = new Game(playerController, rows, columns, winLength);
	}

	public void play() {
		do {
			// Process move for the current player
			if (game.getCurrentPlayerController().isHuman()) {
				handleMoveResponse(handleHumanMove());
			} else {
				handleMoveResponse(handleAIMove());
			}
		} while (!game.gameOver);
	}

	private int handleHumanMove() {
		// Print any AI moves since the last human move
		printAIMoves();

		// Print prompt and new board
		printNewBoardAndPrompt();

		// Get input and try to make the move, forcing success
		return forceSuccessfulMove(getInputAndTryToMove());
	}

	private int handleAIMove() {
		// Check the board for runs of (winLength - 1) that could be extended or blocked
		// with the next move.
		Object[][] extendingMoves = BoardChecker.findMoveThatExtendsRun(game.getWinLength());

		// If there are any extending moves possible, let's handle one of those first
		if (extendingMoves.length > 0) {
			// If the current player has a run of 3, playing will win, so let's do that
			for (int i = 0; i < extendingMoves[2].length; i++) {
				if ((int) extendingMoves[2][i] == game.getCurrentPlayerController().getIndex()) {
					BoardMove move = new BoardMove(new Date(), game.getCurrentPlayerController().getPlayer(),
							game.boardController.getMoveForColumn(((BoardSquare) extendingMoves[0][0]).col()));
					game.boardController.makeMove(move);
					return BoardChecker.checkForWin(move);
				}
			}

			// If the current player doesn't have a winning move, play to block; if multiple
			// are found, play on the first one
			BoardMove move = new BoardMove(new Date(), game.getCurrentPlayerController().getPlayer(),
					game.boardController.getMoveForColumn(((BoardSquare) extendingMoves[0][0]).col()));
			game.boardController.makeMove(move);
			return BoardChecker.checkForWin(move);
		}

		// Find the move that will make the longest line where four are possible and
		// play on it.

		// Try blocking a single opponent token where four are possible.
		// continue;

		// Try playing in a random open space where four are possible.
		// continue;

		// If no such move is possible, play in a random open space.
		// continue;

		// Check the board for threats (open 2 or closed 3)
		// BoardSquare[] threat = BoardChecker.checkForThreats(game.boardController,
		// move);

		// Check the board for line extensions

		// Make the move
		// return tryToMove(move.col());
		return 0;
	}

	private void printAIMoves() {
		// If the last move was not played by a human player, display a message to this
		// user detailing all AI moves since the last human player move
		ArrayList<BoardMove> movesByAI = game.boardController.getLog().getLastAIMoves();
		if (movesByAI.size() > 0) {
			// Print a spacer
			Utilities.println();

			// Print the moves
			for (int i = 0; i < movesByAI.size(); i++) {
				Utilities.println(movesByAI.get(i).getPlayer().name + " played at "
						+ movesByAI.get(i).getBoardSquare().coordinatesReadable() + ".");
			}

			// Print a spacer
			Utilities.println();
		}
	}

	private void printNewBoardAndPrompt() {
		// Set up the prompt so that we can measure it for the spacer
		prompt = "Your turn, " + game.getCurrentPlayerName() + " (" + game.getCurrentPlayerToken()
				+ "). Pick a column (1-" + game.boardController.width() + "): ";

		// Print the space and new board
		printSpacer(prompt, "-");
		game.boardController.printBoard(game.playerController);

		// Print the prompt
		Utilities.print(prompt);
	}

	private void printSpacer(String s, String c) {
		for (int i = 0; i < (s.length() + String.valueOf(game.boardController.width() - 1).length()); i++) {
			Utilities.print(c);
		}
		Utilities.println();
	}

	private int getInputAndTryToMove() {
		// Make the user put in a positive number
		int selectedColumn = makeUserInputAValidColumn();

		// Create the square reference (returns null if the column is full)
		BoardSquare square = game.boardController.getMoveForColumn(selectedColumn);

		// If the square isn't playable, start over
		if (square == null || BoardChecker.isLegalMove(square) != 0) {
			Utilities.print("That column isn't playable. Try again: ");
			return getInputAndTryToMove();
		} else {
			// Make the move
			BoardMove move = new BoardMove(new Date(), game.getCurrentPlayerController().getPlayer(), square);
			game.boardController.makeMove(move);
			return BoardChecker.checkForWin(move);

		}
	}

	private int makeUserInputAValidColumn() {
		// Convert input to column index by subtracting 1
		int selectedColumn = Utilities.makeUserInputAPositiveNumber() - 1;

		if (!BoardChecker.isValidColumn(selectedColumn)) {
			Utilities.print("That column isn't valid for this board. Try again: ");
			selectedColumn = makeUserInputAValidColumn();
		}

		return selectedColumn;
	}

	private int forceSuccessfulMove(int moveResponse) {
		while (isInvalidMove(moveResponse)) {
			moveResponse = getInputAndTryToMove();
		}

		return moveResponse;
	}

	private boolean isInvalidMove(int moveResponse) {
		return (moveResponse == -2 || moveResponse == -3);
	}

	private void handleMoveResponse(int moveResponse) {
		if (moveResponse == 0) {
			// Check for a draw
			if (BoardChecker.isDraw()) {
				Utilities.println();
				Utilities.println("Looks like a draw to me. Game over, losers.");
				game.gameOver = true;
			}

			// If the move was valid and there's no draw, swap turns
			incrementOrResetTurn();
		} else {
			// If the game is over, change the boolean so the program can exit
			game.gameOver = true;
			displayWinMessage(moveResponse);
		}
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