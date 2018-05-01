package com.blacklgames.healthdairy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blacklgames.healthdairy.db.DO.User;

import java.util.ArrayList;
import java.util.List;

public class DB
{
    private static volatile DB instance;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "HDDB";

    private static final String TABLE_USERS = "table_users";
    private static final String KEY_USER_ID = "key_user_id";
    private static final String KEY_USER_NAME = "key_user_name";
    private static final String KEY_USER_PASS = "key_user_pass";
    private static final String KEY_USER_CURRENT = "key_user_current";

    enum eUserKeyPos
    {
        K_USER_ID,
        K_USER_NAME,
        K_USER_PASS,
        K_USER_CURRENT,
        K_MAX
    }

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

    public void setBaseContext(Context context)
    {
        mContext = context;
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" +
                                                   KEY_USER_ID + " INTEGER PRIMARY KEY, " +
                                                   KEY_USER_NAME + " TEXT, " +
                                                   KEY_USER_PASS+ " TEXT, " +
                                                   KEY_USER_CURRENT + " INTEGER)");
        db.close();
    }

    public void setCurrentUserId(int id)
    {
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        //db.execSQL("UPDATE " + TABLE_INFO + " SET " + KEY_CURRENT_ID + " = " + id);
        db.close();
    }

    public User getCurrentUser()
    {
        List<User> list = getAllUsers();
        return  list.get(0);
    }

    public void addUser(User user)
    {
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, user.get_id());
        values.put(KEY_USER_NAME, user.get_name());
        values.put(KEY_USER_PASS, user.get_pass());
        values.put(KEY_USER_CURRENT, user.is_current());

        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public User getUser(int id)
    {
        String[] kList = new String[]{KEY_USER_ID, KEY_USER_NAME, KEY_USER_PASS, KEY_USER_CURRENT};
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        Cursor cursor = db.query(TABLE_USERS, kList ,KEY_USER_ID + "=?",
             new String[]{String.valueOf(id)}, null,null, null, null);

        if(null != cursor)
        {
            cursor.moveToFirst();
        }

        User user = new User();
        user.set_name(cursor.getString(eUserKeyPos.K_USER_NAME.ordinal()));
        user.set_name(cursor.getString(eUserKeyPos.K_USER_PASS.ordinal()));
        user.set_id(Integer.parseInt(cursor.getString(eUserKeyPos.K_USER_ID.ordinal())));
        user.set_current(Integer.parseInt(cursor.getString(eUserKeyPos.K_USER_CURRENT.ordinal())) > 0);
        return  user;
    }

    public List<User> getAllUsers()
    {
        List<User> list = new ArrayList<User>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst())
        {
            do {
                User user = new User();
                user.set_name(cursor.getString(eUserKeyPos.K_USER_NAME.ordinal()));
                user.set_name(cursor.getString(eUserKeyPos.K_USER_PASS.ordinal()));
                user.set_id(Integer.parseInt(cursor.getString(eUserKeyPos.K_USER_ID.ordinal())));
                user.set_current(Integer.parseInt(cursor.getString(eUserKeyPos.K_USER_CURRENT.ordinal())) > 0);
                list.add(user);
            }while (cursor.moveToNext());
        }
        return list;
    }

    public int updateUser(User user)
    {
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, user.get_id());
        values.put(KEY_USER_NAME, user.get_name());
        values.put(KEY_USER_PASS, user.get_pass());
        values.put(KEY_USER_CURRENT, user.is_current());

        return db.update(TABLE_USERS, values, KEY_USER_ID + " =?",
                        new String[]{String.valueOf(user.get_id())});
    }

    public void deleteUser(User user)
    {
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        db.delete(TABLE_USERS, KEY_USER_ID + " =?", new String[]{String.valueOf(user.get_id())});
        db.close();
    }

    public void deleteAll()
    {
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        db.delete(TABLE_USERS, null, null);
        db.close();
    }

    public int getUsersCount()
    {
        return getAllUsers().size();
    }
}
