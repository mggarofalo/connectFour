package main;

import java.util.Scanner;
import java.util.Random;

public class ConnectFour {

	// Note: A maximum of 62 AI players are permitted because I don't want to go
	// through token-getting outside of [A-Za-z0-9]

	// Default values
	static int columns = 7;
	static int rows = 6;
	static int winLength = 4;
	static int players[] = new int[] { 1, 1 }; // Default value is 1 human (0) and 1 AI (0)

	// Utilities
	static Scanner key = new Scanner(System.in);
	static String[] choicesYesNo = new String[] { "Y", "YES", "N", "NO" };
	static String choice;
	public static Random rand = new Random();

	// Player components
	static String[] name = new String[players[0] + players[1]];
	static String[] token = new String[players[0] + players[1]];
	static boolean[] gender = new boolean[players[0] + players[1]];

	// Game components
	static PlayerController[] playerController = new PlayerController[players[0] + players[1]];
	static GameController gameController;

	public static void main(String[] args) {
		Utilities.println("Welcome to Connect Four!");
		Utilities.println("I have two options for you:");
		Utilities.println();
		Utilities.println("1. Human vs. AI");
		Utilities.println("2. Human vs. Human");
		Utilities.println();
		Utilities.print("What would you like to play? ");

		String gameTypeChoice = Utilities.makeUserInputStringFromArray(new String[] { "1", "2" }, "choice", false,
				"That choice is not valid.");

		Utilities.println();

		if (gameTypeChoice.equals("1")) {
			Utilities.print("Would you like to play a standard human vs. AI game of Connect-Four with "
					+ (players[0] + players[1]) + " players and " + columns + " columns of " + rows + " rows? (Y/N) ");
			setUpHumanVsAIGame();
		} else if (gameTypeChoice.equals("2")) {
			Utilities.print("Would you like to play a standard " + (players[0] + players[1])
					+ "-player game of Connect-Four with " + columns + " columns of " + rows + " rows? (Y/N) ");
			setUpHumanVsHumanGame();
		}

		// Ask for players' names and tokens
		setUpPlayers();

		// Initialize the game controller
		gameController = new GameController(playerController, rows, columns, winLength);

		// Show game instructions
		printInstructions();

		// Start the game
		gameController.play();

		// When a winner is found, gameController.play() exits and we display a message
		Utilities.println("Thanks for playing!");

		key.close();
	}

	private static void setUpHumanVsAIGame() {
		if (!Utilities.userSaysYes()) {
			Utilities.print("How many human players are there? ");
			players[0] = Utilities.makeUserInputAPositiveNumber();

			Utilities.print("And how many AI players would you like? ");
			players[1] = Utilities.makeUserInputAPositiveNumber();

			name = new String[players[0] + players[1]];
			token = new String[players[0] + players[1]];
			playerController = new PlayerController[players[0] + players[1]];

			setUpCustomRules();
		}
	}

	private static void setUpHumanVsHumanGame() {
		// Reset to humans-only
		players[0] = 2;
		players[1] = 0;

		if (!Utilities.userSaysYes()) {
			Utilities.print("How many human players are there? ");
			players[0] = Utilities.makeUserInputAPositiveNumber();

			name = new String[players[0]];
			token = new String[players[0]];
			playerController = new PlayerController[players[0]];

			setUpCustomRules();
		}
	}

	public static void setUpCustomRules() {
		Utilities.print("How many rows would you like? ");
		rows = Utilities.makeUserInputAPositiveNumber();

		Utilities.print("How many columns would you like? ");
		columns = Utilities.makeUserInputAPositiveNumber();

		Utilities.print("And how many tokens in a row should be required to win? ");
		winLength = Utilities.makeUserInputAPositiveNumberSmallerThanArrayMax(new int[] { rows, columns });
	}

	private static void setUpPlayers() {
		for (int i = 0; i < players[0]; i++) {
			Utilities.println();
			Utilities.print("Hey there, Player " + (i + 1) + ". What's your name? ");
			name[i] = Utilities.makeUserInputUniqueString(name, "name", null, false);

			Utilities.print("And what symbol would you like as your token? ");
			token[i] = Utilities.makeUserInputUniqueString(token, "token", String.valueOf(columns).length(), true);

			// We never refer to the human player's gender, so we're just passing in true
			// because it doesn't matter
			playerController[i] = new PlayerController(name[i], token[i], i, true, true);
		}

		// Set up the AI players
		for (int i = players[1]; i < (players[0] + players[1]); i++) {
			playerController[i] = new PlayerController(PlayerGenerator.generateAIPlayer(name, token, i));
		}
	}

	private static void printInstructions() {
		Utilities.println();
		Utilities.println("Looks like we're ready to play. I've randomized the play order and "
				+ playerController[gameController.game.turn].getName() + " ("
				+ playerController[gameController.game.turn].getToken() + ") is going first.");
		Utilities.println("When it's your turn, input a column number to place a token.");
		Utilities.println("Here's the board!");
	}
}