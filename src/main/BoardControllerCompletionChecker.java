package main;

import main.BoardController.CheckType;

public class BoardControllerCompletionChecker {

	private static BoardController boardController;
	private static BoardController.CheckType currentCheckType;

	private static int columnCount;
	private static int rowCount;

	private static BoardSquare outerLoopSquare;
	private static BoardSquare innerLoopSquare;
	private static int innerLoopSquareValue;

	private static int outerLoopCount;
	private static int innerLoopCount;
	private static String outerLoopDirection;
	private static String innerLoopDirection;

	private static int playerWhoMightWin = -1;
	private static int counter = 0;

	public static int checkForWin(BoardController bc, CheckType ct) {
		// Returns:
		// -1 = Error
		// 0 = No win detected
		// 1 = Player 1 won
		// 2 = Player 2 won

		boardController = bc;
		currentCheckType = ct;
		columnCount = boardController.width();
		rowCount = boardController.height();

		setInitialCheckTypeValues(currentCheckType);

		// Testing
		System.out.println();
		System.out.println("currentCheckType: " + currentCheckType.toString());
		bc.printBoard();

		return outerLoop();
	}

	// Checks for a draw and returns 0 for draw or -1 for no draw
	public static int checkForDraw(BoardController boardController) {
		// Since this happens after win checking, we can see if the board is full first
		if (boardController.readLog().readMoves().size() == (boardController.width() * boardController.height())) {
			return 0;
		} else {
			// Else iterate through the board to see if it's possible for either player to
			// win. Note: I run the risk of repeating iterative code designing like this.
			// It'd be a good idea to build an iterator function that takes a direction and
			// call it for each check.

		}

		return -1;
	}

	private static void setInitialCheckTypeValues(CheckType ct) {
		// Set the starting space and direction
		outerLoopSquare = new BoardSquare((rowCount - 1), 0);
		if (ct == CheckType.V) {
			// Vertical means we check all columns, all but the top three rows, and move up.
			// Since columns are our home base, they're our outer loop.
			outerLoopCount = (columnCount - 1);
			innerLoopCount = (rowCount - 1);
			outerLoopDirection = "E";
			innerLoopDirection = "N";
		} else if (ct == CheckType.H) {
			// Horizontal means we check all but the right three columns, all rows, and move
			// right. Since rows are our home base, they're our outer loop.
			outerLoopCount = (rowCount - 1);
			innerLoopCount = (columnCount - 1);
			outerLoopDirection = "N";
			innerLoopDirection = "E";
		} else if (ct == CheckType.D) {
			// Descending means we start at the fourth column, check all but the left three
			// columns, all but the top three rows, and move up-left. Since columns are our
			// home base, they're our outer loop.
			outerLoopSquare = new BoardSquare((rowCount - 1), 3);
			outerLoopCount = (columnCount - outerLoopSquare.col() - 1);
			innerLoopCount = (rowCount - 1);
			outerLoopDirection = "E";
			innerLoopDirection = "NW";
		} else if (ct == CheckType.A) {
			// Ascending means we check all but the right three columns, all but the top
			// three rows, and move up-right. Since columns are our home base, they're our
			// outer loop.
			outerLoopCount = (columnCount - 4);
			innerLoopCount = (rowCount - 4);
			outerLoopDirection = "E";
			innerLoopDirection = "NE";
		}
	}

	private static int outerLoop() {
		// This function starts at the initial square and runs the four-in-a-row finder
		// function along each possible line.

		int retVal = 0;

		for (int outerLoop = 0; outerLoop <= outerLoopCount; outerLoop++) {

			System.out.println("Outer Loop " + (outerLoop + 1) + ", starting at " + outerLoopSquare.row() + ","
					+ outerLoopSquare.col() + ".");

			// Reset the starting square for the inner loop
			innerLoopSquare = outerLoopSquare;

			retVal = innerLoop();

			// If we haven't declared a winner, we should reset the variables and loop
			setInitialCheckTypeValues(currentCheckType);
			for (int i = 0; i <= outerLoop; i++) {
				outerLoopSquare.move(boardController, outerLoopDirection);
			}

			counter = 0;
			playerWhoMightWin = -1;
		}

		return retVal;
	}

	private static int innerLoop() {
		// This function starts at the outer loop's starting square and iterates along a
		// line to see if there are four in a row in that line.

		for (int innerLoop = 0; innerLoop <= innerLoopCount; innerLoop++) {

			// Initialize the focusValue
			innerLoopSquareValue = boardController.readBoardSquare(innerLoopSquare);

			// If we're looking vertically and find an empty space before a winner, we can
			// move to the next column.
			System.out.println("Inner Loop " + (innerLoop + 1) + ". currentCheckType = " + currentCheckType
					+ ", started on " + outerLoopSquare.row() + "," + outerLoopSquare.col() + " and looking at "
					+ innerLoopSquare.row() + "," + innerLoopSquare.col() + ", the value of which is "
					+ innerLoopSquareValue + ".");
			if (currentCheckType == CheckType.V && innerLoopSquareValue == -1) {
				System.out.println("Empty column while looking vertically. Break.");
				break;
			} else if (innerLoopCount < (innerLoop + (4 - counter))) {
				System.out.println("We have " + counter + " in a row and " + (innerLoopCount - innerLoop)
						+ " inner loops left. Break.");
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