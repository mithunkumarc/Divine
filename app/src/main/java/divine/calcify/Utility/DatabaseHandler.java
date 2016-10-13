package divine.calcify.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import divine.calcify.model.Locations;

/**
 * Created by Calcify3 on 01-06-2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "locationManager";
    private static final String TABLE_CONTACTS = "locations";
    private static final String KEY_ID = "id";
    private static final String KEY_LOC_NAME = "name";
    private static final String KEY_LOC_ID = "location_id";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LOC_NAME + " TEXT,"
                + KEY_LOC_ID + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }


    // code to add the new contact
    public void addLocation(Locations locations) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LOC_NAME, locations.getLocation_name()); // Contact Name
        values.put(KEY_LOC_ID, locations.getLocation_id()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single location
    Locations getLocation(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_LOC_NAME, KEY_LOC_ID }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Locations contact = new Locations(cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }

    // code to get all contacts in a list view
    public List<Locations> getAllLocations() {
        List<Locations> locationList = new ArrayList<Locations>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Locations locations = new Locations();
                //contact.setID(Integer.parseInt(cursor.getString(0)));
                locations.setLocation_name(cursor.getString(1));
                locations.setLocation_id(cursor.getString(2));
                // Adding contact to list
                locationList.add(locations);
            } while (cursor.moveToNext());
        }

        // return contact list
        return locationList;
    }

    // Deleting single contact
    public void deleteContact(Locations locations) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_LOC_ID + " = ?", new String[] { String.valueOf(locations.getLocation_id()) });
        db.close();
    }




}

