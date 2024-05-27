package com.merp.your.custom.quote.gen.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.merp.your.custom.quote.gen.model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // ======================> DATABASE <====================

    private static final String DATABASE    =   "YourQuote";
    private static final Integer VERSION    =   1;
    private static final String USER_TABLE =   "tbl_user";

    // ======================> FIELD <====================

    private static final String USER_ID         =       "_id";
    private static final String NAME            =       "Name";
    private static final String USERNAME        =       "Username";
    private static final String USER_EMAIL      =       "Email";
    private static final String USER_IMAGE      =       "Image";
    private static final String USER_VERIFIED   =       "isVerified";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + USER_TABLE + "(" +
                USER_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                NAME + " TEXT NOT NULL," +
                USERNAME + " TEXT NOT NULL," +
                USER_EMAIL + " TEXT NOT NULL," +
                USER_IMAGE + " BLOB NOT NULL," +
                USER_VERIFIED + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
    }

    public void insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME, user.getName());
        cv.put(USERNAME, user.getUsername());
        cv.put(USER_EMAIL, user.getEmail());
        cv.put(USER_IMAGE, user.getImage());
        cv.put(USER_VERIFIED, user.getIsVerified());
        db.insert(USER_TABLE, null, cv);
        db.close();
        cv.clear();
    }
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME, user.getName());
        cv.put(USERNAME, user.getUsername());
        cv.put(USER_EMAIL, user.getEmail());
        cv.put(USER_IMAGE, user.getImage());
        cv.put(USER_VERIFIED, user.getIsVerified());
        db.update(USER_TABLE,  cv, USER_ID + " = ? ", new String[]{String.valueOf(user.getId())});
        db.close();
        cv.clear();
    }

    public void deleteUser(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USER_TABLE, USER_ID + " = ? ", new String[]{String.valueOf(id)});
        db.close();
    }

    @SuppressLint("Range")
    public List<User> getAllUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<User> userList = new ArrayList<>();
        Cursor cr = db.rawQuery("SELECT * FROM " + USER_TABLE, null);
        if (cr.moveToFirst()) {
            do {
                User product = new User();
                product.setId(      cr.getInt(cr.getColumnIndex(USER_ID)));
                product.setName(    cr.getString(cr.getColumnIndex(NAME)));
                product.setUsername(cr.getString(cr.getColumnIndex(USERNAME)));
                product.setEmail(   cr.getString(cr.getColumnIndex(USER_EMAIL)));
                product.setImage(   cr.getBlob(cr.getColumnIndex(USER_IMAGE)));
                product.setIsVerified( cr.getInt(cr.getColumnIndex(USER_VERIFIED)));
                userList.add(product);
            } while (cr.moveToNext());
        }
        db.close();
        cr.close();
        return userList;
    }

    @SuppressLint("Range")
    public User getUserById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cr = db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + USER_ID + " = ? ", new String[]{String.valueOf(id)});
        User user = new User();
        if (cr.moveToFirst()) {
            do {
                user.setId(      cr.getInt(cr.getColumnIndex(USER_ID)));
                user.setName(    cr.getString(cr.getColumnIndex(NAME)));
                user.setUsername(cr.getString(cr.getColumnIndex(USERNAME)));
                user.setEmail(   cr.getString(cr.getColumnIndex(USER_EMAIL)));
                user.setImage(   cr.getBlob(cr.getColumnIndex(USER_IMAGE)));
                user.setIsVerified( cr.getInt(cr.getColumnIndex(USER_VERIFIED)));
            } while (cr.moveToNext());
        }
        db.close();
        cr.close();
        return user;
    }
}
