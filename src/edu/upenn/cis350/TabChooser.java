package edu.upenn.cis350;

import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.content.res.Resources;


public class TabChooser extends TabActivity {
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    Resources res = getResources();
	    TabHost tabHost = getTabHost();
	    TabHost.TabSpec spec;
	    Intent intent;
	    
	    intent = new Intent().setClass(this, EventListActivity.class);
	    spec = tabHost.newTabSpec("events")
	    		.setIndicator("Events", res.getDrawable(R.drawable.event_tab_image))
	    		.setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, WhartonAndroidAppPeopleActivity.class);
	    spec = tabHost.newTabSpec("people")
	    		.setIndicator("People", res.getDrawable(R.drawable.people_tab_image))
	    		.setContent(intent);
	    tabHost.addTab(spec);
	    
	    tabHost.setCurrentTab(1);
	}

}
