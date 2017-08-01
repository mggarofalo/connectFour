package main;

public class BoardControllerCompletionChecker {

	private static int columnCount;
	private static int rowCount;

	private static BoardController boardController;
	private static String winCheckType;

	private static BoardSquare outerLoopSquare;
	private static BoardSquare innerLoopSquare;
	private static int innerLoopSquareValue;

	private static int outerLoopCount;
	private static int innerLoopCount;
	private static String outerLoopDirection;
	private static String innerLoopDirection;

	private static int playerWhoMightWin = -1;
	private static int counter = 0;

	// Checks for wins in four directions. We pass two booleans to combine for the
	// four states. If runAgain, we're doing vertical or descending. Descending,
	// despite the name, means that the line is descending from left to right. We
	// actually check it left to right because that way we're always climbing.
	public static int checkForWin(BoardController bc, String wct) {
		int retVal = 0;
		// Returns:
		// -1 = Error
		// 0 = No win detected
		// 1 = Player 1 won
		// 2 = Player 2 won

		if (wct == null) {
			winCheckType = "V";
		} else {
			winCheckType = wct;
		}
		boardController = bc;
		columnCount = boardController.width();
		rowCount = boardController.height();

		setInitialValues(winCheckType);

		// Testing
		System.out.println();
		System.out.println("winCheckType: " + winCheckType);
		bc.printBoard();

		retVal = beginOuterLoop();

		// If we got all the way through, let's run it for the next check type. Note:
		// it'd be cool to make these enums if you could iterate enums. So that I could
		// just call something like winCheckType += 1. I might just put a translator in
		// a comment above and do this anyway.
		if (winCheckType == "V") {
			// This triggers the first time through.
			winCheckType = "H";
		} else if (winCheckType == "H") {
			// This triggers the second time through.
			winCheckType = "D";
		} else if (winCheckType == "D") {
			// This triggers the third time through.
			winCheckType = "A";
		} else {
			// This triggers the fourth time through.
			return 0;
		}

		// Run again with the checks
		retVal = checkForWin(boardController, winCheckType);

		// Now, after running four times, let's return the winner back up through the
		// nested calls.
		return retVal;
	}

	// Checks for a draw and returns 0 for draw or -1 for no draw
	public static int checkForDraw(BoardController boardController) {
		// Since this happens after win checking, we can see if the board is full first
		if (boardController.readLog().readMoves().size() == (boardController.width() * boardController.height())) {
			return 0;
		}

		// Else iterate through the board to see if it's possible for either player to
		// win. Note: I run the risk of repeating iterative code designing like this.
		// It'd be a good idea to build an iterator function that takes a direction and
		// call it for each check.

		return -1;
	}

	private static void setInitialValues(String winCheckType) {
		// Set the starting space and direction
		outerLoopSquare = new BoardSquare((rowCount - 1), 0);
		if (winCheckType == "V") {
			// Vertical means we check all columns, all but the top three rows, and move up.
			// Since columns are our home base, they're our outer loop.
			outerLoopCount = (columnCount - 1);
			innerLoopCount = (rowCount - 1);
			outerLoopDirection = "E";
			innerLoopDirection = "N";
		} else if (winCheckType == "H") {
			// Horizontal means we check all but the right three columns, all rows, and move
			// right. Since rows are our home base, they're our outer loop.
			outerLoopCount = (rowCount - 1);
			innerLoopCount = (columnCount - 3);
			outerLoopDirection = "N";
			innerLoopDirection = "E";
		} else if (winCheckType == "D") {
			// Descending means we start at the fourth column, check all but the left three
			// columns, all but the top three rows, and move up-left. Since columns are our
			// home base, they're our outer loop.
			outerLoopSquare = new BoardSquare((rowCount - 1), 3);
			outerLoopCount = (columnCount - 3);
			innerLoopCount = (rowCount - 3);
			outerLoopDirection = "E";
			innerLoopDirection = "NW";
		} else if (winCheckType == "A") {
			// Ascending means we check all but the right three columns, all but the top
			// three rows, and move up-right. Since columns are our home base, they're our
			// outer loop.
			outerLoopCount = (columnCount - 3);
			innerLoopCount = (rowCount - 3);
			outerLoopDirection = "E";
			innerLoopDirection = "NE";
		}
	}

	private static int beginOuterLoop() {
		// This function starts at the initial square and runs the four-in-a-row finder
		// function along each possible line.

		int retVal = 0;

		for (int outerLoop = outerLoopCount; outerLoop > 0; outerLoop--) {

			// Reset the starting square for the inner loop
			innerLoopSquare = outerLoopSquare;

			retVal = beginInnerLoop();

			// If we haven't declared a winner, we should reset the variables and loop
			outerLoopSquare.move(boardController, outerLoopDirection);
			counter = 0;
			playerWhoMightWin = -1;
		}

		return retVal;
	}

	private static int beginInnerLoop() {
		// This function starts at the outer loop's starting square and iterates along a
		// line to see if there are four in a row in that line.

		for (int innerLoop = innerLoopCount; innerLoop > 0; innerLoop--) {
			// Initialize the focusValue
			innerLoopSquareValue = boardController.readBoardSquare(innerLoopSquare);

			// If we're looking vertically and find an empty space before a winner, we can
			// move to the next column.
			System.out.println("winCheckType = " + winCheckType + ", started on " + outerLoopSquare.row() + ","
					+ outerLoopSquare.col() + " and looking at " + innerLoopSquare.row() + "," + innerLoopSquare.col()
					+ ", the value of which is " + innerLoopSquareValue + ".");
			if (winCheckType == "V" && innerLoopSquareValue == -1) {
				System.out.println("We should break...");
				break;
			}

			// Handle the value
			if (innerLoopSquareValue == -1) {
				// If it's blank, nobody's winning

				playerWhoMightWin = -1;
				counter = 0;
			} else if (innerLoopSquareValue == playerWhoMightWin) {
				// We have another token for the current winner
				// Increment the counter
				counter += 1;

				// If we have four in a row, declare the winner as Player 1 or Player 2
				if (counter == 4) {
					return (playerWhoMightWin + 1); // HOORAY!
				}
			} else {
				// We have an opponent token before four in a row (we check that at the bottom)
				// Set the new winner
				playerWhoMightWin = innerLoopSquareValue;

				// Reset the counter to 1 because we have one in a row
				counter = 1;
			}

			// Move the focus and loop
			innerLoopSquare.move(boardController, innerLoopDirection);
		}

		return 0;
	}
}