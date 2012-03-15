package edu.upenn.cis350;

import android.app.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.*;
import android.widget.*;
import android.view.*;

public class WhartonAndroidAppActivity extends Activity {
	
	private SQLiteDatabase db;
    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Create a SQLite database holding event info
		CustomOpenHelper dbopenhelper = new CustomOpenHelper(this);
		db = dbopenhelper.getWritableDatabase();

		
		setContentView(R.layout.main);
	  
		ListView lv = (ListView)findViewById(R.id.eventListView);
		//Subclass CursorAdapter to read data from the database into the ListView
		Cursor c = db.query("event", new String[] {"_id", "isfavorite", "name", "starttime", "endtime"}, null, null, null, null, "starttime asc");
		CustomCursorAdapter dbadapter = new CustomCursorAdapter(this, c);
		lv.setAdapter(dbadapter);
		
		//lv.setAdapter(new ListElementArrayAdapter<String>(this, R.layout.list_item, R.id.event_name, COUNTRIES));

//		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			public void onItemClick(AdapterView<?> parent, View view,
//			int position, long id) {
//				// When clicked, show a toast with the TextView text
//				Toast.makeText(getApplicationContext(), "from list"+id, Toast.LENGTH_SHORT).show();
//			}
//		});
	}
	
	public void onEventButtonClicked(View v) {
	    // Do something when the button is clicked
	    Toast.makeText(getApplicationContext(), "Button clicked", Toast.LENGTH_SHORT).show();
	}
	
	public void onDestroy() {
		db.close();
	}
	
	
	  static final String[] COUNTRIES = new String[] {
		    "9-10 Event A", "10-11 Event B", "10:30-12 Event C"};
}