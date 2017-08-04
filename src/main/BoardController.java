package main;

public class BoardController {

	private final int winLength;
	private Board board;
	private BoardMoveLog log;

	// Initializes a game with dimensions
	public BoardController(int rows, int columns, int winLength) {
		this.winLength = winLength;

		// Convert row/column counts to an in[][]
		int[][] boardArray = new int[rows][columns];

		// Set initial values to empty (-1)
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				boardArray[row][col] = -1;
			}
		}

		board = new Board(boardArray);
		log = new BoardMoveLog();
	}

	// Returns the height of the board
	public int height() {
		return board.height();
	}

	// Returns the width of the board
	public int width() {
		return board.width();
	}

	// Returns the number of tokens needed in a row to win
	public int winLength() {
		return winLength;
	}

	// Returns the contents of a given BoardSquare
	public int readBoardSquare(BoardSquare square) {
		return board.boardArray[square.row()][square.col()];
	}

	// Same as above with the log. This will be necessary when implementing AI
	// strategy, since it will be necessary to see the opponent's previous play.
	public BoardMoveLog readLog() {
		return log;
	}

	// Print the board to the console
	public void printBoard(PlayerController[] playerController) {
		// Print leading line
		Utilities.print("");

		// Print column labels
		for (int col = 0; col < board.width(); col++) {
			Utilities.print(" ");

			// Print the column header. If we're done, print a new line.
			Utilities.print(
					Utilities.padString(String.valueOf(col + 1), " ",
							String.valueOf(board.width()).length() + 1 - String.valueOf(col + 1).length()),
					(col == (board.width() - 1)));
		}

		// Loop through rows
		for (int row = 0; row < board.height(); row++) {
			// Initial border
			Utilities.print("|");

			// Loop through columns in row
			for (int col = 0; col < board.width(); col++) {
				// Print blank space if the space is empty; otherwise print the player number.
				if (board.boardArray[row][col] == -1) {
					Utilities.print(Utilities.padString(" ", null, String.valueOf(board.width()).length()));
				} else {
					Utilities
							.print(Utilities
									.padString(playerController[board.boardArray[row][col]].getPlayer().getToken(), " ",
											String.valueOf(board.width()).length() + 1 - String.valueOf(
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
		// If the column isn't valid, throw an AI exception or return -2
		if (col < 0 || col >= board.width()) {
			if (!player.isHuman()) {
				throw new IllegalArgumentException(
						"An AI player just tried to move in column " + col + ", which is not valid.");
			} else {
				Utilities.print("Sorry, but that isn't a valid column selection. Try again: ");
				return -2;
			}
		}

		// Get the lowest row now so we only have to get it once per move attempt
		int lowestRow = getLowestRow(col);

		// If the column is full, throw an AI exception or return -3
		if (lowestRow == -1) {
			if (!player.isHuman()) {
				throw new IllegalArgumentException(
						"An AI player just tried to move in column " + col + ", which is full.");
			} else {
				Utilities.print("Sorry, but that column is full. Try again: ");
				return -3;
			}
		}

		// First, convert the column to a move.
		BoardSquare move = new BoardSquare(lowestRow, col);

		// Then call the logMove overload
		makeMove(player, move);

		// If we've gotten this far, let's perform our state check and return
		return checkForWinOrDraw(player, move);
	}

	// Gets the lowest open row for a given column
	public int getLowestRow(int col) {
		for (int row = (board.height() - 1); row >= 0; row--) {
			if (board.boardArray[row][col] == -1) {
				return row;
			}
		}

		// If the column is full, return -1
		return -1;
	}

	// Takes a player ID and move to log and add to the board
	public void makeMove(Player player, BoardSquare move) {
		log.addMove(player, move);
		board.boardArray[move.row()][move.col()] = player.getIndex();
	}

	// Checks for a win (player ID) or a draw (-1)
	private int checkForWinOrDraw(Player player, BoardSquare move) {
		// Returns:
		// 0 = Game continues
		// 1 = Player 1 wins (checkForWin)
		// 2 = Player 2 wins (checkForWin)
		// -1 = Draw detected (checkForDraw)

		// If we have a win, return the player number
		boolean isWin = BoardChecker.isWinningMove(this, move);
		if (isWin) {
			return (player.getIndex() + 1);
		}

		// Otherwise check for a draw
		int draw = BoardChecker.checkForDraw(this);

		// Return an appropriate value
		if (draw == 0) {
			return -1;
		} else {
			return 0;
		}
	}
}