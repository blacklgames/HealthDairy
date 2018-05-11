package com.blacklgames.healthdairy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blacklgames.healthdairy.db.dataobjects.User;
import com.blacklgames.healthdairy.interfaces.IDBUsersHandler;

import java.util.ArrayList;
import java.util.List;

public class DBUsersHandler implements IDBUsersHandler
{
    Context mContext;

    private static final String TABLE_NAME = "table_users";

    private static final String KEY_USER_ID = "key_user_id";
    private static final String KEY_USER_NAME = "key_user_name";
    private static final String KEY_USER_PASS = "key_user_pass";
    private static final String KEY_USER_RECEIPT_LIST = "key_user_receipt_list";

    enum eUserKeyPos
    {
        K_USER_ID,
        K_USER_NAME,
        K_USER_PASS,
        K_USER_RECEIPT_LIST,
        K_MAX
    }

    public DBUsersHandler(Context context)
    {
        mContext = context;
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                KEY_USER_ID + " INTEGER PRIMARY KEY, " +
                KEY_USER_NAME + " TEXT, " +
                KEY_USER_PASS + " TEXT," +
                KEY_USER_RECEIPT_LIST + " TEXT)");
        db.close();
    }

    public void addUser(User user)
    {
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, user.get_id());
        values.put(KEY_USER_NAME, user.get_name());
        values.put(KEY_USER_PASS, user.get_pass());
        values.put(KEY_USER_RECEIPT_LIST, user.get_receipt_list());

        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public User getUser(int id)
    {
        String[] kList = new String[]{KEY_USER_ID, KEY_USER_NAME, KEY_USER_PASS, KEY_USER_RECEIPT_LIST};
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        Cursor cursor = db.query(TABLE_NAME, kList ,KEY_USER_ID + "=?",
                new String[]{String.valueOf(id)}, null,null, null, null);

        if(null != cursor)
        {
            cursor.moveToFirst();
        }

        User user = new User();
        user.set_name(cursor.getString(eUserKeyPos.K_USER_NAME.ordinal()));
        user.set_name(cursor.getString(eUserKeyPos.K_USER_PASS.ordinal()));
        user.add_receipt_id(cursor.getString(eUserKeyPos.K_USER_RECEIPT_LIST.ordinal()));
        user.set_id(Integer.parseInt(cursor.getString(eUserKeyPos.K_USER_ID.ordinal())));
        db.close();
        return  user;
    }

    public List<User> getAllUsers()
    {
        List<User> list = new ArrayList<User>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst())
        {
            do {
                User user = new User();
                user.set_name(cursor.getString(eUserKeyPos.K_USER_NAME.ordinal()));
                user.set_pass(cursor.getString(eUserKeyPos.K_USER_PASS.ordinal()));
                user.add_receipt_id(cursor.getString(eUserKeyPos.K_USER_RECEIPT_LIST.ordinal()));
                user.set_id(Integer.parseInt(cursor.getString(eUserKeyPos.K_USER_ID.ordinal())));
                list.add(user);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public int updateUser(User user)
    {
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, user.get_id());
        values.put(KEY_USER_NAME, user.get_name());
        values.put(KEY_USER_PASS, user.get_pass());
        values.put(KEY_USER_RECEIPT_LIST, user.get_receipt_list());
        int r = db.update(TABLE_NAME, values, KEY_USER_ID + " =?",
                            new String[]{String.valueOf(user.get_id())});
        db.close();
        return r;
    }

    public void deleteUser(User user)
    {
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        db.delete(TABLE_NAME, KEY_USER_ID + " =?", new String[]{String.valueOf(user.get_id())});
        db.close();
    }

    public void deleteAll()
    {
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public int getUsersCount()
    {
        return getAllUsers().size();
    }
}
