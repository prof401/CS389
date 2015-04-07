package net.april1.mystic;

import java.util.Random;
import java.util.Set;

import android.content.Context;
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

	public BoardView(Context context, AttributeSet attrSet) {
		super(context, attrSet);
		this.context = context;
	}

	public void start() {
		createTiles();
		// shuffleTiles();
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

	@SuppressWarnings("unused")
	private void shuffleTiles() {
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
		int emptyRow = random.nextInt(rowCount);
		int emptyColumn = random.nextInt(columnCount);

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
					newView.setText(Integer.toString((row * columnCount)
							+ column));
				}
				gameTiles.add(newView);
			}
		}
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
		Log.d(this.getClass().getName(), ">>isWon");
		boolean won = true;
		for (TileView view : gameTiles) {
			Log.d(this.getClass().getName(), "##isWon " + Boolean.toString(won)
					+ " " + view.getRow() + "," + view.getColumn() + "-> "
					+ view.isCorrectPosition());
			won &= view.isCorrectPosition();
		}

		return won;
	}

}
