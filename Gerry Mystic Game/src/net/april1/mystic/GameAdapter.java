package net.april1.mystic;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
//http://www.stealthcopter.com/blog/2010/09/android-creating-a-custom-adapter-for-gridview-buttonadapter/
//http://stackoverflow.com/questions/20129337/android-gridview-with-custom-baseadapter-get-clicked-view-at-position
//https://thinkandroid.wordpress.com/2010/01/13/custom-baseadapters/
//http://www.piwai.info/android-adapter-good-practices/
//http://androidadapternotifiydatasetchanged.blogspot.com/
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class GameAdapter extends BaseAdapter {

	private Context mContext;
	private MysticGame game = new MysticGame();

	public GameAdapter(Context c) {
		mContext = c;
		game.startGame();
	}

	@Override
	public int getCount() {
		return game.getColumnCount() * game.getRowCount();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d(this.getClass().getName(), ">>getView");

		GridView gridView = (GridView) parent;
		if (gridView.getNumColumns() != game.getColumnCount()) {
			Log.d(this.getClass().getName(),
					"##getView - changing column count in gridview");
			gridView.setNumColumns(game.getColumnCount());
		}

		TextView textView;
		if (convertView == null) {
			textView = new TextView(mContext);
			textView.setLayoutParams(new GridView.LayoutParams(85, 85));
			textView.setPadding(8, 8, 8, 8);
			textView.setTag(Integer.toString(position));
			textView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					boolean test = game.move(Integer.parseInt((String) v
							.getTag()));
					Log.d(this.getClass().getName(),
							"##getView " + Boolean.toString(test));

				}
			});
		} else {
			textView = (TextView) convertView;
		}

		textView.setText(game.getFace(position));
		return textView;
	}

}
