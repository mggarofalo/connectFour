package main;

public class BoardSquare {

	private int row;
	private int col;

	public BoardSquare(int aRow, int aColumn) {
		row = aRow;
		col = aColumn;
	}

	public int col() {
		return col;
	}

	public int row() {
		return row;
	}

	public void move(String direction) {
		if (direction.equalsIgnoreCase("N")) {
			row -= 1;
		}

		if (direction.equalsIgnoreCase("E")) {
			col += 1;
		}

		if (direction.equalsIgnoreCase("S")) {
			row += 1;
		}

		if (direction.equalsIgnoreCase("W")) {
			col -= 1;
		}

		if (direction.equalsIgnoreCase("NE")) {
			move("N");
			move("E");
		}

		if (direction.equalsIgnoreCase("SE")) {
			move("S");
			move("E");
		}

		if (direction.equalsIgnoreCase("SW")) {
			move("S");
			move("W");
		}

		if (direction.equalsIgnoreCase("NW")) {
			move("N");
			move("W");
		}
	}
}