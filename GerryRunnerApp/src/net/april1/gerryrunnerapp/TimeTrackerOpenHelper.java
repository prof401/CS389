package net.april1.gerryrunnerapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TimeTrackerOpenHelper extends SQLiteOpenHelper {

	TimeTrackerOpenHelper(Context context) {
		super(context, "timetracker.db", null, 1);
		Log.d(this.getClass().getName(), ">>TimeTrackerOpenHelper");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
