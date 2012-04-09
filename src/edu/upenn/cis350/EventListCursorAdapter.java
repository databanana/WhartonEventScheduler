package edu.upenn.cis350;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class EventListCursorAdapter extends CursorAdapter {
	private Activity activity;
	private SimpleDateFormat date_input_format;
	private SimpleDateFormat date_output_format;
	private SimpleDateFormat date_output_format_ampm;

	public EventListCursorAdapter(Activity activity, Cursor c) {
		super(activity, c);
		this.activity = activity;
		this.date_input_format = new SimpleDateFormat("yyyy-MM-DD HH:MM");
		this.date_output_format = new SimpleDateFormat("hh:mm");
		this.date_output_format_ampm = new SimpleDateFormat("aa");
	}

	 public EventListCursorAdapter(Activity activity, Cursor c, boolean
	 autoRequery) {
		 super(activity, c, autoRequery);
		 this.activity = activity;
		this.date_input_format = new SimpleDateFormat("yyyy-MM-DD HH:MM");
		this.date_output_format = new SimpleDateFormat("HH:MM");
		this.date_output_format_ampm = new SimpleDateFormat("aa");
	 }

	//Called to create a list element from a database row
	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		Log.d("cca", "Binding view");
		// Set view tag as event ID
		Integer event_pk = new Integer(cursor.getInt(cursor
				.getColumnIndex("_id")));
		view.setTag(R.id.EVENT_DB_KEY, event_pk);

		// Set name view tag and text
		TextView name = (TextView) view.findViewById(R.id.event_name);
		name.setTag(R.id.EVENT_DB_KEY, event_pk);
		name.setText(cursor.getString(cursor.getColumnIndex("name")));
		
		//Set all text tag
		View alltext = view.findViewById(R.id.all_event_text);
		alltext.setTag(R.id.EVENT_DB_KEY, event_pk);

		// TO-DO: parse time field
		//YYY-MM-DD HH:MM
		
		TextView time = (TextView) view.findViewById(R.id.event_time);
		Date start_time;
		Date end_time;
		try {
			start_time = date_input_format.parse(cursor
					.getString(cursor.getColumnIndex("starttime")));
			end_time = date_input_format.parse(cursor.getString(cursor.getColumnIndex("endtime")));
		} catch (java.text.ParseException p) {
			start_time = null;
			end_time = null;
		}
		time.setText(getTimeString(start_time, end_time));

		// Set button tag
		Button btn = (Button) view.findViewById(R.id.event_select_button);
		btn.setTag(R.id.EVENT_DB_KEY, event_pk);

		// Set button state
		int stored_favorite = cursor
				.getInt(cursor.getColumnIndex("isfavorite"));
		btn.setPressed(stored_favorite != 0);
		Log.d("sql", "event id "+event_pk+" was "+(stored_favorite!=0?"":"not")+" favorited");

		// Set button touch listener
		final OnTouchListener buttonListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent me) {
				int action = me.getActionMasked();
				if (action == MotionEvent.ACTION_UP) {
					v.setPressed(!v.isPressed());
					int event_id = ((Integer) v.getTag(R.id.EVENT_DB_KEY))
							.intValue();
					((EventListActivity) activity).eventButtonClicked(
							event_id, v.isPressed());
					return true;
				} else
					return true;
			}
		};

		btn.setOnTouchListener(buttonListener);

		// Set text click listener
		final OnClickListener eventListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				int event_id = ((Integer) v.getTag(R.id.EVENT_DB_KEY))
						.intValue();
				((EventListActivity) activity)
						.eventTextClicked(event_id);
			}
		};

		alltext.setOnClickListener(eventListener);
	}

	//Called to create a list element
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.list_item, parent, false);
		bindView(v, context, cursor);
		return v;
	}
	
	//Return the time ranges of events in a readable format
	public String getTimeString(Date start, Date end) {
		if (start==null || end==null) return "";
		String start_ampm = date_output_format_ampm.format(start);
		String end_ampm = date_output_format_ampm.format(end);
		String starttime = date_output_format.format(start);
		String endtime = date_output_format.format(end);
		if (start_ampm.equals(end_ampm))
			return starttime + "-" + endtime + " " + end_ampm;
		else return starttime + " " + start_ampm + "-" + endtime + " " + end_ampm;
	}

}
