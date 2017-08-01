package main;

import java.util.Scanner;
import java.util.Random;

public class ConnectFour {

	public static Random rand = new Random();

	public static void main(String[] args) {
		int columns = 7;
		int rows = 6;
		int players = 2;
		String[] name = new String[players];
		String[] token = new String[players];
		PlayerController[] playerController = new PlayerController[players];
		GameController gameController;
		Scanner key = new Scanner(System.in);

		System.out.println("Welcome to Connect Four!");
		System.out.print("Would you like to play a normal " + players + "-player game with " + columns + " columns of "
				+ rows + " rows? (Y/N) ");

		if (Utilities.makeUserInputStringFromArray(new String[] { "Y", "N", "y", "n" }, "choice", false)
				.equalsIgnoreCase("N")) {
			System.out.println("How many players are there? ");
			players = Utilities.makeUserInputANumber();
			name = new String[players];
			token = new String[players];
			playerController = new PlayerController[players];

			System.out.print("How many columns would you like? ");
			columns = Utilities.makeUserInputANumber();

			System.out.print("And how many rows would you like? ");
			rows = Utilities.makeUserInputANumber();
		}

		/*
		 * System.out.println();
		 * System.out.print("Would you like to play against the computer? (Y/N) ");
		 * 
		 * if (key.next().equalsIgnoreCase("Y")) { player[0] = }
		 */

		// If they didn't say no, then too bad

		for (int i = 0; i < players; i++) {
			System.out.println();
			System.out.print("Hey there, Player " + (i + 1) + ". What's your name? ");
			name[i] = Utilities.makeUserInputUniqueString(name, "name", null);

			System.out.print("And what symbol would you like as your token? ");
			token[i] = Utilities.makeUserInputUniqueString(token, "token", String.valueOf(columns).length());

			playerController[i] = new PlayerController(name[i], token[i], i, true);
		}

		System.out.println();
		System.out.println("Looks like we're ready to play. I've randomized the play order.");
		System.out.println("When it's your turn, input a column number to place a token.");
		System.out.println("Here's the board!");
		System.out.println();

		gameController = new GameController(playerController, rows, columns);
		gameController.play();

		System.out.println("Thanks for playing!");

		key.close();
	}
}