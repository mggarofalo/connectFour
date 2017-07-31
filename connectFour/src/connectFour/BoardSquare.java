package connectFour;

public class BoardSquare {

	private final int col;
	private final int row;

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

}