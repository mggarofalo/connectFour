package main;

import java.util.List;
import java.util.ArrayList;

public class BoardChecker {

	private static List<String> dir;
	private static BoardController boardController;
	private static int[] winner = new int[4];

	public static BoardSquare[] checkForThreats(BoardController bc, BoardSquare square) {
		return new BoardSquare[] { new BoardSquare(0, 0), new BoardSquare(0, 0) };
	}

	public static boolean isWinningMove(BoardController bc, BoardSquare square) {
		dir = setUpDirectionArray(true);
		boardController = bc;

		for (int i = 0; i < dir.size(); i++) {
			if (enoughSpaceInDirection(square.row(), square.col(), dir.get(i))) {
				boolean isWinner = isWinner(square.row(), square.col(), dir.get(i));

				if (isWinner) {
					return isWinner;
				}
			}
		}

		return false;
	}

	public static int checkForWin(BoardController bc) {
		// Returns: 0 = No win detected, 1 = Player 1 won, 2 = Player 2 won
		dir = setUpDirectionArray(false);
		boardController = bc;

		for (int row = 0; row < boardController.height(); row++) {
			for (int col = 0; col < boardController.width(); col++) {
				for (int i = 0; i < dir.size(); i++) {
					if (enoughSpaceInDirection(row, col, dir.get(i))) {
						winner[i] = checkForWinner(row, col, dir.get(i));
					}
				}
			}
		}

		// If we found a winner anywhere, let's return the ID
		for (int i = 0; i < winner.length; i++) {
			if (winner[i] == 0) {
			} else {
				return winner[i];
			}
		}

		// If we didn't find a winner, let's return 0
		return 0;
	}

	public static int checkForDraw(BoardController bc) {
		if (bc.readLog().getMoves().size() == (bc.width() * bc.height())) {
			return 0;
		} else {
			// Iterate through the board to see if there are possible wins. If so, return 0.
		}

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

	private static boolean enoughSpaceInDirection(int row, int col, String dir) {
		if (dir.length() == 1) {
			if (dir.equals("N")) {
				return (row >= (boardController.winLength() - 1));
			} else if (dir.equals("S")) {
				return (row <= (boardController.height() - boardController.winLength()));
			} else if (dir.equals("E")) {
				return (col <= (boardController.width() - boardController.winLength()));
			} else if (dir.equals("W")) {
				return (col >= (boardController.winLength() - 1));
			} else {
				return false;
			}
		} else {
			boolean ret = true;
			char[] d = dir.toCharArray();

			for (int i = 0; i < d.length; i++) {
				if (!enoughSpaceInDirection(row, col, String.valueOf(d[i]))) {
					ret = false;
				}
			}

			return ret;
		}
	}

	private static boolean isWinner(int row, int col, String direction) {
		Object[] squares = iterateAndRead(row, col, direction);
		int equality = checkEquality(squares);
		if (equality == -1 || equality == 0) {
			return false;
		} else {
			return true;
		}
	}

	private static int checkForWinner(int row, int col, String direction) {
		return checkEquality(iterateAndRead(row, col, direction));
	}

	private static Object[] iterateAndRead(int row, int col, String direction) {
		BoardSquare checkSquare = new BoardSquare(row, col);
		Object[] squares = new Object[boardController.winLength()];

		for (int i = 0; i < boardController.winLength(); i++) {
			squares[i] = boardController.readBoardSquare(checkSquare);
			checkSquare.move(direction);
		}

		return squares;
	}

	private static int checkEquality(Object[] squares) {
		if (Utilities.allAreEqual(squares) && (int) squares[0] != -1) {
			return (int) squares[0] + 1;
		} else {
			return 0;
		}
	}
}