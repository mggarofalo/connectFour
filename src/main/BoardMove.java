package main;

import java.util.Date;

public class BoardMove {

	private final Date date;
	private final Player player;
	private final BoardSquare move;

	public BoardMove(Date aDate, Player aPlayer, BoardSquare aMove) {
		date = aDate;
		player = aPlayer;
		move = aMove;
	}

	public Date getDate() {
		return this.date;
	}

	public Player getPlayer() {
		return this.player;
	}

	public BoardSquare getBoardSquare() {
		return this.move;
	}
}