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
	private final int winLength;
	private Board board;
	private BoardMoveLog log;

	// Initialize a game
	public BoardController(int rowTotal, int columnTotal, int winLength) {
		this.rows = rowTotal;
		this.columns = columnTotal;
		this.winLength = winLength;
		board = new Board(this.rows, this.columns);
		log = new BoardMoveLog();
	}

	// Initialize a game
	public BoardController(int[][] boardArray, int winLength) {
		rows = boardArray.length;
		columns = boardArray[0].length;
		this.winLength = winLength;
		board = new Board(this.rows, this.columns);
		log = new BoardMoveLog();
	}

	// Initialize a game
	public BoardController(BoardController boardController) {
		rows = boardController.rows;
		columns = boardController.columns;
		winLength = boardController.winLength;
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

	// Returns the number of tokens needed in a row to win
	public int winLength() {
		return winLength;
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
	public void printBoard(PlayerController[] playerController) {
		// Print leading line
		Utilities.print("");

		// Print column labels
		for (int col = 0; col < columns; col++) {
			Utilities.print(" ");

			// Print the column header. If we're done, print a new line.
			Utilities.print(
					Utilities.padString(String.valueOf(col + 1), " ",
							String.valueOf(columns).length() + 1 - String.valueOf(col + 1).length()),
					(col == (columns - 1)));
		}

		// Loop through rows
		for (int row = 0; row < rows; row++) {
			// Initial border
			Utilities.print("|");

			// Loop through columns in row
			for (int col = 0; col < columns; col++) {
				// Print blank space if the space is empty; otherwise print the player number.
				if (board.boardArray[row][col] == -1) {
					Utilities.print(Utilities.padString(" ", null, String.valueOf(columns).length()));
				} else {
					Utilities
							.print(Utilities
									.padString(playerController[board.boardArray[row][col]].getPlayer().getToken(), " ",
											String.valueOf(columns).length() + 1 - String.valueOf(
													playerController[board.boardArray[row][col]].getPlayer().getToken())
													.length()));
				}

				// Dividing border
				Utilities.print("|");
			}

			// End the row and loop again
			Utilities.println();
		}
	}

	// Takes a player ID and column to log.
	public int tryMove(Player player, int col) {
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
	private void makeMove(Player player, BoardSquare move) {
		log.addMove(player, move);
		board.boardArray[move.row()][move.col()] = player.getIndex();
	}

	// Checks for a win (player ID) or a draw (-1)
	private int checkForWinOrDraw() {
		// Returns:
		// 0 = Game continues
		// 1 = Player 1 wins (checkForWin)
		// 2 = Player 2 wins (checkForWin)
		// -1 = Draw detected (checkForDraw)

		// Check for a winner
		int winner = BoardIterator.checkForWin(this);

		// If we have a win, return the player number
		if (winner != 0) {
			return winner;
		}

		// Otherwise check for a draw
		int draw = BoardIterator.checkForDraw(this);

		// Return an appropriate value
		if (draw == 0) {
			return -1;
		} else {
			return 0;
		}
	}
}