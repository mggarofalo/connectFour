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
	// class, r refers to row (height) and c refers to column (width). NB: games
	// over 10x10 will not be displayed properly, but they should play normally.

	public final int rows;
	public final int columns;

	// The board is a two-dimensional int matrix (-1 is empty). Board spaces are
	// checked with board[r][c] (board[h][w]).
	public int[][] boardArray = new int[][] {};

	public Board(int rowTotal, int columnTotal) {
		// Set the width and height variables
		rows = rowTotal;
		columns = columnTotal;

		// Initialize a board to the proper dimensions
		boardArray = new int[rows][columns];

		// Set initial values to -1 to indicate empty
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				boardArray[row][col] = -1;
			}
		}
	}
}