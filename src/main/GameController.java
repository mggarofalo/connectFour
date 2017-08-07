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
		// Print prompt and new board
		printNewBoardAndPrompt();

		// Get input and try to make the move, forcing success
		return forceSuccessfulMove(getInputAndTryToMove());
	}

	private int handleAIMove() {
		// Print a stub for the line describing the move
		Utilities.print(game.getCurrentPlayerName() + " (" + game.getCurrentPlayerToken() + ") played");

		// Check the board for winning runs that could be extended or blocked with the
		// next move.
		ArrayList<ThreatMove> threatMoves = BoardChecker.findMovesThatExtendRun();

		// If there are any extending moves possible, let's handle one of those first
		if (threatMoves != null) {
			for (int i = (game.getWinLength() - 1); i > 0; i--) {
				// Iterate down through the lengths, prioritizing the extending the AI's runs
				// over blocking opponents'. First we play to win, then to block a win, then to
				// extend our longest run or block an opponent's longest run

				for (int j = 0; j <= 1; j++) {
					// The first loop here is to look for opportunities of the outer-loop length
					// that belong to the current player. The second is to look for opportunities
					// that belong to an opponent
					boolean belongsToCurrentPlayer = true;

					for (ThreatMove threatMove : threatMoves) {
						// Skip the move if it's not the length we're looking for or if its owner is
						// null
						if (threatMove.severity != i || threatMove.runOwner == null) {
							continue;
						}

						// If it matches our boolean, let's make the move
						if (belongsToCurrentPlayer == (threatMove.runOwner.index == game.getCurrentPlayerController()
								.getIndex())) {
							return makeAIMove(threatMove, belongsToCurrentPlayer);
						}

						belongsToCurrentPlayer = false;
					}
				}
			}
		}

		ArrayList<BoardSquare> playableSquares = game.boardController.getPlayableSquares();
		BoardSquare chosenSquare = playableSquares.get(ConnectFour.rand.nextInt(playableSquares.size()));

		// Since this is a simple AI, we're just going to pick a random move if there
		// isn't a block-win-extend opportunity
		Utilities.println(" randomly in column " + (chosenSquare.col() + 1) + ".");

		return makeAIMove(chosenSquare);
	}

	private int makeAIMove(ThreatMove threatMove, boolean belongsToCurrentPlayer) {
		Utilities.print(" to ");

		if (threatMove.severity == (game.getWinLength() - 1)) {
			Utilities.print((belongsToCurrentPlayer ? "win" : "block"));
		} else {
			Utilities.print((belongsToCurrentPlayer ? "extend" : "block"));
			if (belongsToCurrentPlayer) {
				Utilities.print(" " + (game.getCurrentPlayerController().isFemale() ? "her" : "his") + " run of "
						+ threatMove.severity);
			} else {
				Utilities.print(" " + threatMove.runOwner.name + "\'s run of " + threatMove.severity);
			}
		}

		Utilities.println(" in column " + (threatMove.runExtendingSquare.col() + 1));

		return makeAIMove(threatMove.runExtendingSquare);
	}

	private int makeAIMove(BoardSquare square) {
		BoardMove move = new BoardMove(new Date(), game.getCurrentPlayerController().getPlayer(),
				game.boardController.getMoveForColumn(square.col()));
		game.boardController.makeMove(move);
		return BoardChecker.checkForWin(move);
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