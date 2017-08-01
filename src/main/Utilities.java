package main;

import java.util.Scanner;
import java.util.Arrays;

public class Utilities {

	private static Scanner key = new Scanner(System.in);

	public static boolean allAreEqual(Object[] o) {
		Object val = o[0];

		if (o.length > 1) {
			for (int i = 1; i < o.length; i++) {
				if (!o[i].equals(val)) {
					return false;
				}
			}
		}

		return true;
	}

	public static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static int makeUserInputANumber() {
		String input = key.next();

		while (!isInteger(input)) {
			System.out.print("Please provide a number: ");
			input = key.next();
		}

		return Integer.parseInt(input);
	}

	public static String makeUserInputStringFromArray(String[] values, String inputName, boolean caseSensitive) {
		String input = key.nextLine();

		while (!Arrays.asList(values).contains(input)) {
			System.out.print("That " + inputName + " is not valid. Try again: ");
			input = key.nextLine();
		}

		return input;
	}

	public static String makeUserInputUniqueString(String[] values, String inputName, Integer length) {
		String input = key.nextLine();

		while (length != null && input.length() > length) {
			System.out.print("That " + inputName + " is too long. Please enter " + length + " character"
					+ ((length != 1) ? "s" : "") + ": ");
			input = key.nextLine();
		}

		while (Arrays.asList(values).contains(input)) {
			System.out.print("Sorry, but that " + inputName + " is taken already. Try again: ");
			input = key.nextLine();
		}

		while (input.contains("\\s+")) {
			System.out.print("Sorry, but whitespace characters are not allowed. Try again: ");
			input = key.nextLine();
		}

		return input;
	}

	public static String padString(String s, String padChar, int length) {
		for (int i = 1; i < length; i++) {
			if (padChar == null) {
				s += s;
			} else {
				s += padChar;
			}
		}

		return s;
	}

}