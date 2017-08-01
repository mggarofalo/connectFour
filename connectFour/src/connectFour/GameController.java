package connectFour;

import java.util.Random;
import java.util.Scanner;

public class GameController {

	private Scanner key = new Scanner(System.in);
	private int columns;
	private PlayerController[] playerController;
	private BoardController boardController;
	private int turn;
	private boolean gameOver = false;

	public GameController(PlayerController[] playerController, int rows, int columns) {
		this.columns = columns;
		this.playerController = playerController;
		this.boardController = new BoardController(rows, columns);
		this.turn = pickFirstPlayer();
	}

	private int pickFirstPlayer() {
		Random r = new Random();
		return r.nextDouble() >= 0.5 ? 1 : 0;
	}

	public void play() {
		do {
			int selectedColumn;
			int tryMoveResponse;

			// Print the prompt
			System.out.println("Your turn, " + playerController[turn].getPlayer().getName() + ". Pick a column (1-"
					+ (columns + 1) + "): ");

			// Handle the input
			selectedColumn = key.nextInt();

			// Interpret the response
			tryMoveResponse = handleTryMoveResponse(boardController.tryMove(turn, selectedColumn));

			// If the game is over, change the boolean so the program can exit
			if (tryMoveResponse == 1) {
				gameOver = true;
			}

			// Finally, swap the turn
			if (turn == 0) {
				turn = 1;
			} else {
				turn = 0;
			}
		} while (!gameOver);
	}

	public int handleTryMoveResponse(int response) {
		// The return value here is 0 for continue, -1 for retry, and 1 win or draw
		// detected.
		if (response == 0) {
			// Game continues
			return 0;
		} else if (response == -3) {
			System.out.println("Sorry, but that column is full.");
			return -1;
		} else if (response == -2) {
			System.out.println("Sorry, but that isn't a valid column selection.");
			return -1;
		} else if (response == -1) {
			System.out.println("Looks like a draw to me. Game over, losers.");
			return 1;
		} else {
			displayWinMessage(response);
			return 1;
		}
	}

	private void displayWinMessage(int winner) {
		System.out.println("Congratulations on your win, " + playerController[winner - 1].getPlayer().getName() + "!");
	}
}