package main;

import java.util.List;
import java.util.ArrayList;

public class BoardChecker {

	private static List<String> directions;
	private static GameController gc = ConnectFour.gameController;
	private static BoardController bc = ConnectFour.gameController.game.boardController;

	public static Object[][] findMoveThatExtendsRun(int length) {
		directions = setUpDirectionArray(true);

		// This object has three sets: BoardSquare (0), direction (1), and run owner (2)
		Object[][] extendingMoves = new Object[3][];
		int i = 0; // Object index

		// Check each possible move to see if it could block a winning run
		for (BoardSquare move : bc.getPossibleMoves()) {
			// Check each direction
			for (String d : directions) {

				// Skip if we can't move as far as we need to
				if (!enoughSpaceInDirection(move, d, length + 1)) {
					continue;
				}

				// Reinitialize so that we don't lose the possible move we're checking
				BoardSquare threatCheckStart = move;

				// Move in a direction
				threatCheckStart.move(d);

				// Read (winLength - 1) squares in a direction
				ArrayList<BoardSquare> squares = iterateAndRead(threatCheckStart, d, length);

				// Find out if this is a run and, if so, whose run it is
				int runOwner = checkEquality(squares);

				// If this is a threat, add the move to the list
				if (runOwner != -1 && runOwner != 0) {
					extendingMoves[0][i] = move;
					extendingMoves[1][i] = d;
					extendingMoves[2][i] = runOwner;
					i += 1;
					break;
				}
			}
		}

		return extendingMoves;
	}

	public static boolean isValidColumn(int col) {
		if (col < 0 || col >= gc.game.boardController.width()) {
			return false;
		} else {
			return true;
		}
	}

	public static int isLegalMove(BoardSquare square) {
		if (square.row() < 0 || square.row() >= gc.game.boardController.height()) {
			// Somehow the row selection isn't valid...
			throw new IllegalArgumentException(
					"Somehow the row isn't valid for " + gc.game.getCurrentPlayerName() + ". Weird.");
		} else if (bc.readBoardSquare(square) != -1) {
			// The square isn't empty
			return 1;
		} else {
			// The square is playable
			return 0;
		}
	}

	public static boolean isWinningMove(BoardMove move) {
		directions = setUpDirectionArray(true);

		for (int i = 0; i < directions.size(); i++) {
			if (enoughSpaceInDirection(new BoardSquare(move.getBoardSquare()), directions.get(i),
					gc.game.getWinLength())) {
				boolean isWinner = isWinner(new BoardSquare(move.getBoardSquare()), directions.get(i));

				if (isWinner) {
					return isWinner;
				}
			}
		}

		return false;
	}

	// Checks for a win (player ID) or a draw (-1)
	public static int checkForWin(BoardMove move) {
		// Returns:
		// 0 = Game continues
		// 1 = Player 1 wins (checkForWin)
		// 2 = Player 2 wins (checkForWin)

		// If we have a win, return the player number
		if (BoardChecker.isWinningMove(move)) {
			return (move.getPlayer().index + 1);
		} else {
			return 0;
		}
	}

	public static boolean isDraw() {
		if (bc.getLog().getMoves().size() == (bc.width() * bc.height())) {
			return true;
		} else {
			// Iterate through the board to see if there are possible wins. If so, return 0.
		}

		return false;
	}

	// Gets the lowest open row for a given column
	public static int getLowestRow(int col) {
		for (int row = (bc.height() - 1); row >= 0; row--) {
			if (bc.readBoardSquare(new BoardSquare(row, col)) == -1) {
				return row;
			}
		}

		// If the column is full, return -1
		return -1;
	}

	private static ArrayList<String> setUpDirectionArray(boolean checkAllDirections) {
		ArrayList<String> dir = new ArrayList<String>();

		dir.add("S");
		dir.add("SE");
		dir.add("E");
		dir.add("NE");

		if (checkAllDirections) {
			dir.add("SW");
			dir.add("W");
			dir.add("NW");
			dir.add("N");
		}

		return dir;
	}

	private static boolean enoughSpaceInDirection(BoardSquare checkSquare, String direction, int length) {
		if (direction.length() == 1) {
			if (direction.equals("N")) {
				return (checkSquare.row() >= (length - 1));
			} else if (direction.equals("S")) {
				return (checkSquare.row() <= (bc.height() - length));
			} else if (direction.equals("E")) {
				return (checkSquare.col() <= (bc.width() - length));
			} else if (direction.equals("W")) {
				return (checkSquare.col() >= (length - 1));
			} else {
				return false;
			}
		} else {
			boolean ret = true;
			char[] d = direction.toCharArray();

			for (int i = 0; i < d.length; i++) {
				if (!enoughSpaceInDirection(checkSquare, String.valueOf(d[i]), length)) {
					ret = false;
				}
			}

			return ret;
		}
	}

	private static boolean isWinner(BoardSquare checkSquare, String direction) {
		ArrayList<BoardSquare> squares = iterateAndRead(checkSquare, direction, gc.game.getWinLength());

		int equality = checkEquality(squares);
		if (equality == -1 || equality == 0) {
			return false;
		} else {
			return true;
		}
	}

	private static ArrayList<BoardSquare> iterateAndRead(BoardSquare startingSquare, String direction, int length) {
		ArrayList<BoardSquare> squares = new ArrayList<BoardSquare>(length);
		BoardSquare checkSquare = new BoardSquare(startingSquare);

		for (int i = 0; i < length; i++) {
			squares.add(new BoardSquare(checkSquare));
			checkSquare.move(direction);
		}

		return squares;
	}

	private static int checkEquality(ArrayList<BoardSquare> squares) {
		if (Utilities.allAreEqual(bc, squares) && bc.readBoardSquare(squares.get(0)) != -1) {
			return bc.readBoardSquare((BoardSquare) squares.get(0)) + 1;
		} else {
			return 0;
		}
	}
}