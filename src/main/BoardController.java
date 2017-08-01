package main;

public class BoardController {
	/*
	 * Method list: + pickFirstPlayer generates a random player order + readBoard
	 * returns the current board state for AI reading + readLog returns the game log
	 * for AI reading + tryMove takes a player ID and column ID and returns a
	 * success value + makeMove updates the board and logs the move +
	 * checkForWinOrDraw runs checkForWin and checkForDraw and return continue (0),
	 * draw (-1), or winning player ID
	 */

	private final int rows;
	private final int columns;
	private Board board;
	private BoardMoveLog log;

	// Initialize a game
	public BoardController(int rowTotal, int columnTotal) {
		this.rows = rowTotal;
		this.columns = columnTotal;
		board = new Board(this.rows, this.columns);
		log = new BoardMoveLog();
	}

	// Initialize a game
	public BoardController(int[][] boardArray) {
		rows = boardArray.length;
		columns = boardArray[0].length;
		board = new Board(this.rows, this.columns);
		log = new BoardMoveLog();
	}

	// Initialize a game
	public BoardController(BoardController boardController) {
		rows = boardController.rows;
		columns = boardController.columns;
		board = boardController.board;
		log = boardController.log;
	}

	// Return the height of the board
	public int height() {
		return rows;
	}

	// Returns the width of the board
	public int width() {
		return columns;
	}

	// The board is private so that only the BoardController class can write to it.
	// However, so that the AI can read it, we have a read method.
	public int[][] readBoard() {
		return board.boardArray;
	}

	// Returns the contents of a given BoardSquare
	public int readBoardSquare(BoardSquare square) {
		return board.boardArray[square.row()][square.col()];
	}

	// Same as above with the log. This will be necessary when implementing advanced
	// AI strategy, since it will be necessary to see the opponent's previous play.
	public BoardMoveLog readLog() {
		return log;
	}

	// Prints the board to the console
	public void printBoard() {
		// Print board contents to look like this:
		// Note: Eclipse auto-format doesn't keep leading spaces, hence ///
		/// 1 2 3 4 5 6 7
		// | | | | | | | |
		// | | | | | | | |
		// |1| |2|1|1|2| |

		// Print column labels
		for (int col = 0; col < columns; col++) {
			System.out.print(" ");

			// Print the column header. If we're done, print a new line.
			if (col == (columns - 1)) {
				System.out.println(col + 1);
			} else {
				System.out.print(col + 1);
			}
		}

		// Loop through rows
		for (int row = 0; row < rows; row++) {
			// Initial border
			System.out.print("|");

			// Loop through columns in row
			for (int col = 0; col < columns; col++) {
				// Print blank space if the space is empty; otherwise print the player number.
				if (board.boardArray[row][col] == -1) {
					System.out.print(" ");
				} else {
					System.out.print(board.boardArray[row][col]);
				}

				// Dividing border
				System.out.print("|");
			}

			// End the row and loop again
			System.out.println();
		}

		// Print a spacer line
		System.out.println();
	}

	// Takes a player ID and column to log.
	public int tryMove(int player, int col) {
		// If the column isn't valid, return -2
		if (col < 0 || col >= columns) {
			return -2;
		}

		// Get the lowest row now so we only have to get it once per move attempt
		int lowestRow = getLowestRow(col);

		// If the column is full, return -3
		if (lowestRow == -1) {
			return -3;
		}

		// First, convert the column to a move.
		BoardSquare move = new BoardSquare(lowestRow, col);

		// Then call the logMove overload
		makeMove(player, move);

		// If we've gotten this far, let's perform our state check and return
		return checkForWinOrDraw();
	}

	// Gets the lowest open row for a given column
	public int getLowestRow(int col) {
		for (int row = (rows - 1); row >= 0; row--) {
			if (board.boardArray[row][col] == -1) {
				return row;
			}
		}

		// If the column is full, return -1
		return -1;
	}

	// Takes a player ID and move to log and add to the board
	private void makeMove(int player, BoardSquare move) {
		log.addMove(player, move);
		board.boardArray[move.row()][move.col()] = player;
	}

	// Checks for a win (player ID) or a draw (-1)
	private int checkForWinOrDraw() {
		// Returns:
		// 0 = Game continues
		// 1 = Player 1 wins (checkForWin)
		// 2 = Player 2 wins (checkForWin)
		// -1 = Draw detected (checkForDraw)

		// Check for a winner
		int winner = BoardControllerCompletionChecker.checkForWin(this, null);

		// If we have a win, return the player number
		if (winner != 0) {
			return winner;
		}

		// Otherwise check for a draw
		int draw = BoardControllerCompletionChecker.checkForDraw(this);

		// Return an appropriate value
		if (draw == 0) {
			return -1;
		} else {
			return 0;
		}
	}
}