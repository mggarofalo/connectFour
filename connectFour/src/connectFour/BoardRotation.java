package connectFour;

public class BoardRotation {
	static int columns;
	static int rows;
	static int[][] originalBoardArray;
	static int[][] quarterRotatedBoardArray;

	// Rotates the board clockwise
	public static Board rotateCW(Board board) {
		setUpVariables(board);

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				quarterRotatedBoardArray[col][rows - 1 - row] = originalBoardArray[row][col];
			}
		}

		return new Board(quarterRotatedBoardArray);
	}

	// Rotates the board counterclockwise
	public static Board rotateCCW(Board board) {
		setUpVariables(board);

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				quarterRotatedBoardArray[columns - 1 - col][row] = originalBoardArray[row][col];
			}
		}

		return new Board(quarterRotatedBoardArray);
	}

	// Sets up variables
	private static void setUpVariables(Board board) {
		originalBoardArray = board.readBoard();
		columns = board.width();
		rows = board.height();
		quarterRotatedBoardArray = new int[rows][columns];
	}
}