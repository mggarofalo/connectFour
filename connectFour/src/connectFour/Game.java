package connectFour;

import java.util.Random;

public class Game {
	// Method list:
	// pickFirstPlayer generates a random player order
	// readBoard returns the current board state for AI reading
	// readLog returns the game log for AI reading
	// tryMove takes a player ID and column ID and returns a success value
	// makeMove updates the board and logs the move

	private final int width = 7;
	private final int height = 6;
	private final int players = 2;
	private Object[] player = new Object[players]; // TODO: Convert to player array
	private final int firstPlayer = pickFirstPlayer();
	private int playerTurn = firstPlayer; // Initial value

	// Initialize the board and log.
	private Board board = new Board(width, height);
	private BoardMoveLog log = new BoardMoveLog();

	// Initialize a game
	public Game() {
	}

	private int pickFirstPlayer() {
		Random r = new Random();
		return r.nextDouble() >= 0.5 ? 1 : 0;
	}

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
	public int tryMove(int player, int c) {
		// If the player ID is invalid, return 1
		if (player != 0 || player != 1) {
			return 1;
		}

		// If the column isn't valid, return 2
		if (c < 0 || c > width) {
			return 2;
		}

		// Get the lowest row now so we only have to get it once per move attempt
		int lowestRow = board.getLowestRow(c);

		// If the column is full, return 3
		if (lowestRow == -1) {
			return 3;
		}

		// First, convert the column to a move.
		BoardSquare move = new BoardSquare(c, lowestRow);

		// Then call the logMove overload
		makeMove(player, move);

		// If we've gotten this far, let's send back a success value.
		return 0;
	}

	// Takes a player ID and move to log and add to the board
	public void makeMove(int player, BoardSquare move) {
		log.addMove(player, move);
		board.makeMove(player, move);
	}
}