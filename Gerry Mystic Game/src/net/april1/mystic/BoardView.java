package net.april1.mystic;

import java.util.Random;
import java.util.Set;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class BoardView extends RelativeLayout {
	private final Random random = new Random();
	private Set<TileView> gameTiles;
	private int rowCount = 4;
	private int columnCount = 4;
	private int tileSize = 69;
	private TileView emptyTile;
	private Context context;
	private Bitmap image;

	public BoardView(Context context, AttributeSet attrSet) {
		super(context, attrSet);
		this.context = context;
		image = BitmapFactory.decodeResource(getResources(),
				R.drawable.defaultimage);
	}

	public void start() {
		createTiles();
		shuffleTiles();
		placeTiles();
	}

	private void placeTiles() {
		for (TileView view : gameTiles) {
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					tileSize, tileSize);
			params.topMargin = tileSize * view.getRow();
			params.leftMargin = tileSize * view.getColumn();
			addView(view, params);
		}
	}

	private void shuffleTiles() {
		Log.d(this.getClass().getName(), ">>shuffleTiles");
		int exchanges = random.nextInt(100);
		TileView[] tiles = (TileView[]) gameTiles
				.toArray(new TileView[gameTiles.size()]);
		for (int i = 0; i < exchanges + tiles.length; i++) {
			int a = random.nextInt(tiles.length);
			int b = random.nextInt(tiles.length);
			if (a != b)
				exchangeTiles(tiles[a], tiles[b]);
		}
	}

	private void exchangeTiles(TileView tileView1, TileView tileView2) {
		int newRow = tileView2.getRow();
		int newColumn = tileView2.getColumn();

		tileView2.setRow(tileView1.getRow());
		tileView2.setColumn(tileView1.getColumn());

		tileView1.setRow(newRow);
		tileView1.setColumn(newColumn);

	}

	private void createTiles() {
		Log.d(this.getClass().getName(), ">>createTiles");
		int emptyRow = random.nextInt(rowCount);
		int emptyColumn = random.nextInt(columnCount);

		Bitmap[] splitImage = splitImage();

		RelativeLayout layout = (RelativeLayout) findViewById(R.id.mystic_grid);
		gameTiles = new java.util.HashSet<TileView>();
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				TileView newView = new TileView(layout.getContext(), row,
						column);
				newView.setOnClickListener((GameActivity) context);
				if (row == emptyRow && column == emptyColumn) {
					newView.setEmpty(true);
					emptyTile = newView;
				} else {
					newView.setImageBitmap(splitImage[(row * columnCount)
							+ column]);
				}
				gameTiles.add(newView);
			}
		}
	}

	private Bitmap[] splitImage() {
		Log.d(this.getClass().getName(), ">>splitImage");
		Bitmap returnImageArray[] = new Bitmap[rowCount * columnCount];
		int width = columnCount * 100;
		int height = rowCount * 100;
		int buttonCount = rowCount * columnCount;

		Bitmap scaledImage = Bitmap.createScaledBitmap(image, width, height,
				true);
		for (int index = 0; index < buttonCount; index++) {
			returnImageArray[index] = Bitmap.createBitmap(scaledImage, 
					width / columnCount * (index % columnCount), 
					height / rowCount * (index / rowCount) , 
					width / columnCount, height	/ rowCount);
		}

		return returnImageArray;
	}

	public void setRows(int rows) {
		rowCount = rows;
	}

	public void setColumns(int columns) {
		columnCount = columns;
	}

	public boolean moveTile(View view) {
		TileView tileView = (TileView) view;
		int emptyDelta = Math.abs(tileView.getRow() - emptyTile.getRow())
				+ Math.abs(tileView.getColumn() - emptyTile.getColumn());
		if (emptyDelta == 1) {
			RelativeLayout.LayoutParams tileParams = (LayoutParams) tileView
					.getLayoutParams();
			RelativeLayout.LayoutParams emptyParams = (LayoutParams) emptyTile
					.getLayoutParams();
			exchangeTiles(tileView, emptyTile);
			emptyTile.setLayoutParams(tileParams);
			tileView.setLayoutParams(emptyParams);
			return true;
		}
		return false;
	}

	public boolean isWon() {
		boolean won = true;
		for (TileView view : gameTiles) {
			if (!view.isCorrectPosition()) {
				won = false;
				break;
			}
		}

		return won;
	}

}
