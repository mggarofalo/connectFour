package connectFour;

public class GameCompleteChecker {

	// Checks for a straight-line win. We pass a boolean to indicate whether we want
	// to run it twice (vertical and horizontal) or just once (horizontal, after
	// we've just run vertical).
	public static int checkForWinStraight(Game game, Board board, boolean runTwice) {
		int counter = 0;
		BoardSquare focusSquare;
		int focusValue;
		int focusPlayer = -1;
		int winner = -1;

		// For each column, starting at the left
		for (int checkColumn = 0; checkColumn < board.width(); checkColumn++) {
			// Initialize the focusSquare for each column
			focusSquare = new BoardSquare(checkColumn, 0);
			focusValue = board.readBoardSquare(focusSquare);

			// If we're looking at a played space, let's see who played it.
			// Note: If I weren't rotating the board, I could call a column empty if its
			// bottom row was empty. But since I'm trying to avoid repetitive code, I have
			// to go the extra step of evaluating 
			if (focusValue != -1) {
				focusPlayer = focusValue;
				counter += 1;
			}

			// We start second from the bottom and move up
			for (int checkRow = 1; checkRow < board.height(); checkRow++) {
				// Note: We'll never have a game winner at this point; we're only checking the
				// second row in the initial loop.

				focusValue = board.readBoardSquare(focusSquare);

				if (focusValue == -1) {
					focusPlayer = focusValue;
					counter = 0; // Nobody's winning with a blank space
				} else if (focusValue != focusPlayer) { // We have an opponent token before a winner was found
					focusPlayer = focusValue; // Set the new winner
					counter = 1; // Reset the counter to 1 because we have one in a row
				} else { // We have another token for the current winner
					// Increment the counter
					counter += 1;

					// If we have four in a row, declare the winner
					if (counter == 4) {
						winner = focusPlayer;
						return winner;
					}
				}

				// Otherwise move the focus and loop
				focusSquare.move(board, "up");
				continue;
			}

			// If we haven't declared a winner, we should look in the next column
			counter = 0;
			continue;
		}

		// If we got all the way through and we're running again, let's rotate the board
		// and try again
		if (runTwice) {
			Board boardCW = board;
			boardCW.rotateBoardCW();
			focusPlayer = checkForWinStraight(game, boardCW, false);
		}

		// Now, whether we've run once or twice, let's return the winner.
		return focusPlayer;
	}

	// This function checks for a descending win and then, if runTwice is true,
	// rotates the board clockwise, making the truly ascending lines appear
	// descending, and runs again.
	public static int checkForWinDiagonal(Game game, Board board, boolean runTwice) {
		int counter = 0;
		int winner = -1;

		// For each column, starting at the left
		for (int checkColumn = 0; checkColumn < (board.width() - 4); checkColumn--) {
			// Initialize the focusSquare for each column
			BoardSquare focusSquare = new BoardSquare(checkColumn, 0);
			winner = board.readBoardSquare(focusSquare);

			// If the column is empty, we can continue
			if (winner != -1) {
				counter += 1;
			}

			// We start second from the bottom and move up
			for (int checkRow = 1; checkRow < board.height(); checkRow++) {
				// Note: We'll never have a game winner at this point; we're only checking the
				// second row in the initial loop.

				int focusValue = board.readBoardSquare(focusSquare);

				if (focusValue == -1) { // We have an empty square before a winner was found
					// We now know there's not a winner in this column
					break;
				} else if (focusValue != winner) { // We have an opponent token before a winner was found
					winner = focusValue; // Set the new winner
					counter = 1; // Reset the counter to 1 because we have one in a row
				} else { // We have another token for the current winner
					// Increment the counter
					counter += 1;

					// If we have four in a row, declare the winner
					if (counter == 4) {
						return winner;
					}
				}

				// Otherwise move the focus and loop
				focusSquare.move(board, "up");
				continue;
			}

			// If we haven't declared a winner, we should look in the next column
			counter = 0;
			continue;
		}

		// If we got all the way through and we're running again, let's rotate the board
		// and try again
		if (runTwice) {
			Board boardCW = board;
			boardCW.rotateBoardCW();
			winner = checkForWinStraight(game, boardCW, false);
		}

		// Now, whether we've run once or twice, let's return the winner.
		return winner;
	}

	// Checks for a draw and returns 0 for draw or -1 for no draw
	public static int checkForDraw(Game game, Board board) {
		// Since this happens after win checking, we can see if the board is full first
		if (game.readLog().readMoves().size() == (board.width() * board.height())) {
			return 0;
		}

		// Else iterate through the board to see if it's possible for either player to
		// win. Note: I run the risk of repeating iterative code designing like this.
		// It'd be a good idea to build an iterator function that takes a direction and
		// call it for each check.

		return -1;
	}
}