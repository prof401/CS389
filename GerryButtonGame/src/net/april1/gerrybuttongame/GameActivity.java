package net.april1.gerrybuttongame;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
	private int clickCount = 0;
	private int rows = 4;
	private int columns = 4;
	private Cell[][] board;
	private static final int[] ROWNEIGHBOR = { -1, 0, 1, 0 };
	private static final int[] COLUMNNEIGHBOR = { 0, 1, 0, -1 };
	private int correctCount = 0;
	private Dialog winnerDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(this.getClass().getName(), ">>onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		setup();

		reset();
	}

	private void setup() {
		Button resetButton = (Button) findViewById(R.id.resetbutton);
		resetButton.setOnClickListener(this);

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
				params.width=50;
				b.setLayoutParams(params);
				Cell cell = new Cell(b, r, c);
				board[r][c] = cell;
				b.setTag(cell);
				b.setOnClickListener(this);
				grid.addView(b);

			}
		}
		
	    createWinnerDialog();
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
	    winnerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	    winnerDialog.setContentView(R.layout.game_won);
	    winnerDialog.setCanceledOnTouchOutside(true);
	    //for dismissing anywhere you touch
	    View masterView = winnerDialog.findViewById(R.id.game_won_view);
	    masterView.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View view) {
	        	Log.d(this.getClass().getName(), ">>onClick in onWin");
	            winnerDialog.dismiss();
	            //GameActivity.this.reset();
	        	Log.d(this.getClass().getName(), "<<onClick in onWin");
	        }
	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	private void reset() {
    	Log.d(this.getClass().getName(), ">>reset");
		clickCount = 0;
		correctCount = 0;
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				board[row][column].reset();
			}
		}
		updateView();
    	Log.d(this.getClass().getName(), "<<reset");
	}

	@Override
	public void onClick(View view) {

		switch (((Button) view).getId()) {
		case R.id.resetbutton:
			reset();
			break;
		default:
			//cell button pressed
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
		if (correctCount==(rows*columns)) {
			onWin();
		}
	}

	private void onWin() {
		    winnerDialog.show();
	}

	private void updateView() {
		((TextView) findViewById(R.id.clicknumber)).setText(Integer
				.toString(clickCount));
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

		public void reset() {
			button.setBackgroundColor(Color.RED);
			button.setEnabled(true);
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
