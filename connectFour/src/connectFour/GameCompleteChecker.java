package connectFour;

abstract class GameCompleteChecker extends Game {
	protected static int winner;
	protected static int counter;

	// Checks for a draw and returns 0 for draw or -1 for no draw
	protected static int checkForDraw() {
		// Since this happens after win checking, we can see if the board is full first
		if (log.readMoves().size() == (width * height)) {
			return 0;
		}

		// Else iterate through the board to see if it's possible for either player to
		// win. Note: I run the risk of repeating iterative code designing like this.
		// It'd be a good idea to build an iterator function that takes a direction and
		// call it for each check.

		return -1;
	}
	
	// Checks for a vertical win
	protected static int checkForWinVertical() {
		counter = 0;

		// For each column, starting at the left
		for (int checkColumn = 1; checkColumn <= width; checkColumn++) {
			// Note: We could check the fourth row in each column and break on empty, but
			// that's harder to make flexible than addressing procedurally. This program is
			// certainly light enough to run the check functions.

			// Initialize the focusSquare for each column
			BoardSquare focusSquare = new BoardSquare(checkColumn, 1);
			winner = board.readBoardSquare(focusSquare);

			// If the column is empty, we can continue
			if (winner == -1) {
				continue;
			}

			// We start second from the bottom and move up
			for (int checkRow = 2; checkRow < height; checkRow++) {
				// Note: We'll never have a winner at this point; we're only checking the second
				// row in the initial loop.

				int focusValue = board.readBoardSquare(focusSquare);

				if (focusValue == -1) { // We have an empty square before a winner was found
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

					// Otherwise move the focus
					focusSquare.move(board, "up");

					continue;
				}
			}

			counter = 0;
		}

		return -1;
	}
}