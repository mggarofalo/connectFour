package connectFour;

import java.util.Random;

public class Game {
	// Method list:
	// + pickFirstPlayer generates a random player order
	// + readBoard returns the current board state for AI reading
	// + readLog returns the game log for AI reading
	// + tryMove takes a player ID and column ID and returns a success value
	// + makeMove updates the board and logs the move
	// + checkForWinOrDraw runs checkForWin and checkForDraw and return continue
	// (0), draw (-1), or winning player ID

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

		// If we've gotten this far, let's perform our state check and return
		return checkForWinOrDraw();
	}

	// Takes a player ID and move to log and add to the board
	public void makeMove(int player, BoardSquare move) {
		log.addMove(player, move);
		board.makeMove(player, move);
	}

	// Checks for a win (player ID) or a draw (-1)
	private int checkForWinOrDraw() {
		// If we have a win, return the player number
		int winner = checkForWin();
		if (winner != -1) {
			return winner;
		}

		// Otherwise return the checkForDraw response
		return checkForDraw();
	}

	// Checks for a win and returns the winning player ID or -1 for no winner
	private int checkForWin() {
		int winner = checkForWinVertical();
		// See if anybody has four in a row horizontally

		// See if anybody has four in a row ascending

		// See if anybody has four in a row descending

		return -1;
	}

	// Checks for a vertical win
	private int checkForWinVertical() {
		// Start at the bottom left corner
		int counter = 0;
		int c = 0; // Initial value
		int r = 0; // Initial value

		// For each row, starting at the bottom
		for (int r = 0; r < (height - 3); r++) {
			int winner = board.readBoardSquare(0, 0);
			for (int c = 0; c < width; ++c) {
				// For each column, starting at the left, but iterating early because we've
				// already set the initial value
				if (board.readBoardSquare(c, r) != winner) {
					// We don't have a winner yet. Hit the next row.
					break;
				} else {
					counter += 1;
					if (counter == 4) {
						return winner;
					}
				}
			}
		}

		return -1;
	}

	// Checks for a draw and returns 0 for draw or -1 for no draw
	private int checkForDraw() {
		// Since this happens after win checking, we can see if the board is full first
		if (log.readMoves().size() == (width * height)) {
			return 0;
		}

		// Else iterate through the board to see if it's possible for either player to
		// win
		if (false) {
			return 0;
		}

		return -1;
	}
}