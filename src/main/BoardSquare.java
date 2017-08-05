package main;

public class BoardSquare {

	private int row;
	private int col;

	public BoardSquare(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public BoardSquare(BoardSquare boardSquare) {
		this.row = boardSquare.row;
		this.col = boardSquare.col;
	}

	public int col() {
		return col;
	}

	public int row() {
		return row;
	}

	public String coordinates() {
		return new String("index " + row + "," + col);
	}

	public String coordinatesReadable() {
		return new String("display row " + (row + 1) + " (from top), display column " + (col + 1) + " (from left)");
	}

	public void move(String direction, int numberOfSpaces) {
		if (direction.length() == 1) {
			if (direction.equals("N")) {
				row -= numberOfSpaces;
			} else if (direction.equals("E")) {
				col += numberOfSpaces;
			} else if (direction.equals("S")) {
				row += numberOfSpaces;
			} else if (direction.equals("W")) {
				col -= numberOfSpaces;
			}
		} else {
			char[] dir = direction.toCharArray();

			for (int i = 0; i < dir.length; i++) {
				move(String.valueOf(dir[i]), numberOfSpaces);
			}
		}
	}

	public void move(String direction) {
		move(direction, 1);
	}
}