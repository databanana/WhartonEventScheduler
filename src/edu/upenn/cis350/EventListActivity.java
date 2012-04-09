package edu.upenn.cis350;

import android.app.*;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.*;
import android.widget.*;
import android.util.Log;
import android.view.*;

public class EventListActivity extends Activity {

	private SQLiteDatabase db;
	private ListView lv;
	private EventListCursorAdapter dbadapter;
	private boolean favoriteview = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Create a SQLite database holding event info
		DatabaseOpener dbopenhelper = new DatabaseOpener(this);
		db = dbopenhelper.getWritableDatabase();
		
		setContentView(R.layout.event_list);

		this.lv = (ListView) findViewById(R.id.eventListView);
		// Subclass CursorAdapter to read data from the database into the
		// ListView
		Cursor c = db.query("event", new String[] { "_id", "isfavorite",
				"name", "starttime", "endtime" }, null, null, null, null,
				"starttime asc");
		dbadapter = new EventListCursorAdapter(this, c, false);
		lv.setAdapter(dbadapter);
	}

	//Called when the star (for favoriting events) next to an event is pressed.
	public void eventButtonClicked(int event_id, boolean isfavorite) {
		int isfavorite_dbval;
		if (isfavorite)
			isfavorite_dbval = 1;
		else
			isfavorite_dbval = 0;
		ContentValues cv = new ContentValues();
		cv.put("isfavorite", isfavorite_dbval);
		// Update database contents
		db.update("event", cv, "_id=" + event_id, null);
		//If event is un-favorited and only favorites are being displayed,
		//re-create the list to remove the event
		if (favoriteview && isfavorite_dbval == 0) {
			Cursor c = db.query("event", new String[] { "_id", "isfavorite",
					"name", "starttime", "endtime" }, "isfavorite = 1", null,
					null, null, "starttime asc");
			dbadapter.changeCursor(c);
		}
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