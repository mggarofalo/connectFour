package main;

import java.util.Scanner;
import java.util.Arrays;

public class Utilities {

	private static Scanner key = new Scanner(System.in);

	// Region "Evaluators"

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

	public static boolean stringExistsInStringArray(String[] values, String toFind) {
		return stringExistsInStringArray(values, toFind, true);
	}

	public static boolean stringExistsInStringArray(String[] values, String toFind, boolean caseSensitive) {
		for (int i = 0; i < values.length; i++) {
			if (caseSensitive && toFind.equals(values[i])) {
				return true;
			} else if (!caseSensitive && toFind.equalsIgnoreCase(values[i])) {
				return true;
			}
		}

		return false;
	}

	// End Region

	// Region "Input Parsers"

	public static int makeUserInputAPositiveNumber() {
		String input = key.next();

		while (!isInteger(input) || Integer.parseInt(input) <= 0) {
			Utilities.print("Please provide a positive, non-zero number: ", false);
			input = key.next();
		}

		return Integer.parseInt(input);
	}

	public static String makeUserInputStringFromArray(String[] values, String inputName, boolean caseSensitive) {
		String input = key.nextLine();

		while (!stringExistsInStringArray(values, input, caseSensitive)) {
			Utilities.print("That " + inputName + " is not valid. Try again: ", false);
			input = key.nextLine();
		}

		return input;
	}

	public static String makeUserInputUniqueString(String[] values, String inputName, Integer length,
			boolean requireNonWhiteSpace) {
		String input = key.nextLine();

		// If, for some reason, we have an empty string, just re-open the function
		if (input.length() == 0 || input.replaceAll("\\s+", "").length() == 0) {
			input = makeUserInputUniqueString(values, inputName, length, requireNonWhiteSpace);
		}

		// First error should be if the input exceeds the length
		if (length != null && input.length() > length) {
			Utilities.print("That " + inputName + " is too long. Please enter " + length + " character"
					+ ((length != 1) ? "s" : "") + ": ", false);
			input = makeUserInputUniqueString(values, inputName, length, requireNonWhiteSpace);
		}

		// Second error should be if the input is the right length but already exists in
		// the array
		if (Arrays.asList(values).contains(input)) {
			Utilities.print("Sorry, but that " + inputName + " is taken already. Try again: ", false);
			input = makeUserInputUniqueString(values, inputName, length, requireNonWhiteSpace);
		}

		// Third error should be if it's the right length and unique but contains
		// whitespace.
		if (requireNonWhiteSpace && input.replaceAll("\\s+", "").length() != input.length()) {
			Utilities.print("Sorry, but you can't use whitespace characters. Try again: ", false);
			input = makeUserInputUniqueString(values, inputName, length, requireNonWhiteSpace);
		}

		return input;
	}

	// End Region

	// Region "String Manipulation"

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

	// End Region

	// Region "System Modifications"

	public static void print() {
		print("", false);
	}

	public static void print(String s) {
		print(s, false);
	}

	public static void println() {
		print("", true);
	}

	public static void println(String s) {
		print(s, true);
	}

	public static void print(String s, boolean newLine) {
		if (newLine) {
			System.out.println(s);
		} else {
			System.out.print(s);
		}
	}

	// End Region

}