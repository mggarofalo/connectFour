package connectFour;

public class Board {

	private int width = 7;
	private int height = 6;

	// Initialize the log
	private BoardMoveLog log = new BoardMoveLog();

	// The board is a two-dimensional integer matrix
	// where the value is player ID or -1 for empty
	private int[][] board = new int[][] {};

	public Board() {
		// Initialize a board to the width and height
		board = new int[width][height];
		
		// Set initial values to -1 to indicate empty
		for (int w = 0; w < width; w++) {
			for (int h = 0; h < height; h++) {
				board[w][h] = -1;
			}
		}
	}

	public void logMove(int player, int col) {
		// First, convert the column to a move.
		BoardSquare move = new BoardSquare(col, getLowestRow(col));

		// Then call the logMove overload
		logMove(player, move);
	}

	public void logMove(int player, BoardSquare move) {
		// If we pass a validity check (necessary because this is public)
		// then add the move to the move log and board
		if (isValidMove(move)) {
			log.addMove(player, move);
			board[move.col()][move.row()] = player;
		}
	}

	public boolean isValidMove(BoardSquare move) {
		// Checks the move list for a given BoardSquare
		for (int i = 0; i < log.squares.size(); i++) {

			if (log.squares.contains(move)) {
				return false;
			}
		}
		return true;
	}

	public int getLowestRow(int col) {
		return 1;
	}

}