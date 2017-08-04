package main;

import java.util.ArrayList;
import java.util.Collections;

public class PlayerGeneratorAI {

	private static ArrayList<String[]> prefixes = new ArrayList<String[]>();
	private static ArrayList<String[]> firstNames = new ArrayList<String[]>();
	private static ArrayList<String> lastNames = new ArrayList<String>();

	public static Player generateAIPlayer(String[] name, String[] token, int index) {
		setUpNameArrays();
		return new Player(generateName(name), generateToken(token), index, false);
	}

	private static String generateName(String[] name) {
		String proposal;
		int loops = calculatePossibleLoops();

		do {
			proposal = constructName();
			loops -= 1;
		} while (Utilities.stringExistsInStringArray(name, proposal, true) && loops > 0);
		return proposal;
	}

	private static int calculatePossibleLoops() {
		int loops = 1;

		// Count up how many prefixes are possible
		for (int i = 0; i < prefixes.size(); i++) {
			loops = loops * (prefixes.get(i).length);
		}

		// And how many first names
		for (int i = 0; i < firstNames.size(); i++) {
			loops = loops * (firstNames.get(i).length);
		}

		// And how many last names
		loops = loops * lastNames.size();

		return loops;
	}

	private static String constructName() {
		int gender = (ConnectFour.rand.nextInt() > 0.5 ? 0 : 1);

		String prefix = prefixes.get(gender)[ConnectFour.rand.nextInt(prefixes.get(gender).length)];
		String firstName = firstNames.get(gender)[ConnectFour.rand.nextInt(firstNames.get(gender).length)];
		String lastName = lastNames.get(ConnectFour.rand.nextInt(lastNames.size()));

		return new String(prefix + " " + firstName + " " + lastName);
	}

	private static String generateToken(String[] token) {
		ArrayList<String> possibleTokens = getPossibleTokens();

		// Go through the list and get the first available token
		for (int i = 0; i < possibleTokens.size(); i++) {
			if (!Utilities.stringExistsInStringArray(token, possibleTokens.get(i))) {
				return possibleTokens.get(i);
			}
		}

		// If we made it here, then none of the possible tokens were available.
		throw new IllegalArgumentException(
				"We have an AI trying to get a token, but it seems all of them are taken. That shouldn't have happened.");
	}

	private static ArrayList<String> getPossibleTokens() {
		ArrayList<String> possibleTokens = new ArrayList<String>();

		// Add all capital letters
		for (int i = 0; i < 26; i++) {
			possibleTokens.add(Character.toString((char) (i + 64)));
		}

		// Add all lower case letters
		for (int i = 0; i < 26; i++) {
			possibleTokens.add(Character.toString((char) (i + 96)));
		}

		// Add all numbers
		for (int i = 0; i < 10; i++) {
			possibleTokens.add(Character.toString((char) (i + 47)));
		}

		// Shuffle the deck
		Collections.shuffle(possibleTokens);

		return possibleTokens;
	}

	private static void setUpNameArrays() {
		prefixes.add(new String[] { "Mr.", "Sir", "Lord", "Duke", "His Majesty the King" });
		prefixes.add(new String[] { "Ms.", "Madam", "Lady", "Duchess", "Her Majesty the Queen" });

		firstNames.add(new String[] { "Lothar", "Childebert", "Clovis", "Theuderic", "Childeric" });
		firstNames.add(new String[] { "Basina", "Bilichildis", "Brunhild", "Clotild", "Theudechild" });

		String[] lastName = new String[] { "Smithbot", "Johnsonbot", "Williamsbot", "Jonesbot", "Brownbot", "Davisbot",
				"Millerbot", "Wilsonbot", "Moorebot", "Taylorbot" };

		for (int i = 0; i < lastName.length; i++) {
			lastNames.add(lastName[i]);
		}
	}
}