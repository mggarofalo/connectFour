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

	public String coordinates() {
		return new String(row + "," + col);
	}

	public String coordinatesReadable() {
		return new String("row " + (row + 1) + ", column " + (col + 1));
	}

	public void move(String direction) {
		if (direction.length() == 1) {
			if (direction.equals("N")) {
				row -= 1;
			} else if (direction.equals("E")) {
				col += 1;
			} else if (direction.equals("S")) {
				row += 1;
			} else if (direction.equals("W")) {
				col -= 1;
			}
		} else {
			char[] dir = direction.toCharArray();

			for (int i = 0; i < dir.length; i++) {
				move(String.valueOf(dir[i]));
			}
		}
	}
}