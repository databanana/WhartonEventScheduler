package edu.upenn.cis350;

import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;


public class TabChooser extends TabActivity {
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    final int tabHeight = 60;
	    Resources res = getResources();
	    
	    Drawable tabBackground = res.getDrawable(R.drawable.events_tab); // The tab background
	    Drawable tabIcon = res.getDrawable(R.drawable.events_tab_image);
	    TabHost tabHost = getTabHost();
	    TabHost.TabSpec spec;
	    Intent intent;
	    
	    intent = new Intent().setClass(this, EventListActivity.class);
	    spec = tabHost.newTabSpec("events")
	    		.setIndicator(createTab(this, tabBackground, tabIcon, "" + "Events"))
	    		.setContent(intent);
	    tabHost.addTab(spec);
	    
	    tabBackground = res.getDrawable(R.drawable.people_tab);
	    tabIcon = res.getDrawable(R.drawable.people_tab_image);
	    
	    intent = new Intent().setClass(this, WhartonAndroidAppPeopleActivity.class);
	    spec = tabHost.newTabSpec("people")
	    		.setIndicator(createTab(this, tabBackground, tabIcon, "" + "People"))
	    		.setContent(intent);
	    tabHost.addTab(spec);
	    
	    tabHost.setCurrentTab(1);
	}
	
	private static View createTab(Context context, Drawable background, Drawable icon, String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.app_tab, null);
        TextView textView = (TextView) view.findViewById(R.id.tab_text);
        textView.setText(text);
        view.setBackgroundDrawable(background);
        return view;
    }

}
