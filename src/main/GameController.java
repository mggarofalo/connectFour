package main;

import java.util.Random;
import java.util.Scanner;

public class GameController {

	private Scanner key = new Scanner(System.in);
	private PlayerController[] playerController;
	private BoardController boardController;
	private int turn;
	private boolean gameOver = false;

	public GameController(PlayerController[] playerController, int rows, int columns) {
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

			// Print the board
			boardController.printBoard();

			// Print the prompt
			System.out.print("Your turn, " + playerController[turn].getPlayer().getName() + ". Pick a column (1-"
					+ boardController.width() + "): ");

			// Handle the input
			selectedColumn = key.nextInt();

			// Convert selection to index
			selectedColumn -= 1;

			// Interpret the response
			tryMoveResponse = handleTryMoveResponse(boardController.tryMove(turn, selectedColumn));

			if (tryMoveResponse == 1) {
				// If the game is over, change the boolean so the program can exit
				gameOver = true;
			} else if (tryMoveResponse == 0) {
				// If the move was valid, swap turns
				swapTurn();
			}
		} while (!gameOver);
	}

	public int handleTryMoveResponse(int response) {
		// The return value of this function is 0 for continue, -1 for retry, and 1 win
		// or draw detected.
		int retVal;

		System.out.println();

		if (response == 0) {
			// Game continues
			retVal = 0;
		} else if (response == -3) {
			System.out.println("Sorry, but that column is full.");
			retVal = -1;
		} else if (response == -2) {
			System.out.println("Sorry, but that isn't a valid column selection.");
			retVal = -1;
		} else if (response == -1) {
			System.out.println("Looks like a draw to me. Game over, losers.");
			retVal = 1;
		} else {
			displayWinMessage(response);
			retVal = 1;
		}

		System.out.println();
		return retVal;
	}

	private void displayWinMessage(int winner) {
		System.out.println("Congratulations on your win, " + playerController[winner - 1].getPlayer().getName() + "!");
	}

	private void swapTurn() {
		if (turn == 0) {
			turn = 1;
		} else {
			turn = 0;
		}
	}
}