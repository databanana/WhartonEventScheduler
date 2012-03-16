package edu.upenn.cis350;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class PeopleCursorAdapter extends CursorAdapter {
	private Activity activity;

	public PeopleCursorAdapter(Activity activity, Cursor c) {
		super(activity, c);
		this.activity = activity;
	}

	public PeopleCursorAdapter(Activity activity, Cursor c, boolean autoRequery) {
		super(activity, c, autoRequery);
		this.activity = activity;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// Set view tag as event ID
		Integer person_pk = new Integer(cursor.getInt(cursor
				.getColumnIndex("_id")));
		view.setTag(R.id.EVENT_DB_KEY, person_pk);

		// Set name view tag and text
		TextView name = (TextView) view.findViewById(R.id.person_name);
		name.setTag(R.id.EVENT_DB_KEY, person_pk);
		name.setText(cursor.getString(cursor.getColumnIndex("name")));

		// TODO: parse time field
		TextView time = (TextView) view.findViewById(R.id.event_time);
		String start_time = cursor
				.getString(cursor.getColumnIndex("starttime"));
		String end_time = cursor.getString(cursor.getColumnIndex("endtime"));

		// Set button tag
		Button btn = (Button) view.findViewById(R.id.event_select_button);
		btn.setTag(R.id.EVENT_DB_KEY, event_pk);

		// Set button state
		int stored_favorite = cursor
				.getInt(cursor.getColumnIndex("isfavorite"));
		btn.setPressed(stored_favorite != 0);

		// Set button touch listener
		final OnTouchListener buttonListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent me) {
				int action = me.getActionMasked();
				if (action == MotionEvent.ACTION_UP) {
					v.setPressed(!v.isPressed());
					int event_id = ((Integer) v.getTag(R.id.EVENT_DB_KEY))
							.intValue();
					((WhartonAndroidAppActivity) activity).eventButtonClicked(
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
				((WhartonAndroidAppActivity) activity)
						.eventTextClicked(event_id);
			}
		};
		name.setOnClickListener(eventListener);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.list_item, parent, false);
		bindView(v, context, cursor);
		return v;
	}
}