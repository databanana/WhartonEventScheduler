package edu.upenn.cis350;

import android.app.*;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.*;
import android.widget.*;
import android.view.*;

public class WhartonAndroidAppPeopleActivity extends Activity {

	private SQLiteDatabase db;
	private ListView lv;
	private CustomCursorAdapter dbadapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Create a SQLite database holding event info
		CustomOpenHelper dbopenhelper = new CustomOpenHelper(this);
		db = dbopenhelper.getWritableDatabase();

		setContentView(R.layout.people);

		this.lv = (ListView) findViewById(R.id.peopleListView);
		// Subclass CursorAdapter to read data from the database into the
		// ListView
		// TODO: Fix this for attendees
		Cursor c = db.query("event", new String[] { "_id", "isfavorite",
				"name", "starttime", "endtime" }, null, null, null, null,
				"starttime asc");
		dbadapter = new CustomCursorAdapter(this, c);
		lv.setAdapter(dbadapter);
	}

	public void messageClicked(int person_id) {
		// Open send message view for this person
		Toast.makeText(getApplicationContext(), "Message button clicked: " + person_id,
				Toast.LENGTH_SHORT).show();
	}

	public void personTextClicked(int person_id) {
		// Open detail view for this person
		Toast.makeText(getApplicationContext(), "Person clicked: " + person_id,
				Toast.LENGTH_SHORT).show();
	}

	public void onDestroy() {
		db.close();
	}

	public void showAllEvents(View v) {
		Cursor c = db.query("event", new String[] { "_id", "isfavorite",
				"name", "starttime", "endtime" }, null, null, null, null,
				"starttime asc");
		dbadapter.changeCursor(c);
	}

	public void showMyEvents(View v) {
		Cursor c = db.query("event", new String[] { "_id", "isfavorite",
				"name", "starttime", "endtime" }, "isfavorite = 1", null, null,
				null, "starttime asc");
		dbadapter.changeCursor(c);
	}
}