package connectFour;

public class Board {
	// Method list:
	// readBoard returns the current board as int[][]
	// getLowestRow returns the lowest open row or -1 if the column is full
	// makeMove sets a coordinate to the given player ID
	// printBoard prints the current board state to the console

	// About:
	// Standard Connect Four is played on a 7-wide by 6-high board. Throughout this
	// class, c refers to column (width) and r refers to row (height). NB: games
	// over 10x10 will not be displayed properly.

	private final int width;
	private final int height;

	// The board is a two-dimensional player ID int matrix (-1 is empty). Board
	// spaces are checked with board[c][r]. It's public so an AI can read it.
	private int[][] boardArray = new int[][] {};

	public Board(int w, int h) {
		// Set the width and height variables
		width = w;
		height = h;

		// Initialize a board to the width and height
		boardArray = new int[width][height];

		// Set initial values to -1 to indicate empty
		for (int c = 0; c < width; c++) {
			for (int r = 0; r < height; r++) {
				boardArray[c][r] = -1;
			}
		}
	}

	// The board is private so that only the Board class can write to it. However,
	// so that the game can read it, we have a read method.
	public int[][] readBoard() {
		return boardArray;
	}

	// Returns the contents of a given coordinate set
	public int readBoardSquare(int c, int r) {
		BoardSquare square = new BoardSquare(c, r)
		return readBoardSquare(square);
	}

	// Returns the contents of a given BoardSquare
	public int readBoardSquare(BoardSquare square) {
		return boardArray[square.col()][square.row()]
	}

	// Gets the lowest open row for a given column
	public int getLowestRow(int c) {
		for (int r = 0; r < height; r++) {
			if (boardArray[c][r] == -1) {
				return r;
			}
		}

		// If the column is full, return -1
		return -1;
	}

	// Takes a player ID and move to log and add to display
	public void makeMove(int player, BoardSquare square) {
		boardArray[square.col()][square.row()] = player;
	}

	// Prints the board to the console
	public void printBoard() {
		// Print board contents to look like this:
		// Note: stupid Eclipse auto-format doesn't keep leading spaces, hence ///
		/// 1 2 3 4 5 6 7
		// | | | | | | | |
		// | | | | | | | |
		// |1| |2|1|1|2| |

		// Print column labels
		for (int c = 0; c < width; c++) {
			System.out.print(" ");
			System.out.print(c);
		}

		for (int r = height - 1; r >= 0; r--) {
			// Initial border
			System.out.print("|");

			// Loop through columns in row
			for (int c = 0; c < width; c++) {
				// Print blank space if the space is empty; otherwise print the player number.
				if (boardArray[c][r] == -1) {
					System.out.print(" ");
				} else {
					System.out.print(boardArray[c][r]);
				}

				// Print a closing border and, if we're done with this row, a new line.
				if (c == width) {
					System.out.println("|");
				} else {
					System.out.print("|");
				}
			}
		}
	}
}