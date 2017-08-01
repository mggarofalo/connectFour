package connectFour;

public class BoardControllerCompletionChecker {

	// Checks for wins in four directions. We pass two booleans to combine for the
	// four states. If runAgain, we're doing vertical or descending. Descending,
	// despite the name, means that the line is descending from left to right. We
	// actually check it left to right because that way we're always climbing.
	public static int checkForWin(BoardController game, Board board, boolean straight, boolean verticalOrDescending) {
		// Returns:
		// 0 = No win detected
		// 1 = Player 1 won
		// 2 = Player 2 won

		int width = board.width();
		int height = board.height();

		int counter = 0;
		BoardSquare focusSquare;
		int focusValue;
		int playerWhoMightWin = -1;

		// For each column, starting at the left (because when we rotate we rotate
		// clockwise, transposing the likely filled spaces from the bottom to the left)
		for (int ixCheckColumn = 0; ixCheckColumn < width; ixCheckColumn++) {
			// Initialize the focusSquare for each column
			focusSquare = new BoardSquare(ixCheckColumn, (height - 1));

			// If we're looking vertically and find an empty space, we can move to the next
			// column.
			if (straight && verticalOrDescending && board.readBoardSquare(focusSquare) == -1) {
				continue;
			}

			// We start at the bottom (height - 1 is the rowIndex of the bottom row) and
			// move up or up-left. checkRow-- means we're climbing, counter-intuitively.
			for (int ixCheckRow = (height - 1); ixCheckRow >= 0; ixCheckRow--) {
				// Read the considered square
				focusValue = board.readBoardSquare(focusSquare);

				if (focusValue == -1) {
					// If it's blank, nobody's winning

					playerWhoMightWin = -1;
					counter = 0;
				} else if (focusValue != playerWhoMightWin) {
					// We have an opponent token before four in a row (we check that at the bottom)

					// Set the new winner
					playerWhoMightWin = focusValue;

					// Reset the counter to 1 because we have one in a row
					counter = 1;
				} else {
					// We have another token for the current winner

					// Increment the counter
					counter += 1;

					// If we have four in a row, declare the winner as Player 1 or Player 2
					if (counter == 4) {
						return (playerWhoMightWin + 1); // HOORAY!
					}
				}

				// Otherwise move the focus and loop
				if (ixCheckRow < (4 - counter)) {
					// For example, if our row index is 1 and we need 3 more to win (counter == 1),
					// we know we only have one row above to check (0) and the run can only be 2
					// long.

					break; // the row loop and check the next column
				} else {
					focusSquare.move(board, "up"); // semper excelsius
				}

				if (!straight) {
					if (ixCheckColumn < (4 - counter)) {
						// For example, if our column index is 2 and we need 3 more to win (counter ==
						// 1), we know we only have two columns to the left still to check and the run
						// can only be 3 long.

						break; // the row loop and check the next column
					} else {
						focusSquare.move(board, "left");
					}
				}
			}

			// If we haven't declared a winner, we should reset the counter and look in the
			// next column
			counter = 0;
		}

		// If we got all the way through and we're running again, let's rotate the board
		// and try again
		if (straight) {
			if (verticalOrDescending) {
				// This triggers the first time through. We just set the secondary boolean to
				// false and run horizontal.
				verticalOrDescending = false;
				board = BoardRotation.rotateCW(board);
			} else {
				// This triggers the second time through. We set both booleans and run
				// descending.
				straight = false;
				verticalOrDescending = true;
				board = BoardRotation.rotateCCW(board);
			}
		} else {
			if (verticalOrDescending) {
				// This triggers the third time through. We just set the secondary boolean to
				// false and run ascending.
				verticalOrDescending = false;
				board = BoardRotation.rotateCW(board);
			} else {
				// This triggers the fourth time through. Since the original board is still in
				// the Game object, we can just return that there wasn't a winner if this
				// triggers.
				return 0;
			}
		}

		// Run again with the checks
		playerWhoMightWin = checkForWin(game, board, straight, verticalOrDescending);

		// Now, after running four times, let's return the winner back up through the
		// nested calls.
		return playerWhoMightWin;
	}

	// Checks for a draw and returns 0 for draw or -1 for no draw
	public static int checkForDraw(BoardController game, Board board) {
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