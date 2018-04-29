package com.blacklgames.healthdairy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DB implements IDatabaseHandler
{

    private static volatile DB instance;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "HDDB";
    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    Context mContext;


    public static DB get() {
        DB localInstance = instance;
        if (localInstance == null) {
            synchronized (DB.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DB();
                }
            }
        }
        return localInstance;
    }

    private DB()
    {

    }

    public void setBaseContext(Context context)
    {
        mContext = context;
    }


    public void upgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        //onCreate(db);
    }

    @Override
    public void addUser(User user)
    {
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, user.getName());

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    @Override
    public User getUser(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID, KEY_NAME},KEY_ID + "=?",
             new String[]{String.valueOf(id)}, null,null, null, null);

        if(null != cursor)
        {
            cursor.moveToFirst();
        }

        User user = new User(Integer.parseInt(cursor.getString(0)),
                            cursor.getString(1));
        return  user;
    }

    @Override
    public List<User> getAllUsers()
    {
        List<User> list = new ArrayList<User>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst())
        {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
            }while (cursor.moveToNext());
        }
        return list;
    }

    @Override
    public int updateUser(User user)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        return db.update(TABLE_USERS, values, KEY_ID + " =?",
                        new String[]{String.valueOf(user.getId())});
    }

    @Override
    public void deleteUser(User user)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID + " =?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    @Override
    public void deleteAll()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USERS, null, null);
        db.close();
    }

    @Override
    public int getUsersCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_USERS;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
}
