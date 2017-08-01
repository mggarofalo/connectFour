package connectFour;

public class Board {
	// Method list:
	// readBoard returns the current board as int[][]
	// readBoardSquare returns the value of a BoardSquare coordinate
	// getLowestRow returns the lowest open row or -1 if the column is full
	// makeMove sets a coordinate to the given player ID
	// printBoard prints the current board state to the console

	// About:
	// Standard Connect Four is played on a 7-wide by 6-high board. Throughout this
	// class, r refers to row (height) and c refers to column (width). NB: games
	// over 10x10 will not be displayed properly, but they should play normally.

	private final int rows;
	private final int columns;

	// The board is a two-dimensional int matrix (-1 is empty). Board spaces are
	// checked with board[r][c] (board[h][w]).
	private int[][] boardArray = new int[][] {};

	public Board(int rowTotal, int columnTotal) {
		// Set the width and height variables
		this.rows = rowTotal;
		this.columns = columnTotal;

		// Initialize a board to the proper dimensions
		this.boardArray = new int[rows][columns];

		// Set initial values to -1 to indicate empty
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				boardArray[row][col] = -1;
			}
		}
	}

	public Board(int[][] boardArray) {
		rows = (boardArray.length);
		columns = (boardArray[0].length);
		this.boardArray = boardArray;
	}

	// The board is private so that only the Board class can write to it. However,
	// so that the Game class can read it, we have a read method.
	public int[][] readBoard() {
		return boardArray;
	}

	// Return the height of the board
	public int height() {
		return rows;
	}

	// Returns the width of the board
	public int width() {
		return columns;
	}

	// Returns the contents of a given BoardSquare
	public int readBoardSquare(BoardSquare square) {
		return boardArray[square.row()][square.col()];
	}

	// Gets the lowest open row for a given column
	public int getLowestRow(int col) {
		for (int row = (rows - 1); row >= 0; row--) {
			if (boardArray[row][col] == -1) {
				return row;
			}
		}

		// If the column is full, return -1
		return -1;
	}

	// Takes a player ID and move to log and add to display
	public void makeMove(int player, BoardSquare square) {
		boardArray[square.row()][square.col()] = player;
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
				if (boardArray[row][col] == -1) {
					System.out.print(" ");
				} else {
					System.out.print(boardArray[row][col]);
				}

				// Dividing border
				System.out.print("|");
			}

			// End the row and loop again
			System.out.println();
		}
	}
}