package net.april1.gerrybuttongame;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.TextView;

public class GameActivity extends Activity implements OnClickListener {
	private int clickCount = 0;
	private int rows = 4;
	private int columns = 4;
	private Cell[][] board;
	private static final int[] ROWNEIGHBOR = { -1, 0, 1, 0 };
	private static final int[] COLUMNNEIBOR = { 0, 1, 0, -1 };
	private int correctCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(this.getClass().getName(), ">>onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		setup();

		reset();
	}

	private void setup() {
		board = new Cell[rows][columns];

		GridLayout grid = (GridLayout) findViewById(R.id.gridlayout);
		grid.setColumnCount(4);
		grid.setOrientation(GridLayout.HORIZONTAL);

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				Button b = new Button(this);
				b.setBackgroundColor(Color.RED);
				LayoutParams params = new LayoutParams();
				params.setMargins(5, 5, 5, 5);
				b.setLayoutParams(params);
				Cell cell = new Cell(b, r, c);
				board[r][c] = cell;
				b.setTag(cell);
				b.setOnClickListener(this);
				grid.addView(b);

			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	private void reset() {
		clickCount = 0;
		correctCount = 0;
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				board[row][column].resetColor();
			}
		}
	}

	@Override
	public void onClick(View view) {
		Log.i(this.getClass().getName(), "click" + view.getTag());
		Cell cellClicked = (Cell) view.getTag();
		int rowClicked = cellClicked.getRow();
		int columnClicked = cellClicked.getColumn();

		cellClicked.changeColor();

		// Change neighbors
		for (int neighbor = 0; neighbor < 4; neighbor++) {
			int neighborRow = rowClicked + ROWNEIGHBOR[neighbor];
			int neighborColumn = columnClicked + COLUMNNEIBOR[neighbor];
			if (neighborRow < 0 || neighborColumn < 0 || neighborRow >= rows
					|| neighborColumn >= columns)
				continue;
			board[neighborRow][neighborColumn].changeColor();
		}

		((TextView) findViewById(R.id.clicknumber)).setText(Integer
				.toString(++clickCount));
		((TextView) findViewById(R.id.correctnumber)).setText(Integer
				.toString(correctCount));

	}

	class Cell {
		public int getRow() {
			return row;
		}

		public int getColumn() {
			return column;
		}

		private int row;
		private int column;
		private Button button;

		public Cell(Button newButton, int newRow, int newColumn) {
			button = newButton;
			row = newRow;
			column = newColumn;

		}

		public String toString() {
			return String.format("%1d:%1d", row, column);
		}

		public void resetColor() {
			button.setBackgroundColor(Color.RED);
		}

		public void changeColor() {
			ColorDrawable buttonColor = (ColorDrawable) button.getBackground();

			int colorId = buttonColor.getColor();

			if (colorId == Color.RED) {
				button.setBackgroundColor(Color.GREEN);
				correctCount++;
			} else {
				if (colorId == Color.GREEN) {
					button.setBackgroundColor(Color.RED);
					correctCount--;
				}
			}
		}
	}

}
