package main;

import java.util.ArrayList;

// DB integration notes

// We'll need a writer method.

// If we import java.security.MessageDigest, we can generate a hash and save it to the
// Game object as well as to a DB with the move log. Then we need a reader method to
// take an identifier and read its moves from the DB. Then we'll just need a resumeGame
// method in the Game class to rebuild the board according to the move log.

public class BoardMoveLog {
	private ArrayList<BoardMove> moves = new ArrayList<BoardMove>();

	// Takes a player and BoardSquare and adds them to the ArrayList.
	public void addMove(BoardMove move) {
		moves.add(move);
	}

	// Gets an ArrayList of all the moves
	public ArrayList<BoardMove> getMoves() {
		return moves;
	}

	// Gets the most recent move
	public BoardMove getLastMove() {
		return moves.get(moves.size() - 1);
	}

	// Gets the move n moves prior to the most recent move
	public BoardMove getLastMove(int n) {
		return moves.get(moves.size() - 1 - n);
	}

	// Gets an ArrayList of all the AI moves after the last human move
	public ArrayList<BoardMove> getLastAIMoves() {
		if (moves.size() == 0 || getLastMove().getPlayer().isHuman) {
			// Return an empty ArrayList
			return new ArrayList<BoardMove>();
		} else {
			ArrayList<BoardMove> movesByAI = new ArrayList<BoardMove>();

			// Get the index of the first AI move after the last human move
			for (int i = (moves.size() - 1); i >= 0; i--) {
				if (moves.get(i).getPlayer().isHuman) {
					// Add all the subsequent moves to the ArrayList
					for (int j = (i + 1); j < moves.size(); j++) {
						movesByAI.add(moves.get(j));
					}

					break;
				}
			}

			// Return the list of AI moves
			return movesByAI;
		}
	}
}