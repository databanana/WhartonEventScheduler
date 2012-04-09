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

	//Called to create list elements from a db row
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// Set view tag as event ID
		Integer person_pk = new Integer(cursor.getInt(cursor
				.getColumnIndex("_id")));
		view.setTag(R.id.EVENT_DB_KEY, person_pk);
		
		// TODO: Set person's profile picture (where will we get this from?)

		// Set name view tag and text
		TextView name = (TextView) view.findViewById(R.id.person_name);
		name.setTag(R.id.EVENT_DB_KEY, person_pk);
		name.setText(cursor.getString(cursor.getColumnIndex("name")));

		// Set person's title
		TextView titleView = (TextView) view.findViewById(R.id.person_title);
		String title = cursor
				.getString(cursor.getColumnIndex("title"));
		titleView.setText(title);

		// Set email button tag
		Button btn = (Button) view.findViewById(R.id.people_message_button);
		btn.setTag(R.id.EVENT_DB_KEY, person_pk);


		// Set button touch listener
		final OnTouchListener buttonListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent me) {
				int action = me.getActionMasked();
				if (action == MotionEvent.ACTION_UP) {
					int person_id = ((Integer) v.getTag(R.id.EVENT_DB_KEY))
							.intValue();
					((WhartonAndroidAppPeopleActivity) activity).messageClicked(person_id);
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
				int person_id = ((Integer) v.getTag(R.id.EVENT_DB_KEY))
						.intValue();
				((WhartonAndroidAppPeopleActivity) activity)
						.personTextClicked(person_id);
			}
		};
		name.setOnClickListener(eventListener);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.people_list_item, parent, false);
		bindView(v, context, cursor);
		return v;
	}
}
