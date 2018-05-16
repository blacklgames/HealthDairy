package com.blacklgames.healthdairy.db;

import android.content.Context;

import com.blacklgames.healthdairy.interfaces.IDBDrugsHandler;
import com.blacklgames.healthdairy.interfaces.IDBReceiptsHandler;
import com.blacklgames.healthdairy.interfaces.IDBUsersHandler;

public class DB
{
    private static volatile DB instance;


    Context mContext;
    IDBUsersHandler mUsers;
    IDBReceiptsHandler mReceipts;
    IDBDrugsHandler mDrugs;

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
        mUsers = new DBUsersHandler(mContext);
        mReceipts = new DBReceiptsHandler(mContext);
        mDrugs = new DBDrugsHandler(mContext);
    }

    public IDBUsersHandler users() {
        return mUsers;
    }

    public IDBReceiptsHandler receipts() {
        return mReceipts;
    }

    public IDBDrugsHandler drugs() {return mDrugs;}

}
