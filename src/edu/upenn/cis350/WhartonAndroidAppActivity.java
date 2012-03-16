package edu.upenn.cis350;

import android.app.*;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.*;
import android.widget.*;
import android.util.Log;
import android.view.*;

public class WhartonAndroidAppActivity extends Activity {

	private SQLiteDatabase db;
	private ListView lv;
	private CustomCursorAdapter dbadapter;
	private boolean favoriteview = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Create a SQLite database holding event info
		CustomOpenHelper dbopenhelper = new CustomOpenHelper(this);
		db = dbopenhelper.getWritableDatabase();
		
		setContentView(R.layout.main);

		this.lv = (ListView) findViewById(R.id.eventListView);
		// Subclass CursorAdapter to read data from the database into the
		// ListView
		Cursor c = db.query("event", new String[] { "_id", "isfavorite",
				"name", "starttime", "endtime" }, null, null, null, null,
				"starttime asc");
		dbadapter = new CustomCursorAdapter(this, c, false);
		lv.setAdapter(dbadapter);
	}

	public void eventButtonClicked(int event_id, boolean isfavorite) {
		int dbvalue;
		if (isfavorite)
			dbvalue = 1;
		else
			dbvalue = 0;
		ContentValues cv = new ContentValues();
		cv.put("isfavorite", dbvalue);
		// Update database contents
		db.update("event", cv, "_id=" + event_id, null);
		if (favoriteview && dbvalue == 0) {
			Cursor c = db.query("event", new String[] { "_id", "isfavorite",
					"name", "starttime", "endtime" }, "isfavorite = 1", null,
					null, null, "starttime asc");
			dbadapter.changeCursor(c);
		}
		// Do something else when the button is clicked?
		// Toast.makeText(getApplicationContext(), "Button clicked: " + event_id
		// + " Pressed: " + isfavorite, Toast.LENGTH_SHORT).show();
	}
	
	public void refresh(View v) { Log.d("refresh", "Called refresh"); } 

	public void eventTextClicked(int event_id) {
		// Do something when the event is clicked
		Toast.makeText(getApplicationContext(), "Event clicked: " + event_id,
				Toast.LENGTH_SHORT).show();
	}

	public void onDestroy() {
		db.close();
	}

	public void showAllEvents() {
		Log.d("click", "Showing all events");
		favoriteview = false;
		Cursor c = db.query("event", new String[] { "_id", "isfavorite",
				"name", "starttime", "endtime" }, null, null, null, null,
				"starttime asc");
		dbadapter.changeCursor(c);
	}

	public void showMyEvents() {
		Log.d("click", "Showing my events");
		favoriteview = true;
		Cursor c = db.query("event", new String[] { "_id", "isfavorite",
				"name", "starttime", "endtime" }, "isfavorite = 1", null, null,
				null, "starttime asc");
		dbadapter.changeCursor(c);
	}
	
	public void toggleMyEvents(View v) {
		favoriteview = !favoriteview;
		if (favoriteview) showMyEvents();
		else showAllEvents();
	}
}