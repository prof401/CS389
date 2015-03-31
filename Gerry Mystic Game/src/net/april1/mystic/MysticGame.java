package net.april1.mystic;

import java.util.Random;

public class MysticGame {
	private int rowCount = 4;
	private int columnCount = 4;
	private Cell[][] board;

	public void startGame() {
		final Random random = new Random();
		board = new Cell[rowCount][columnCount];
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				Cell newCell = new Cell(row, column, (row * columnCount)
						+ column);
				board[row][column] = newCell;
			}
		}

		// make one random cell blank
		int blankRow = random.nextInt(rowCount);
		int blankColumn = random.nextInt(columnCount);
		board[blankRow][blankColumn].setFace(null);

		// Shuffle
		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < columnCount; c++) {
				exchangeTiles(r, c, random.nextInt(rowCount),
						random.nextInt(columnCount));
			}
		}
	}

	public boolean isGameWon() {
		boolean gameWon = true;
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				gameWon &= board[row][column].isInFinalPosition(row, column);
			}
		}
		return gameWon;
	}

	public boolean move(int row, int column) {
		// check if blank was selected to move
		// return false as do not know what cell to exchange with
		if (board[row][column].isBlank())
			return false;

		final int rowDelta[] = { -1, 0, 0, 1 };
		final int columnDelta[] = { 0, -1, 1, 0 };

		for (int deltaIndex = 0; deltaIndex < rowDelta.length; deltaIndex++) {
			int newRow = row + rowDelta[deltaIndex];
			int newColumn = column + columnDelta[deltaIndex];
			// is newRow and newColumn on the board
			if (newRow > -1 && newRow < rowCount && newColumn > -1
					&& newColumn < columnCount) {
				if (board[newRow][newColumn].isBlank()) {
					exchangeTiles(row, column, newRow, newColumn);
					return true;
				}
			}
		}
		return false;
	}

	private void exchangeTiles(int r1, int c1, int r2, int c2) {
		Cell temp = board[r1][c1];
		board[r1][c1] = board[r2][c2];
		board[r2][c2] = temp;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	private class Cell {
		private int startRow; // row of final position
		private int startColumn; // col of final position
		private String face;

		public Cell(int startRow, int startColumn, int face) {
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

}
