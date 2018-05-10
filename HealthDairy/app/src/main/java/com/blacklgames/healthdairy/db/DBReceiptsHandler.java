package com.blacklgames.healthdairy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blacklgames.healthdairy.db.dataobjects.Receipt;
import com.blacklgames.healthdairy.interfaces.IDBReceiptsHandler;

import java.util.ArrayList;
import java.util.List;

public class DBReceiptsHandler implements IDBReceiptsHandler
{
    Context mContext;

    private static final String TABLE_NAME = "table_receipts";

    private static final String KEY_RECEIPT_ID = "key_receipt_id";
    private static final String KEY_RECEIPT_COAST = "key_receipt_coast";
    private static final String KEY_RECEIPT_RATE = "key_receipt_rate";
    private static final String KEY_RECEIPT_DATE = "key_receipt_date";
    private static final String KEY_RECEIPT_PHOTO = "key_receipt_photo";
    private static final String KEY_RECEIPT_COMMENTS = "key_receipt_comments";
    private static final String KEY_RECEIPT_DIAGNOSE = "key_receipt_diagnos";
    private static final String KEY_RECEIPT_DRUG_LIST = "key_receipt_drug_list";

    enum eReceiptKeyPos
    {
        K_RECEIPT_ID,
        K_RECEIPT_COAST,
        K_RECEIPT_RATE,
        K_RECEIPT_DATE,
        K_RECEIPT_PHOTO,
        K_RECEIPT_COMMENTS,
        K_RECEIPT_DIAGNOSE,
        K_RECEIPT_DRUG_LIST
    }

    public DBReceiptsHandler(Context context)
    {
        mContext = context;
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                KEY_RECEIPT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                KEY_RECEIPT_COAST+ " INTEGER, " +
                KEY_RECEIPT_RATE + " INTEGER, " +
                KEY_RECEIPT_DATE + " TEXT, " +
                KEY_RECEIPT_PHOTO + " INTEGER, " +
                KEY_RECEIPT_COMMENTS + " TEXT, " +
                KEY_RECEIPT_DIAGNOSE + " TEXT, " +
                KEY_RECEIPT_DRUG_LIST + " TEXT)");
        db.close();
    }

    public void addReceipt(Receipt receipt)
    {
        ContentValues values = new ContentValues();
        values.put(KEY_RECEIPT_ID, receipt.get_id());
        values.put(KEY_RECEIPT_COAST, receipt.get_coast());
        values.put(KEY_RECEIPT_RATE, receipt.get_rate());
        values.put(KEY_RECEIPT_DATE, receipt.get_date());
        values.put(KEY_RECEIPT_PHOTO, receipt.get_photo());
        values.put(KEY_RECEIPT_COMMENTS, receipt.get_comments());
        values.put(KEY_RECEIPT_DIAGNOSE, receipt.get_diagnosis());
        values.put(KEY_RECEIPT_DRUG_LIST, receipt.get_drug_list());

        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Receipt getReceipt(int id)
    {
        String[] kList = new String[]{KEY_RECEIPT_ID, KEY_RECEIPT_COAST, KEY_RECEIPT_RATE,
                                      KEY_RECEIPT_DATE, KEY_RECEIPT_PHOTO, KEY_RECEIPT_COMMENTS,
                                      KEY_RECEIPT_DIAGNOSE, KEY_RECEIPT_DRUG_LIST};
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        Cursor cursor = db.query(TABLE_NAME, kList ,KEY_RECEIPT_ID + "=?",
                new String[]{String.valueOf(id)}, null,null, null, null);

        if(null != cursor)
        {
            cursor.moveToFirst();
        }

        Receipt receipt = new Receipt();
        receipt.set_id(cursor.getInt(eReceiptKeyPos.K_RECEIPT_ID.ordinal()));
        receipt.set_coast(cursor.getInt(eReceiptKeyPos.K_RECEIPT_COAST.ordinal()));
        receipt.set_rate(cursor.getInt(eReceiptKeyPos.K_RECEIPT_RATE.ordinal()));
        receipt.set_photo(cursor.getInt(eReceiptKeyPos.K_RECEIPT_PHOTO.ordinal()));
        receipt.set_comments(cursor.getString(eReceiptKeyPos.K_RECEIPT_COMMENTS.ordinal()));
        receipt.set_diagnosis(cursor.getString(eReceiptKeyPos.K_RECEIPT_DIAGNOSE.ordinal()));
        receipt.set_date(cursor.getString(eReceiptKeyPos.K_RECEIPT_DATE.ordinal()));
        receipt.set_drug_list(cursor.getString(eReceiptKeyPos.K_RECEIPT_DRUG_LIST.ordinal()));
        return  receipt;
    }

    public List<Receipt> getAllReceipts()
    {
        List<Receipt> list = new ArrayList<Receipt>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst())
        {
            do {
                Receipt receipt = new Receipt();
                receipt.set_id(cursor.getInt(eReceiptKeyPos.K_RECEIPT_ID.ordinal()));
                receipt.set_coast(cursor.getInt(eReceiptKeyPos.K_RECEIPT_COAST.ordinal()));
                receipt.set_rate(cursor.getInt(eReceiptKeyPos.K_RECEIPT_RATE.ordinal()));
                receipt.set_photo(cursor.getInt(eReceiptKeyPos.K_RECEIPT_PHOTO.ordinal()));
                receipt.set_comments(cursor.getString(eReceiptKeyPos.K_RECEIPT_COMMENTS.ordinal()));
                receipt.set_diagnosis(cursor.getString(eReceiptKeyPos.K_RECEIPT_DIAGNOSE.ordinal()));
                receipt.set_drug_list(cursor.getString(eReceiptKeyPos.K_RECEIPT_DRUG_LIST.ordinal()));
                receipt.set_date(cursor.getString(eReceiptKeyPos.K_RECEIPT_DATE.ordinal()));
                list.add(receipt);
            }
            while (cursor.moveToNext());
        }
        return list;
    }

    public int updateReceipt(Receipt receipt)
    {
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);

        ContentValues values = new ContentValues();
        values.put(KEY_RECEIPT_ID, receipt.get_id());
        values.put(KEY_RECEIPT_COAST, receipt.get_coast());
        values.put(KEY_RECEIPT_RATE, receipt.get_rate());
        values.put(KEY_RECEIPT_DATE, receipt.get_date());
        values.put(KEY_RECEIPT_PHOTO, receipt.get_photo());
        values.put(KEY_RECEIPT_COMMENTS, receipt.get_comments());
        values.put(KEY_RECEIPT_DIAGNOSE, receipt.get_diagnosis());
        values.put(KEY_RECEIPT_DRUG_LIST, receipt.get_drug_list());

        return db.update(TABLE_NAME, values, KEY_RECEIPT_ID + " =?",
                new String[]{String.valueOf(receipt.get_id())});
    }

    public void deleteReceipt(Receipt receipt)
    {
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        db.delete(TABLE_NAME, KEY_RECEIPT_ID + " =?", new String[]{String.valueOf(receipt.get_id())});
        db.close();
    }

    public void deleteAll()
    {
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public int getReceiptsCount()
    {
        return getAllReceipts().size();
    }
}
