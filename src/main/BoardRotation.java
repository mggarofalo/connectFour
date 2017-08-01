package main;

public class BoardRotation {
	static int columns;
	static int rows;
	static int[][] originalBoardArray;
	static int[][] quarterRotatedBoardArray;

	// Rotates the board clockwise
	public static BoardController rotateCW(BoardController bc) {
		setUpVariables(bc);

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				quarterRotatedBoardArray[col][rows - 1 - row] = originalBoardArray[row][col];
			}
		}

		return new BoardController(quarterRotatedBoardArray);
	}

	// Rotates the board counterclockwise
	public static BoardController rotateCCW(BoardController bc) {
		setUpVariables(bc);

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				quarterRotatedBoardArray[columns - 1 - col][row] = originalBoardArray[row][col];
			}
		}

		return new BoardController(quarterRotatedBoardArray);
	}

	// Sets up variables
	private static void setUpVariables(BoardController bc) {
		originalBoardArray = bc.readBoard();
		columns = bc.width();
		rows = bc.height();
		quarterRotatedBoardArray = new int[columns][rows];
	}
}