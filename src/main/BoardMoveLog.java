package main;

import java.util.ArrayList;
import java.util.Date;

// DB integration notes

// We'll need a writer method.

// If we import java.security.MessageDigest, we can generate a hash and save it to the
// Game object as well as to a DB with the move log. Then we need a reader method to
// take an identifier and read its moves from the DB. Then we'll just need a resumeGame
// method in the Game class to rebuild the board according to the move log.

public class BoardMoveLog {
	// Each member of this ArrayList is an Object[3].
	// 0 is the date and time, 1 is the player (1 or 2), and
	// 2 is the BoardSquare instance representing row and column.
	// The ArrayList will naturally be in chronological order.
	private ArrayList<BoardMove> moves = new ArrayList<BoardMove>();

	public void addMove(Player player, BoardSquare move) {
		// Takes a player and BoardSquare and adds them to the ArrayList.
		moves.add(new BoardMove(new Date(), player, move));
	}

	public ArrayList<BoardMove> readMoves() {
		return moves;
	}
}