package main;

import java.util.Date;

public class BoardMove {

	private final Date date;
	private final int player;
	private final BoardSquare move;

	public BoardMove(Date aDate, int aPlayer, BoardSquare aMove) {
		date = aDate;
		player = aPlayer;
		move = aMove;
	}

	public Date getDate(BoardMove move) {
		return move.date;
	}

	public int getPlayer(BoardMove move) {
		return move.player;
	}

	public BoardSquare getBoardSquare(BoardMove move) {
		return move.move;
	}
}