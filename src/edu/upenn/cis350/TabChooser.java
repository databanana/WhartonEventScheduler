package edu.upenn.cis350;

import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;


public class TabChooser extends TabActivity {
	//Called to create a view in which two tabs allow switching between viewing
	//lists of people and lists of events.
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
	    
        tabHost.getTabWidget().getChildAt(0).getLayoutParams().height = tabHeight;
        tabHost.getTabWidget().getChildAt(1).getLayoutParams().height = tabHeight;
        
	    tabHost.setCurrentTab(0);
	}
	
	private static View createTab(Context context, Drawable background, Drawable icon, String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.app_tab, null);
        TextView textView = (TextView) view.findViewById(R.id.tab_text);
        textView.setText(text);
        // TODO: include icon
        view.setBackgroundDrawable(background);
        return view;
    }

}
