package connectFour;

public class BoardSquare {

	private int col;
	private int row;

	public BoardSquare(int aCol, int aRow) {
		col = aCol;
		row = aRow;
	}

	public int col() {
		return col;
	}

	public int row() {
		return row;
	}

	// This doesn't need c/r input, but it'll read more easily if I require it
	public void moveLeft(Board board) {
		if (col != 0) {
			col -= 1;
		}
	}

	public void moveRight(Board board) {
		if (col < board.readWidth()) {
			col += 1;
		}
	}

	// This doesn't need c/r input, but it'll read more easily if I require it
	public void moveDown(Board board) {
		if (row != 0) {
			row -= 1;
		}
	}

	public void moveUp(Board board) {
		if (row < board.readHeight()) {
			row += 1;
		}
	}
}