package connectFour;

import java.util.Scanner;

public class ConnectFour {

	public static void main() {
		int columns = 7;
		int rows = 6;
		String name;
		Scanner key = new Scanner(System.in);
		PlayerController[] playerController = new PlayerController[2];
		GameController gameController;

		System.out.println("Welcome to Connect Four!");
		System.out.print(
				"Would you like to play a normal game with " + columns + " columns of " + rows + " rows? (Y/N) ");

		if (key.next().equalsIgnoreCase("N")) {
			System.out.print("How many columns would you like? ");
			columns = key.nextInt();
			System.out.println();

			System.out.print("And how many rows would you like?");
			rows = key.nextInt();
			System.out.println();
		}

		/*
		 * System.out.println();
		 * System.out.print("Would you like to play against the computer? (Y/N) ");
		 * 
		 * if (key.next().equalsIgnoreCase("Y")) { player[0] = }
		 */

		// If they didn't say no, then too bad

		System.out.println();
		System.out.print("Okay, person who loaded me. What's your name?");

		name = key.next();
		playerController[0].initializePlayer(name, true);

		System.out.println();
		System.out.print("And what about you, friendo?");

		name = key.next();
		playerController[1].initializePlayer(name, true);

		System.out.println(
				"Looks like we're ready to play. Here's the board. When it's your turn, input a column number to place a token.");
		System.out.println();
		System.out.println();

		gameController = new GameController(playerController, rows, columns);
		gameController.play();

		System.out.println();
		System.out.println();
		System.out.println("Thanks for playing!");

		key.close();
	}

}
