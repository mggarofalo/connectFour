package main;

import java.util.ArrayList;

public class BoardChecker {

	private static GameController gc = ConnectFour.gameController;
	private static BoardController bc = ConnectFour.gameController.game.boardController;

	public static boolean isDraw() {
		if (bc.getLog().getMoves().size() == (bc.width() * bc.height())) {
			return true;
		} else {
			// Iterate through the board to see if there are possible wins. If so, return 0.
		}

		return false;
	}

	// Checks for a win (player ID)
	public static int checkForWin(BoardMove move) {
		if (BoardChecker.isWinningMove(move)) {
			// If we have a win, return the player number
			return (move.getPlayer().index + 1);
		} else {
			// We have to check the whole board for a win, since wins can come by connecting
			// two runs
			for (int row = (bc.height() - 1); row >= 0; row--) {
				for (int col = 0; col < bc.width(); col++) {
					// Create a new BoardMove at the bottom left, iterating right and up
					BoardMove moveToCheckForWin = new BoardMove(move.getDate(),
							bc.readBoardSquare(new BoardSquare(row, col)), new BoardSquare(row, col));
					if (isWinningMove(moveToCheckForWin)) {
						return (move.getPlayer().index + 1);
					}
				}
			}
		}

		// The game continues
		return 0;
	}

	public static boolean isWinningMove(BoardMove move) {
		ArrayList<Direction> direction = Direction.setUpDirectionArray(true);

		for (Direction dir : direction) {

			if (enoughSpaceInDirection(new BoardSquare(move.getBoardSquare()), dir.direction, gc.game.getWinLength())) {
				boolean isWinner = isWinner(move, dir.direction);

				if (isWinner) {
					return isWinner;
				}
			}
		}

		return false;
	}

	public static ArrayList<ThreatMove> findMovesThatExtendRun() {
		gc.game.boardController.printBoard(gc.game.playerController);

		ArrayList<Direction> direction = Direction.setUpDirectionArray(true);
		ArrayList<BoardSquare> playableSquares = bc.getPlayableSquares();

		// This object has four sets: BoardSquare (0), direction (1), run length (2),
		// and run owner (3)
		ArrayList<ThreatMove> threatMoves = new ArrayList<ThreatMove>();

		for (BoardSquare playableSquare : playableSquares) {
			// We start by understanding that we can only affect runs we can play on

			for (Direction dir : direction) {
				// Check each playable square to see if any winLength run arrays include it in
				// any direction

				for (int numberOfSpacesToMove = (gc.game.getWinLength()
						- 1); numberOfSpacesToMove > 1; numberOfSpacesToMove--) {
					// Start checking with the runs that are one token away from a win; end before
					// checking single tokens because they're not really "runs"

					BoardSquare startingSquare = new BoardSquare(playableSquare);

					// Skip if we can't move far enough; we'll come back to it with a lower number
					if (!enoughSpaceInDirection(startingSquare, dir.opposite, numberOfSpacesToMove)) {
						continue;
					} else {
						startingSquare.move(dir.opposite, numberOfSpacesToMove);
					}

					for (int k = numberOfSpacesToMove; k >= 0; k--) {
						// We're going to start the number of spaces we moved away from the origin and
						// then iterate back, checking in blocks of game-winning length as we go.

						if (!enoughSpaceInDirection(startingSquare, dir.direction, gc.game.getWinLength())) {
							// If we can't move far enough and we're moving in that direction, we should
							// break because it's not going to get any better
							break;
						}

						// Read the squares into a ThreatMove object; we don't know if it has an owner
						// yet, because we haven't determined whether or not it's a run
						ThreatMove threateningRun = new ThreatMove(
								iterateAndRead(startingSquare, dir.direction, gc.game.getWinLength()), playableSquare,
								null, 0);

						boolean mayBeRun = true;
						for (int l = 0; l < threateningRun.threatSquares.size(); l++) {
							// Now find out if this isn't a potential run

							Player threateningPlayer = bc.readBoardSquare(threateningRun.threatSquares.get(l));

							if (threateningPlayer == null) {
								continue;
							} else if (threateningRun.runOwner == null) {
								threateningRun.runOwner = threateningPlayer;
							} else if (threateningPlayer != threateningRun.runOwner) {
								mayBeRun = false;
								break;
							}
						}

						if (!mayBeRun || threateningRun.runOwner == null) {
							// If it's not a potential run, let's move to the next loop; if it is, add it to
							// the list
							continue;
						}

						for (BoardSquare square : threateningRun.threatSquares) {
							// Calculate the severity (i.e. how many spaces are held by the owner)
							if (bc.readBoardSquare(square) == null || threateningRun.runOwner == null) {
								continue;
							} else if (bc.readBoardSquare(square).index == threateningRun.runOwner.index) {
								threateningRun.severity += 1;
							}
						}

						// Now we need to make sure we tell the object which space in it can be played
						threateningRun.runExtendingSquare = playableSquare;

						if (threateningRun.severity >= (gc.game.getWinLength() - 2)) {
							// If we've gotten this far, we know it's a run and have an owner and a severity
							// ranking. Let's add it to the ArrayList if it hits our threshold
							if (!threatMoves.contains(threateningRun)) {
								threatMoves.add(threateningRun);
							}
						}
					}
				}
			}
		}

		return threatMoves;
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
		} else if (bc.readBoardSquare(square) != null) {
			// The square isn't empty
			return 1;
		} else {
			// The square is playable
			return 0;
		}
	}

	// Gets the lowest open row for a given column
	public static int getLowestRow(int col) {
		for (int row = (bc.height() - 1); row >= 0; row--) {
			BoardSquare possibleLowestSpace = new BoardSquare(row, col);
			if (bc.readBoardSquare(possibleLowestSpace) == null) {
				return row;
			}
		}

		// If the column is full, return -1
		return -1;
	}

	private static boolean enoughSpaceInDirection(BoardSquare checkSquare, String direction, int length) {
		if (direction.length() == 1) {
			if (direction.equals("N")) {
				return (checkSquare.row() >= (length));
			} else if (direction.equals("S")) {
				return (checkSquare.row() < (bc.height() - length));
			} else if (direction.equals("E")) {
				return (checkSquare.col() < (bc.width() - length));
			} else if (direction.equals("W")) {
				return (checkSquare.col() >= (length));
			} else {
				return false;
			}
		} else {
			boolean ret = true;
			char[] d = direction.toCharArray();

			for (char c : d) {
				if (!enoughSpaceInDirection(checkSquare, String.valueOf(c), length)) {
					ret = false;
				}
			}

			return ret;
		}
	}

	private static boolean isWinner(BoardMove checkMove, String direction) {
		ArrayList<BoardSquare> squares = iterateAndRead(checkMove.getBoardSquare(), direction, gc.game.getWinLength());

		if (checkMove.getPlayer() != null && checkMove.getPlayer().index == checkEquality(squares)) {
			return true;
		} else {
			return false;
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
		Player player = bc.readBoardSquare((BoardSquare) squares.get(0));
		if (player == null || !Utilities.allAreEqual(bc, squares)) {
			return -1;
		} else {
			return bc.readBoardSquare((BoardSquare) squares.get(0)).index;
		}
	}
}