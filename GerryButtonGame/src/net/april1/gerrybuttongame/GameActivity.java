package net.april1.gerrybuttongame;

import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.TextView;

public class GameActivity extends Activity implements OnClickListener {
	class Cell {
		private Button button;

		private int column;

		private int row;
		public Cell(Button newButton, int newRow, int newColumn) {
			button = newButton;
			row = newRow;
			column = newColumn;

		}
		public void changeColor() {
			ColorDrawable buttonColor = (ColorDrawable) button.getBackground();

			int colorId = buttonColor.getColor();

			if (colorId == startColor) {
				button.setBackgroundColor(endColor);
				correctCount++;
			} else {
				if (colorId == endColor) {
					button.setBackgroundColor(startColor);
					correctCount--;
				}
			}
		}

		public int getColumn() {
			return column;
		}

		public int getRow() {
			return row;
		}

		public void reset() {
			button.setBackgroundColor(startColor);
			button.setEnabled(true);
		}

		public String toString() {
			return String.format("%1d:%1d", row, column);
		}
	}
	private static final int[] COLUMNNEIGHBOR = { 0, 1, 0, -1 };
	private static final Random RANDOM = new Random();
	private static final int[] ROWNEIGHBOR = { -1, 0, 1, 0 };
	private Cell[][] board;
	private int clickCount = 0;
	private int columns = 4;
	private int correctCount = 0;
	private int endColor = Color.GREEN;
	private int rows = 4;
	private int startColor = Color.RED;

	private Dialog winnerDialog;

	private void buildBoard() {
		board = new Cell[rows][columns];

		GridLayout grid = (GridLayout) findViewById(R.id.gridlayout);
		grid.setColumnCount(columns);
		grid.setOrientation(GridLayout.HORIZONTAL);

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				Button b = new Button(this);
				b.setBackgroundColor(Color.RED);
				LayoutParams params = new LayoutParams();
				params.setMargins(5, 0, 0, 5);
				params.width = 50;
				b.setLayoutParams(params);
				Cell cell = new Cell(b, r, c);
				board[r][c] = cell;
				b.setTag(cell);
				b.setOnClickListener(this);
				grid.addView(b);
			}
		}
	}

	private int colorBrightness(int c1) {
		int r1 = (c1 >> 16) & 0xFF;
		int g1 = (c1 >> 8) & 0xFF;
		int b1 = c1 & 0xFF;
		return ((r1 * 299) + (g1 * 587) + (b1 * 114)) / 1000;
	}

	private int colorDifference(int c1, int c2) {
		int r1 = (c1 >> 16) & 0xFF;
		int g1 = (c1 >> 8) & 0xFF;
		int b1 = c1 & 0xFF;
		int r2 = (c2 >> 16) & 0xFF;
		int g2 = (c2 >> 8) & 0xFF;
		int b2 = c2 & 0xFF;

		return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
	}

	private void createWinnerDialog() {
		winnerDialog = new Dialog(this) {
			protected void onStop() {
				Log.d(this.getClass().getName(), ">>onStop in onWin");
				GameActivity.this.reset();
				Log.d(this.getClass().getName(), "<<onStop in onWin");
			}
		};
		winnerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		winnerDialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		winnerDialog.setContentView(R.layout.game_won);
		winnerDialog.setCanceledOnTouchOutside(true);
		// for dismissing anywhere you touch
		View masterView = winnerDialog.findViewById(R.id.game_won_view);
		masterView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d(this.getClass().getName(), ">>onClick in onWin");
				winnerDialog.dismiss();
				// GameActivity.this.reset();
				Log.d(this.getClass().getName(), "<<onClick in onWin");
			}
		});
	}

	@Override
	public void onClick(View view) {

		switch (((Button) view).getId()) {
		case R.id.resetbutton:
			reset();
			break;
		default:
			// cell button pressed
			clickCount++;
			Cell cellClicked = (Cell) view.getTag();
			int rowClicked = cellClicked.getRow();
			int columnClicked = cellClicked.getColumn();

			cellClicked.changeColor();

			// Change neighbors
			for (int neighbor = 0; neighbor < 4; neighbor++) {
				int neighborRow = rowClicked + ROWNEIGHBOR[neighbor];
				int neighborColumn = columnClicked + COLUMNNEIGHBOR[neighbor];
				if (neighborRow < 0 || neighborColumn < 0
						|| neighborRow >= rows || neighborColumn >= columns)
					continue;
				board[neighborRow][neighborColumn].changeColor();
			}

			updateView();
		}
		if (correctCount == (rows * columns)) {
			onWin();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(this.getClass().getName(), ">>onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		setup();

		reset();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	private void onWin() {
		winnerDialog.show();
	}

	private int randomColor() {
		return randomColor(Color.WHITE);
	}

	private int randomColor(int compareColor) {
		boolean invalid = true;
		int newColor = Color.WHITE;
		int compareBrightness = colorBrightness(compareColor);
		do {
			newColor = RANDOM.nextInt() | 0xFF000000;
			int newBrightness = colorBrightness(newColor);
			if (((256 - newBrightness) > 64)
					&& (Math.abs(newBrightness - compareBrightness) > 64)
					&& (colorDifference(compareColor, newColor) > 64))
				invalid = false;

		} while (invalid);

		return newColor;
	}

	private void randomizeColors() {
		Log.d(this.getClass().getName(), ">>randomizeColors");
		startColor = randomColor();
		endColor = randomColor(startColor);
		Log.d(this.getClass().getName(), "<<randomizeColors");
	}

	private void reset() {
		Log.d(this.getClass().getName(), ">>reset");
		clickCount = 0;
		correctCount = 0;
		randomizeColors();

		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				board[row][column].reset();
			}
		}
		updateView();
		Log.d(this.getClass().getName(), "<<reset");
	}

	private void setup() {
		Button resetButton = (Button) findViewById(R.id.resetbutton);
		resetButton.setOnClickListener(this);

		buildBoard();

		createWinnerDialog();
	}

	private void updateView() {
		((TextView) findViewById(R.id.clicknumber)).setText(Integer
				.toString(clickCount));
	}

}
