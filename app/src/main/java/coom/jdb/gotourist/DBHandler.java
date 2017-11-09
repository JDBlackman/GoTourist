package coom.jdb.gotourist;

/**
 * Created by Denn1s on 22/05/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static android.provider.Contacts.SettingsColumns.KEY;

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "GoTourist";
    // Contacts table name
    private static final String TABLE_BOOKMARKS = "bookmarks";
    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_PLACE_ID = "place_id";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOKMARKS_TABLE = "CREATE TABLE " + TABLE_BOOKMARKS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PLACE_ID + " TEXT)";
        db.execSQL(CREATE_BOOKMARKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKMARKS);
        // Creating tables again
        onCreate(db);
    }


    public void addBookmark(Bookmark bookmark){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_ID, bookmark.getPlaceId());
        db.insert(TABLE_BOOKMARKS, null, values);
        db.close();
    }

    public void deleteBookmark(Bookmark bookmark){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOKMARKS, KEY_PLACE_ID + " = ?",
                new String[] { bookmark.getPlaceId() });
        db.close();
    }

    public ArrayList<Bookmark> getAllBookmarks(){
        ArrayList<Bookmark> bookmarks = new ArrayList<Bookmark>();
        String selectQuery = "SELECT * FROM " + TABLE_BOOKMARKS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Bookmark bookmark = new Bookmark(cursor.getString(1));
                bookmarks.add(bookmark);
            } while (cursor.moveToNext());
        }
        return bookmarks;
    }

}