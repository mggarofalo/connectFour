package main;

import java.util.Date;

public class BoardMove {

	private final Date date;
	private final Player player;
	private final BoardSquare square;

	public BoardMove(Date date, Player player, BoardSquare square) {
		this.date = date;
		this.player = player;
		this.square = square;
	}

	public BoardMove(BoardMove boardMove) {
		this.date = boardMove.date;
		this.player = boardMove.player;
		this.square = boardMove.square;
	}

	public Date getDate() {
		return this.date;
	}

	public Player getPlayer() {
		return this.player;
	}

	public BoardSquare getBoardSquare() {
		return this.square;
	}

	// Prints the details of the move
	public void printMoveDetails() {
		Utilities.println("Details for move:");
		Utilities.println("Date: " + date);
		Utilities.println("Player: " + player.name + "; Token: " + player.token + "; Index: " + player.index + "; "
				+ (player.isHuman ? "Human" : "AI"));
		Utilities.println("Move: " + square.coordinates() + " (" + square.coordinatesReadable() + ")");
	}
}