package main;

import java.util.Scanner;
import java.util.Random;

public class ConnectFour {

	// Note: A maximum of 62 AI players are permitted because I don't want to go
	// through token-getting outside of [A-Za-z0-9]

	static int columns = 7; // Default value
	static int rows = 6; // Default value
	static int winLength = 4; // Default value
	static int players = 2; // Default value
	static int playersAI = 0; // Default value

	static Scanner key = new Scanner(System.in);
	public static Random rand = new Random();

	static String[] name = new String[players];
	static String[] token = new String[players];
	static PlayerController[] playerController = new PlayerController[players];
	static GameController gameController;

	public static void main(String[] args) {
		Utilities.println("Welcome to Connect Four!");

		Utilities.print("Would you like to play a human vs. AI game of Connect-Four with " + columns + " columns of "
				+ rows + " rows? (Y/N) ");
		String choice = Utilities.makeUserInputStringFromArray(new String[] { "Y", "YES", "N", "NO" }, "choice", false);
		if (choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("YES")) {
			setUpHumanVsAIGame();
		} else {
			Utilities.print("Would you like to play a normal " + players + "-player game of Connect-Four with "
					+ columns + " columns of " + rows + " rows? (Y/N) ");

			setUpHumanVsHumanGame();
		}
		key.close();
	}

	private static void setUpHumanVsAIGame() {
		Utilities.print("How many players are there? ");
		players = Utilities.makeUserInputAPositiveNumber();

		Utilities.print("And how many AI players would you like? ");
		playersAI = Utilities.makeUserInputAPositiveNumber();

		name = new String[players];
		token = new String[players];

		playerController = new PlayerController[players + playersAI];

		// Ask for players' names and tokens
		setUpPlayers();

		// Show game instructions
		printInstructions();

		// Initialize the game controller
		gameController = new GameController(playerController, rows, columns, winLength);

		// Start the game
		gameController.play();

		// When a winner is found, gameController.play() exits and we display a message
		Utilities.println("Thanks for playing!");

	}

	private static void setUpHumanVsHumanGame() {
		String choice = Utilities.makeUserInputStringFromArray(new String[] { "Y", "YES", "N", "NO" }, "choice", false);

		if (choice.equalsIgnoreCase("N") || choice.equalsIgnoreCase("NO")) {
			Utilities.print("How many players are there? ");
			players = Utilities.makeUserInputAPositiveNumber();
			name = new String[players];
			token = new String[players];
			playerController = new PlayerController[players];

			Utilities.print("How many rows would you like? ");
			rows = Utilities.makeUserInputAPositiveNumber();

			Utilities.print("How many columns would you like? ");
			columns = Utilities.makeUserInputAPositiveNumber();

			Utilities.print("And how many tokens in a row should be required to win? ");
			winLength = Utilities.makeUserInputAPositiveNumberSmallerThanArrayMax(new int[] { rows, columns });
		}

		// Ask for players' names and tokens
		setUpPlayers();

		// Show game instructions
		printInstructions();

		// Initialize the game controller
		gameController = new GameController(playerController, rows, columns, winLength);

		// Start the game
		gameController.play();

		// When a winner is found, gameController.play() exits and we display a message
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

		for (int i = players; i < (players + playersAI); i++) {
			playerController[i] = new PlayerController(PlayerGenerator.generateAIPlayer(name, token, i));
		}
	}

	private static void printInstructions() {
		Utilities.println();
		Utilities.println("Looks like we're ready to play. I've randomized the play order.");
		Utilities.println("When it's your turn, input a column number to place a token.");
		Utilities.println("Here's the board!");
	}
}