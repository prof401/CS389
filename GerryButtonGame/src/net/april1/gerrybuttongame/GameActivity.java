package net.april1.gerrybuttongame;

import java.util.Random;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
			int colorId = 0;
			Drawable buttonDrawable = button.getBackground();
			if (buttonDrawable instanceof ColorDrawable) {
				ColorDrawable buttonColor = (ColorDrawable) button
						.getBackground();
				colorId = buttonColor.getColor();
			} else {
				TransitionDrawable td = (TransitionDrawable)buttonDrawable;
				ColorDrawable d = (ColorDrawable)td.getDrawable(1);
				colorId = d.getColor();
			}

			if (colorId == startColor) {
				Log.d(this.getClass().getName(), "##change from start to end");
				// Let's change background's color from blue to red.
				ColorDrawable[] color = { new ColorDrawable(startColor),
						new ColorDrawable(endColor) };
				TransitionDrawable trans = new TransitionDrawable(color);
				// This will work also on old devices. The latest API says you
				// have to use setBackground instead.
				trans.startTransition(1000);
				button.setBackgroundDrawable(trans);
				correctCount++;
			} else {
				if (colorId == endColor) {
					Log.d(this.getClass().getName(),
							"##change from end to start");
					// Let's change background's color from blue to red.
					ColorDrawable[] color = { new ColorDrawable(endColor),
							new ColorDrawable(startColor) };
					TransitionDrawable trans = new TransitionDrawable(color);
					// This will work also on old devices. The latest API says
					// you have to use setBackground instead.
					button.setBackgroundDrawable(trans);
					trans.startTransition(1000);

					// button.setBackgroundColor(startColor);
					correctCount--;
				}
			}
			// Animation a = AnimationUtils.loadAnimation(GameActivity.this,
			// R.animator.cellchange);
			// button.startAnimation(a);
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
	// private static final int[] COLUMNNEIGHBOR = { -1, 0, 1, -1, 1, -1, 0, 1
	// };
	private static final Random RANDOM = new Random();
	private static final int[] ROWNEIGHBOR = { -1, 0, 1, 0 };
	// private static final int[] ROWNEIGHBOR = { -1, -1, -1, 0, 0, 1, 1, 1 };
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
				b.setBackgroundColor(startColor);
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
				GameActivity.this.resetBoard();
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
			resetBoard();
			break;
		default:
			// cell button pressed
			clickCount++;
			Cell cellClicked = (Cell) view.getTag();
			int rowClicked = cellClicked.getRow();
			int columnClicked = cellClicked.getColumn();

			cellClicked.changeColor();

			// Change neighbors
			for (int neighbor = 0; neighbor < ROWNEIGHBOR.length; neighbor++) {
				int neighborRow = rowClicked + ROWNEIGHBOR[neighbor];
				int neighborColumn = columnClicked + COLUMNNEIGHBOR[neighbor];
				if (neighborRow < 0 || neighborColumn < 0
						|| neighborRow >= rows || neighborColumn >= columns)
					continue;
				board[neighborRow][neighborColumn].changeColor();
			}

			updateClickCount();
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

		setupGame();

		resetBoard();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
    protected void onStart() {
    	Log.d(this.getClass().getName(), ">>onStart");
	}

    protected void onRestart() {
    	Log.d(this.getClass().getName(), ">>onRestart");
	}

    protected void onResume() {
    	Log.d(this.getClass().getName(), ">>onResume");
	}

    protected void onPause() {
    	Log.d(this.getClass().getName(), ">>onPause");
	}

    protected void onStop() {
    	Log.d(this.getClass().getName(), ">>onStop");
	}

    protected void onDestroy() {
    	Log.d(this.getClass().getName(), ">>onDestroy");
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

	private void resetBoard() {
		Log.d(this.getClass().getName(), ">>resetBoard");
		clickCount = 0;
		correctCount = 0;
		randomizeColors();

		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				board[row][column].reset();
			}
		}
		updateClickCount();
		Log.d(this.getClass().getName(), "<<resetBoard");
	}

	private void setupGame() {
		Button resetButton = (Button) findViewById(R.id.resetbutton);
		resetButton.setOnClickListener(this);

		buildBoard();

		createWinnerDialog();
	}

	private void updateClickCount() {
		((TextView) findViewById(R.id.clicknumber)).setText(Integer
				.toString(clickCount));
	}

}
