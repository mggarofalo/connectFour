package main;

public class Board {
	// Method list:
	// readBoard returns the current board as int[][]
	// readBoardSquare returns the value of a BoardSquare coordinate
	// getLowestRow returns the lowest open row or -1 if the column is full
	// makeMove sets a coordinate to the given player ID
	// printBoard prints the current board state to the console

	// About:
	// Standard Connect Four is played on a 7-wide by 6-high board. Throughout this
	// class, r refers to row (height) and c refers to column (width).

	// The board is a two-dimensional int matrix (-1 is empty). Board spaces are
	// checked with board[r][c] (board[h][w]).
	public Player[][] boardArray = new Player[][] {};

	public final int rows;
	public final int columns;

	public Board(Player[][] boardArray) {
		// Set the width and height variables
		rows = boardArray.length;
		columns = boardArray[0].length;

		this.boardArray = new Player[rows][columns];
	}

	public int height() {
		return this.rows;
	}

	public int width() {
		return this.columns;
	}
}