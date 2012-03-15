package edu.upenn.cis350;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CustomCursorAdapter extends CursorAdapter {

	public CustomCursorAdapter(Context context, Cursor c) {
		super(context, c);
	}

	public CustomCursorAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView name = (TextView) view.findViewById(R.id.event_name);
		TextView time = (TextView) view.findViewById(R.id.event_time);
		Button btn = (Button) view.findViewById(R.id.event_select_button);
		int stored_favorite = cursor.getInt(cursor.getColumnIndex("isfavorite"));
		btn.setPressed(stored_favorite != 0);
		name.setText(cursor.getString(cursor.getColumnIndex("name")));
		String start_time = cursor.getString(cursor.getColumnIndex("starttime"));
		String end_time = cursor.getString(cursor.getColumnIndex("endtime"));
		//TO-DO: parse times, set time in view
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.list_item, parent, false);
		bindView(v, context, cursor);
		return v;
	}

}
