package edu.upenn.cis350;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.*;

public class WhartonAndroidAppActivity extends Activity {
    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.main);
	  
		ListView lv = (ListView)findViewById(R.id.eventListView);
		lv.setAdapter(new ListElementArrayAdapter<String>(this, R.layout.list_item, R.id.event_description, COUNTRIES));

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
				// When clicked, show a toast with the TextView text
				Toast.makeText(getApplicationContext(), "from list"+id, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void onEventButtonClicked(View v) {
	    // Do something when the button is clicked
	    Toast.makeText(getApplicationContext(), "Button clicked", Toast.LENGTH_SHORT).show();
	}
	
	  static final String[] COUNTRIES = new String[] {
		    "9-10 Event A", "10-11 Event B", "10:30-12 Event C"};
}