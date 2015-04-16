package net.april1.gerryrunnerapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TimeTrackerOpenHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "timetracker.db";
	private static final String TIME_TABLE_NAME = "timerecords";

	private static final String TIME_COLUMN_ID = "id";
	private static final String TIME_COLUMN_TIME = "time";
	private static final String TIME_COLUMN_NOTE = "notes";

	TimeTrackerOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.d(this.getClass().getName(), ">>TimeTrackerOpenHelper");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(this.getClass().getName(), ">>onCreate");
		db.execSQL("create table " + TIME_TABLE_NAME + "(" + TIME_COLUMN_ID
				+ " integer primary key, " + TIME_COLUMN_TIME + " text, "
				+ TIME_COLUMN_NOTE + " text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS timerecords");
		onCreate(db);
	}

}
