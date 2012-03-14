package edu.upenn.cis350;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ListElementArrayAdapter<T> extends ArrayAdapter<T> {
	Context context;
	int listviewid;
	T[] objects;
	
	public ListElementArrayAdapter(Context context, int resource,
			int textViewResourceId, T[] objects) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
		this.listviewid = resource;
		this.objects = objects;
	}
	
	@Override
	//Code from http://stackoverflow.com/questions/5062803/capturing-click-on-a-custom-listview-in-android
	public View getView (int position, View convertView, ViewGroup parent) {
		View v = convertView;
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(listviewid, null);
		
		TextView tv = (TextView)v.findViewById(R.id.event_description);
		tv.setText(objects[position].toString());
		tv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
			      Toast.makeText(context, "Hello from textview number "+v.getId() +"!", Toast.LENGTH_SHORT).show();
			}
		});
		
		tv.setId(position);
		
		Button b = (Button)v.findViewById(R.id.event_select_button);
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
			      Toast.makeText(context, "Hello from star button number "+v.getId() +"!", Toast.LENGTH_SHORT).show();
			}
		});
		
		b.setId(position);
		v.setLongClickable(true);
		return v;
		
	}




}
