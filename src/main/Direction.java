package main;

import java.util.ArrayList;

public class Direction {

	public String direction;
	public String opposite;

	public Direction(String direction, String opposite) {
		this.direction = direction;
		this.opposite = opposite;
	}

	public static ArrayList<Direction> setUpDirectionArray(boolean checkAllDirections) {
		ArrayList<Direction> dir = new ArrayList<Direction>();

		dir.add(new Direction("S", "N"));
		dir.add(new Direction("SE", "NW"));
		dir.add(new Direction("E", "W"));
		dir.add(new Direction("NE", "SW"));

		if (checkAllDirections) {
			dir.add(new Direction("N", "S"));
			dir.add(new Direction("NW", "SE"));
			dir.add(new Direction("W", "E"));
			dir.add(new Direction("SW", "NE"));
		}

		return dir;
	}

}