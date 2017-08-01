package scratchPad;

public class ArrayRotation {
	public static int height = 2;
	public static int width = 2;
	public static int[][] boardArray = new int[width + 1][height + 1];

	public static void main(String[] args) {
		boardArray[0][0] = 0;
		boardArray[0][1] = 0;
		boardArray[1][0] = 0;
		boardArray[1][1] = 1;

		int[][] tableCW = readCWRotatedBoard(boardArray);

		printBoard(boardArray);
		printBoard(tableCW);
	}

	public static int[][] readCWRotatedBoard(int[][] boardArray) {
		int[][] tableCW = new int[width][height];
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				tableCW[c][height - 1 - r] = boardArray[r][c];
			}
		}
		return tableCW;
	}

	// Prints the board to the console
	public static void printBoard(int[][] board) {
		// Print board contents to look like this:
		// Note: Eclipse auto-format doesn't keep leading spaces, hence ///
		/// 1 2 3 4 5 6 7
		// | | | | | | | |
		// | | | | | | | |
		// |1| |2|1|1|2| |

		// Print column labels
		for (int c = 0; c < width; c++) {
			System.out.print(" ");
			if (c == (width - 1)) {
				System.out.println(c);
			} else {
				System.out.print(c);
			}
		}

		for (int r = height - 1; r >= 0; r--) {
			// Initial border
			System.out.print("|");

			// Loop through columns in row
			for (int c = 0; c < width; c++) {
				// Print blank space if the space is empty; otherwise print the player number.
				if (board[c][r] == -1) {
					System.out.print(" ");
				} else {
					System.out.print(board[c][r]);
				}

				// Print a closing border and, if we're done with this row, a new line.
				if (c == (width - 1)) {
					System.out.println("|");
				} else {
					System.out.print("|");
				}
			}
		}

		// Print a final line break
		System.out.println();
	}
}