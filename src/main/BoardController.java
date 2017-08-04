package main;

import java.util.ArrayList;

public class BoardController {

	private Board board;
	private BoardMoveLog log;

	// Initializes a game with dimensions
	public BoardController(int rows, int columns, int winLength) {
		// Convert row/column counts to an in[][]
		int[][] boardArray = new int[rows][columns];

		// Set initial values to empty (-1)
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				boardArray[row][col] = -1;
			}
		}

		board = new Board(boardArray);
		log = new BoardMoveLog();
	}

	// Returns the height of the board
	public int height() {
		return board.height();
	}

	// Returns the width of the board
	public int width() {
		return board.width();
	}

	// Returns the contents of a given BoardSquare
	public int readBoardSquare(BoardSquare square) {
		return board.boardArray[square.row()][square.col()];
	}

	// Same as above with the log. This will be necessary when implementing AI
	// strategy, since it will be necessary to see the opponent's previous play.
	public BoardMoveLog getLog() {
		return log;
	}

	// Gets all possible moves on the board
	public ArrayList<BoardSquare> getPossibleMoves() {
		ArrayList<BoardSquare> possibleMoves = new ArrayList<BoardSquare>(width());

		for (int i = 0; i < possibleMoves.size(); i++) {
			possibleMoves.add(new BoardSquare(BoardChecker.getLowestRow(i), i));
		}

		return possibleMoves;
	}

	// Takes a column and returns the BoardSquare in that column that constitutes a
	// valid move (or null if the column is full)
	public BoardSquare getMoveForColumn(int col) {
		BoardSquare move = null;

		int lowestRow = BoardChecker.getLowestRow(col);

		if (lowestRow != -1) {
			move = new BoardSquare(lowestRow, col);
		}

		return move;
	}

	// Takes a player ID and move to log and add to the board
	public void makeMove(BoardMove move) {
		log.addMove(move);
		board.boardArray[move.getBoardSquare().row()][move.getBoardSquare().col()] = move.getPlayer().index;
	}

	// Print the board to the console
	public void printBoard(PlayerController[] playerController) {
		// Print leading line
		Utilities.print("");

		// Print column labels
		for (int col = 0; col < board.width(); col++) {
			Utilities.print(" ");

			// Print the column header. If we're done, print a new line.
			Utilities.print(
					Utilities.padString(String.valueOf(col + 1), " ",
							String.valueOf(board.width()).length() + 1 - String.valueOf(col + 1).length()),
					(col == (board.width() - 1)));
		}

		// Loop through rows
		for (int row = 0; row < board.height(); row++) {
			// Initial border
			Utilities.print("|");

			// Loop through columns in row
			for (int col = 0; col < board.width(); col++) {
				// Print blank space if the space is empty; otherwise print the player number.
				if (board.boardArray[row][col] == -1) {
					Utilities.print(Utilities.padString(" ", null, String.valueOf(board.width()).length()));
				} else {
					Utilities.print(Utilities.padString(playerController[board.boardArray[row][col]].getToken(), " ",
							String.valueOf(board.width()).length() + 1 - String
									.valueOf(playerController[board.boardArray[row][col]].getToken()).length()));
				}

				// Dividing border
				Utilities.print("|");
			}

			// End the row and loop again
			Utilities.println();
		}
	}
}