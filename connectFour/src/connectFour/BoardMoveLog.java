package connectFour;

import java.util.ArrayList;
import java.util.Date;

public class BoardMoveLog {

	// Each member of this ArrayList is an Object[3].
	// 0 is the date and time, 1 is the player (1 or 2), and
	// 2 is the BoardSquare instance representing row and column.
	// The ArrayList will naturally be in chronological order.
	public ArrayList<Object[]> moves = new ArrayList<Object[]>();
	
	// This ArrayList exists to make checking validity easier.
	public ArrayList<BoardSquare> squares = new ArrayList<BoardSquare>();

	public void addMove(int player, BoardSquare move) {
		// Takes a player and BoardSquare and adds them both to the ArrayList.
		Object[] o = new Object[3];

		o[0] = new Date(); // Current date and time
		o[1] = player;
		o[2] = move;

		moves.add(o);
		squares.add(move);
	}
}