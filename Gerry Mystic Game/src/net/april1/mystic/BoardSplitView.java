package net.april1.mystic;

import java.util.Set;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class BoardSplitView extends RelativeLayout implements OnClickListener {

	private Context context;

	private int rows = 4;
	private int columns = 4;
	private int buttonSize = 100;
	private Bitmap image;
	private Set<TileView> tiles;

	public void start() {
		createTiles();
		placeTiles();
	}

	private void createTiles() {
		tiles = new java.util.HashSet<TileView>(rows * columns);
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				TileView newView = new TileView(this.context, row, column);
				newView.setFace(Integer.toString((row * rows) + column));
				tiles.add(newView);
			}
		}
	}

	private void placeTiles() {
		for (TileView tile : tiles) {
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					buttonSize, buttonSize);
			params.topMargin = tile.getRow() * buttonSize;
			params.leftMargin = tile.getColumn() * buttonSize;
			tile.setText(tile.getFace());
			addView(tile, params);
		}

	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public BoardSplitView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub

	}

}
