package net.april1.mystic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
//http://developer.android.com/guide/topics/ui/layout/gridview.html
//https://www.caveofprogramming.com/guest-posts/custom-gridview-with-imageview-and-textview-in-android.html
//http://www.stealthcopter.com/blog/2010/09/android-creating-a-custom-adapter-for-gridview-buttonadapter/
//http://stackoverflow.com/questions/20129337/android-gridview-with-custom-baseadapter-get-clicked-view-at-position
//https://thinkandroid.wordpress.com/2010/01/13/custom-baseadapters/
//http://www.piwai.info/android-adapter-good-practices/
//http://androidadapternotifiydatasetchanged.blogspot.com/

public class GameAdapter extends BaseAdapter {

	private Context mContext;
	private MysticGame game = new MysticGame();
	
	public GameAdapter(Context c) {
		mContext = c;
	}
	
	@Override
	public int getCount() {
		return game.getColumnCount()*game.getRowCount();
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
		TextView textView;
		if (convertView == null) {
			textView = new TextView(mContext);
			textView.setLayoutParams(new GridView.LayoutParams(85, 85));
			textView.setPadding(8, 8, 8, 8);
		}
		// TODO Auto-generated method stub
		return textView = (TextView) convertView;
	}

}
