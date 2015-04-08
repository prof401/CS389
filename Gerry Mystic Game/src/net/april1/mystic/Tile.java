package net.april1.mystic;

public class Tile {
	private int startRow; // row of final position
	private int startColumn; // col of final position
	private String face;

	public Tile(int startRow, int startColumn, int face) {
		this.startRow = startRow;
		this.startColumn = startColumn;
		this.face = Integer.toString(face);
	}

	public boolean isInFinalPosition(int row, int column) {
		return row == startRow && column == startColumn;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String newFace) {
		this.face = newFace;
	}

	public boolean isBlank() {
		return (face == null);
	}

}
