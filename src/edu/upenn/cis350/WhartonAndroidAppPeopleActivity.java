package edu.upenn.cis350;

import android.app.*;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.*;
import android.widget.*;
import android.view.*;

public class WhartonAndroidAppPeopleActivity extends Activity {

	private SQLiteDatabase db;
	private ListView lv;
	private PeopleCursorAdapter dbadapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Create a SQLite database holding event info
		DatabaseOpener dbopenhelper = new DatabaseOpener(this);
		db = dbopenhelper.getWritableDatabase();

		setContentView(R.layout.people);

		this.lv = (ListView) findViewById(R.id.peopleListView);
		// Subclass CursorAdapter to read data from the database into the
		//ListView
		Cursor c = db.query("person", new String[] { "_id",
				"name", "title", "profile_picture" }, null, null, null, null,
				"name asc");
		dbadapter = new PeopleCursorAdapter(this, c);
		lv.setAdapter(dbadapter);
	}

	public void messageClicked(int person_id) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		//Cursor emailquery = db.query("person", new String[] {"_id", "email"}, "_id="+person_id, null, null, null, null);
		//String email = emailquery.getString(emailquery.getColumnIndex("email"));
		String email = "bob@email.com";
		i.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
		i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Wharton event");
		this.startActivity(Intent.createChooser(i, "Send mail..."));
		
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
}