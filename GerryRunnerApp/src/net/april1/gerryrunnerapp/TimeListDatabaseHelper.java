package net.april1.gerryrunnerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TimeListDatabaseHelper {
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "timetracker.db";
	private static final String TABLE_NAME = "timerecords";

	private static final String TIME_COLUMN_ID = "id";
	private static final String TIMETRACKER_COLUMN_TIME = "time";
	private static final String TIMETRACKER_COLUMN_NOTES = "notes";

	private TimeTrackerOpenHelper openHelper;
	private SQLiteDatabase database;

	public TimeListDatabaseHelper(Context context) {
		openHelper = new TimeTrackerOpenHelper(context);
		database = openHelper.getWritableDatabase();
	}

	public void saveTimeRecord(String time, String notes) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(TIMETRACKER_COLUMN_TIME, time);
		contentValues.put(TIMETRACKER_COLUMN_NOTES, notes);
		database.insert(TABLE_NAME, null, contentValues);
	}

	public Cursor getAllTimeRecords() {
		 return database.rawQuery(
		 "select * from " + TABLE_NAME,
		 null
		 );
		}
	
	private class TimeTrackerOpenHelper extends SQLiteOpenHelper {

		TimeTrackerOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d(this.getClass().getName(), ">>TimeTrackerOpenHelper");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(this.getClass().getName(), ">>onCreate");
			db.execSQL("create table " + TABLE_NAME + "(" + TIME_COLUMN_ID
					+ " integer primary key, " + TIMETRACKER_COLUMN_TIME
					+ " text, " + TIMETRACKER_COLUMN_NOTES + " text)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS timerecords");
			onCreate(db);
		}

	}

}
