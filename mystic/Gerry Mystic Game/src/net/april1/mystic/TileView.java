package net.april1.mystic;

import android.content.Context;
import android.widget.ImageView;

public class TileView extends ImageView {
	
	private int row;
	private int column;
	private int originalRow;
	private int originalColumn;
	private boolean empty;
	

	public TileView(Context context, int row, int column) {
		super(context);
		this.row = row;
		this.column = column;
		this.originalRow = row;
		this.originalColumn = column;
	}
	
	public boolean isCorrectPosition() {
		return (row==originalRow)&&(column==originalColumn);
	}
	
	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
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
