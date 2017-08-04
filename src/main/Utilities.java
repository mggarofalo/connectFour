package main;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class Utilities {

	private static Scanner key = new Scanner(System.in);

	// Region "Evaluators"

	// Checks to see whether all values in a given String array are equal
	public static boolean allAreEqual(String[] s) {
		if (s.length > 1) {
			for (int i = 1; i < s.length; i++) {
				if (!s[i].equals(s[0])) {
					return false;
				}
			}
		}

		return true;
	}

	// Checks to see whether all values in a given int array are equal
	public static boolean allAreEqual(int[] i) {
		if (i.length > 1) {
			for (int j = 1; j < i.length; j++) {
				if (i[j] != i[0]) {
					return false;
				}
			}
		}

		return true;
	}

	// Checks to see whether all values in a given ArrayList<BoardSquare> are equal
	public static boolean allAreEqual(BoardController bc, ArrayList<BoardSquare> al) {
		if (al.size() > 1) {
			for (int i = 1; i < al.size(); i++) {
				if (bc.readBoardSquare(al.get(i)) != (bc.readBoardSquare((al.get(0))))) {
					return false;
				}
			}
		}

		return true;
	}

	// Checks to see whether a given string can be parsed to an integer
	public static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Checks to see whether a given string exists in a given array (defaults to
	// case-sensitive)
	public static boolean stringExistsInStringArray(String[] values, String toFind) {
		return stringExistsInStringArray(values, toFind, true);
	}

	// Checks to see whether a given string exists in a given ArrayList<String>
	public static boolean stringExistsInStringArrayList(ArrayList<String> values, String toFind) {
		String[] valuesString = new String[values.size()];

		for (int i = 0; i < values.size(); i++) {
			valuesString[i] = values.get(i);
		}

		return stringExistsInStringArray(valuesString, toFind, true);
	}

	// Checks to see whether a given string exists in a given array (case-sensitive
	// optional)
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

	// Checks to see whether an int is larger than any int in a given array
	public static boolean intBiggerThanIntsInArray(int value, int[] values) {
		for (int i = 0; i < values.length; i++) {
			if (value > values[i]) {
				return true;
			}
		}

		return false;
	}

	// End Region

	// Region "Input Parsers"

	// Sanitizes input to ensure that an integer is smaller than the largest of a
	// set of ints
	public static int makeUserInputAPositiveNumberSmallerThanArrayMax(int[] values) {
		int input = makeUserInputAPositiveNumber();
		int maxPermitted = 0;

		for (int i = 0; i < values.length; i++) {
			if (i > maxPermitted) {
				maxPermitted = i;
			}
		}

		while (intBiggerThanIntsInArray(input, values)) {
			Utilities.print("That number is too large. The maximum permitted is " + maxPermitted + ". Try again: ");
			input = makeUserInputAPositiveNumber();
		}

		return input;
	}

	// Sanitizes input to ensure that the int is positive and non-zero
	public static int makeUserInputAPositiveNumber() {
		String input = key.next();

		while (!isInteger(input) || Integer.parseInt(input) <= 0) {
			Utilities.print("Please provide a positive, non-zero number. Try again: ", false);
			input = key.next();
		}

		return Integer.parseInt(input);
	}

	// Sanitizes input to ensure that a given string does not already exist in a
	// given array (case-sensitive optional)
	public static String makeUserInputStringFromArray(String[] values, String inputName, boolean caseSensitive) {
		String input = key.nextLine();

		while (!stringExistsInStringArray(values, input, caseSensitive)) {
			Utilities.print("That " + inputName + " is already taken. Try again: ", false);
			input = key.nextLine();
		}

		return input;
	}

	// Sanitizes input to ensure that a given string does not exceed an optional
	// length threshold, does not already exist in a given array, and is not
	// entirely whitespace characters
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
		if (requireNonWhiteSpace && input.replaceAll("\\s+", "").length() == 0) {
			Utilities.print("Sorry, but you can't use whitespace characters. Try again: ", false);
			input = makeUserInputUniqueString(values, inputName, length, requireNonWhiteSpace);
		}

		return input;
	}

	// End Region

	// Region "String Manipulation"

	// Pads a string to a given length with a given character (or itself)
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
	// All functions in this region offer slightly more helpful versions of the
	// System.out.print() and System.out.println methods

	public static void print() {
		print("", false);
	}

	public static void print(String s) {
		print(s, false);
	}

	public static void print(int i) {
		print(Integer.toString(i));
	}

	public static void print(boolean b) {
		print((b ? "true" : "false"), false);
	}

	public static void println() {
		print("", true);
	}

	public static void println(String s) {
		print(s, true);
	}

	public static void println(int i) {
		print(Integer.toString(i), true);
	}

	public static void println(boolean b) {
		print((b ? "true" : "false"), true);
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