package edu.upenn.cis350;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CustomOpenHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "eventplanner";
    private static final String CREATE_EVENTS_TABLE =
    		"CREATE TABLE event (_id INTEGER PRIMARY KEY, name VARCHAR(255), starttime TEXT, endtime TEXT, location VARCHAR(255), isfavorite INTEGER, moderator INTEGER, FOREIGN KEY(moderator) REFERENCES person(_id))";
	private static final String CREATE_PERSON_TABLE = 
    		"CREATE TABLE person (_id INTEGER PRIMARY KEY, name VARCHAR(255), email VARCHAR(255), title TEXT, profile_picture VARCHAR(255))";
    private static final String CREATE_SPEAKERS_TABLE =
    		"CREATE TABLE speakers (_id INTEGER PRIMARY KEY, event_id INTEGER, speaker_id INTEGER, FOREIGN KEY(event_id) REFERENCES event(_id), FOREIGN KEY(speaker_id) REFERENCES person(_id))";
    
    private static final String[] INITIAL_DATA = {
			"insert into event values(NULL, 'Milestone pre-meeting', '2012-03-16 14:00', '2012-03-16 15:00', 'Somewhere', 0, 0);",
    		"insert into event values(NULL, 'Milestone meeting', '2012-03-16 15:00', '2012-03-16 16:00', 'Vance Hall', 0, 0);",
    		"insert into person  values(NULL, 'Robert Mead', 'robmead@seas.upenn.edu', 'Dude', NULL);", };
    		//"insert into speakers values(0, 0, 0);" };

 
    CustomOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PERSON_TABLE);
        db.execSQL(CREATE_EVENTS_TABLE);
        db.execSQL(CREATE_SPEAKERS_TABLE);
        for (String init:INITIAL_DATA) db.execSQL(init);
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
