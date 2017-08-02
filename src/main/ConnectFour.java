package main;

import java.util.Scanner;
import java.util.Random;

public class ConnectFour {

	public static Random rand = new Random();
	static int columns = 7;
	static int rows = 6;
	static int winLength = 4;
	static int players = 2;
	static String[] name = new String[players];
	static String[] token = new String[players];
	static PlayerController[] playerController = new PlayerController[players];
	static GameController gameController;
	static Scanner key = new Scanner(System.in);

	public static void main(String[] args) {
		Utilities.println("Welcome to Connect Four!");
		Utilities.print("Would you like to play a normal " + players + "-player game of " + winLength
				+ " in a row with " + columns + " columns of " + rows + " rows? (Y/N) ");

		setUpGame();

		key.close();
	}

	private static void setUpGame() {
		String choice = Utilities.makeUserInputStringFromArray(new String[] { "Y", "N" }, "choice", false);
		if (choice.equalsIgnoreCase("N")) {
			Utilities.println("How many players are there? ");
			players = Utilities.makeUserInputAPositiveNumber();
			name = new String[players];
			token = new String[players];
			playerController = new PlayerController[players];

			Utilities.print("How many columns would you like? ");
			columns = Utilities.makeUserInputAPositiveNumber();

			Utilities.print("How many rows would you like? ");
			rows = Utilities.makeUserInputAPositiveNumber();

			Utilities.print("And how many tokens in a row should be required to win? ");
			winLength = Utilities.makeUserInputAPositiveNumber();
		}

		setUpPlayers();

		gameController = new GameController(playerController, rows, columns, winLength);
		gameController.play();

		Utilities.println("Thanks for playing!");
	}

	private static void setUpPlayers() {
		for (int i = 0; i < players; i++) {
			Utilities.println();
			Utilities.print("Hey there, Player " + (i + 1) + ". What's your name? ");
			name[i] = Utilities.makeUserInputUniqueString(name, "name", null, false);

			Utilities.print("And what symbol would you like as your token? ");
			token[i] = Utilities.makeUserInputUniqueString(token, "token", String.valueOf(columns).length(), true);

			playerController[i] = new PlayerController(name[i], token[i], i, true);
		}

		printInstructions();
	}

	private static void printInstructions() {
		Utilities.println();
		Utilities.println("Looks like we're ready to play. I've randomized the play order.");
		Utilities.println("When it's your turn, input a column number to place a token.");
		Utilities.println("Here's the board!");
	}
}