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

	public void move(Board board, String direction) {
		if (direction.equalsIgnoreCase("left") && col != 1) {
			col -= 1;
		} else if (direction.equalsIgnoreCase("right") && col < board.width()) {
			col += 1;
		} else if (direction.equalsIgnoreCase("down") && (row != 1)) {
			row -= 1;
		} else if (direction.equalsIgnoreCase("up") && row < board.height()) {
			row += 1;
		}
	}
}