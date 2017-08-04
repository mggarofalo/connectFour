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
		return new String("display row " + (row + 1) + ", display column " + (col + 1));
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