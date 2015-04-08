package net.april1.mystic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class TileView extends TextView {
	
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
		if (empty) 
			this.setText("");
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
