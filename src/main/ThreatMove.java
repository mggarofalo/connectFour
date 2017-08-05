package main;

import java.util.ArrayList;

public class ThreatMove {

	public ArrayList<BoardSquare> threatSquares;
	public BoardSquare runExtendingSquare;
	public Player runOwner;
	public int severity;

	public ThreatMove(ArrayList<BoardSquare> threatSquares, BoardSquare playableSquare, Player runOwner, int severity) {
		this.threatSquares = threatSquares;
		this.runOwner = runOwner;
		this.severity = severity;
	}
}