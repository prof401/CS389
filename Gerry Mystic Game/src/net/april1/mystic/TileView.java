package net.april1.mystic;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

public class TileView extends TextView {

	private int row;
	private int column;
	private String face;

	public TileView(Context context, int row, int column) {
		super(context);
		this.setRow(row);
		this.setColumn(column);
		this.setGravity(Gravity.CENTER);
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getFace() {
		return face;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

}
