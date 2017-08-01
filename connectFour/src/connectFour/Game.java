package connectFour;

import java.util.Random;

public class Game {
	/*
	 * Method list: + pickFirstPlayer generates a random player order + readBoard
	 * returns the current board state for AI reading + readLog returns the game log
	 * for AI reading + tryMove takes a player ID and column ID and returns a
	 * success value + makeMove updates the board and logs the move +
	 * checkForWinOrDraw runs checkForWin and checkForDraw and return continue (0),
	 * draw (-1), or winning player ID
	 */

	private int rowTotal;
	private int columnTotal;
	/*
	 * private int players; private Object[] player; // TODO: Convert to player
	 * array private int firstPlayer; private int playerTurn;
	 */

	// Initialize the board and log.
	private Board board = new Board(rowTotal, columnTotal);
	private BoardMoveLog log = new BoardMoveLog();

	// Initialize a game
	public Game() {
		columnTotal = 7;
		rowTotal = 6;
		/*
		 * players = 2; player = new Object[players]; firstPlayer = pickFirstPlayer();
		 * playerTurn = firstPlayer;
		 */
	}

	/*
	 * private int pickFirstPlayer() { Random r = new Random(); return
	 * r.nextDouble() >= 0.5 ? 1 : 0; }
	 */

	// The board is private so that only the Board class can write to it. However,
	// so that the AI can read it, we have a read method.
	public int[][] readBoard() {
		return board.readBoard();
	}

	// Same as above with the log. This will be necessary when implementing advanced
	// AI strategy, since it will be necessary to see the opponent's previous play.
	public BoardMoveLog readLog() {
		return log;
	}

	// Takes a player ID and column to log.
	public int tryMove(int player, int col) {
		// If the column isn't valid, return -2
		if (col < 0 || col >= columnTotal) {
			return -2;
		}

		// Get the lowest row now so we only have to get it once per move attempt
		int lowestRow = board.getLowestRow(col);

		// If the column is full, return -3
		if (lowestRow == -1) {
			return -3;
		}

		// First, convert the column to a move.
		BoardSquare move = new BoardSquare(col, lowestRow);

		// Then call the logMove overload
		makeMove(player, move);

		// If we've gotten this far, let's perform our state check and return
		return checkForWinOrDraw();
	}

	// Takes a player ID and move to log and add to the board
	private void makeMove(int player, BoardSquare move) {
		log.addMove(player, move);
		board.makeMove(player, move);
	}

	// Checks for a win (player ID) or a draw (-1)
	private int checkForWinOrDraw() {
		// Returns:
		// 0 = Game continues
		// 1 = Player 1 wins (checkForWin)
		// 2 = Player 2 wins (checkForWin)
		// -1 = Draw detected (checkForDraw)

		// Check for a winner
		int winner = GameOverChecker.checkForWin(this, board, true, true);

		// If we have a win, return the player number
		if (winner != 0) {
			return winner;
		}

		// Otherwise check for a draw
		int draw = GameOverChecker.checkForDraw(this, board);

		// Return an appropriate value
		if (draw == 0) {
			return -1;
		} else {
			return 0;
		}
	}
}