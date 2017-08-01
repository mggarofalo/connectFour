package main;

public class BoardIterator {

	private static int winLength = 4;

	private static BoardController boardController;
	private static BoardSquare checkSquare;

	public static int checkForWin(BoardController bc) {
		// Returns:
		// 0 = No win detected
		// 1 = Player 1 won
		// 2 = Player 2 won

		boardController = bc;

		for (int row = 0; row < boardController.height(); row++) {
			for (int col = 0; col < boardController.width(); col++) {

				// Check vertical
				if (row <= (boardController.height() - winLength)) {
					Object[] squares = new Object[winLength];
					checkSquare = new BoardSquare(row, col);
					for (int i = 0; i < winLength; i++) {
						squares[i] = readAndMove("S");
					}
					if (Utilities.allAreEqual(squares) && (int) squares[0] != -1) {
						return (int) squares[0] + 1;
					}
				}

				// Check horizontal
				if (col <= (boardController.width() - winLength)) {
					Object[] squares = new Object[winLength];
					checkSquare = new BoardSquare(row, col);
					for (int i = 0; i < winLength; i++) {
						squares[i] = readAndMove("E");
					}
					if (Utilities.allAreEqual(squares) && (int) squares[0] != -1) {
						return (int) squares[0] + 1;
					}
				}

				// Check descending
				// 4,5
				if (row < (boardController.height() - winLength + 1)
						&& (col < boardController.width() - winLength + 1)) {
					Object[] squares = new Object[winLength];
					checkSquare = new BoardSquare(row, col);
					for (int i = 0; i < winLength; i++) {
						squares[i] = readAndMove("SE");
					}
					if (Utilities.allAreEqual(squares) && (int) squares[0] != -1) {
						return (int) squares[0] + 1;
					}
				}

				// Check ascending (NOT CONFIRMED)
				if (row >= winLength && (col < boardController.width() - winLength + 1)) {
					Object[] squares = new Object[winLength];
					checkSquare = new BoardSquare(row, col);
					for (int i = 0; i < winLength; i++) {
						squares[i] = readAndMove("NE");
					}
					if (Utilities.allAreEqual(squares) && (int) squares[0] != -1) {
						return (int) squares[0] + 1;
					}
				}

			}
		}

		return 0;

	}

	public static int checkForDraw(BoardController bc) {
		if (bc.readLog().readMoves().size() == (bc.width() * bc.height())) {
			return 0;
		} else {
			// Iterate through the board to see if there are possible wins. If so, return 0.
		}

		return -1;
	}

	private static Object readAndMove(String dir) {
		Object o = boardController.readBoardSquare(checkSquare);
		checkSquare.move(dir);
		return o;
	}
}