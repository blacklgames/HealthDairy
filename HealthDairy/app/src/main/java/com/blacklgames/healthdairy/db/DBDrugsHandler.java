package com.blacklgames.healthdairy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blacklgames.healthdairy.db.dataobjects.Drug;
import com.blacklgames.healthdairy.interfaces.IDBDrugsHandler;

import java.util.ArrayList;
import java.util.List;

public class DBDrugsHandler implements IDBDrugsHandler
{
    Context mContext;

    private static final String TABLE_NAME= "table_drugs";

    private static final String KEY_DRUG_ID = "key_drug_id";
    private static final String KEY_DRUG_NAME = "key_drug_name";
    private static final String KEY_DRUG_COMMENTS = "key_drug_comments";
    private static final String KEY_DRUG_COUNT = "key_drug_count";
    private static final String KEY_DRUG_PERIOD = "key_drug_period";
    private static final String KEY_DRUG_INPUT_COUNT = "key_drug_input_count";
    private static final String KEY_DRUG_INPUT_PERIOD = "key_drug_input_period";
    private static final String KEY_DRUG_COAST = "key_drug_coast";
    private static final String KEY_COUNT_ON_TAKING = "key_count_on_taking";


    enum eDrugKeyPos
    {
        K_DRUG_ID,
        K_DRUG_NAME,
        K_DRUG_COMMENTS,
        K_DRUG_COUNT,
        K_DRUG_PERIOD,
        K_DRUG_INPUT_COUNT ,
        K_DRUG_INPUT_PERIOD,
        K_DRUG_COAST,
        K_DRUG_COUNT_ON_TAKING
    }

    public DBDrugsHandler(Context context)
    {
        mContext = context;
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                                                   KEY_DRUG_ID + " INTEGER PRIMARY KEY, " +
                                                   KEY_DRUG_NAME + " TEXT, " +
                                                   KEY_DRUG_COMMENTS + " TEXT, " +
                                                   KEY_DRUG_COUNT + " INTEGER, " +
                                                   KEY_DRUG_PERIOD + " INTEGER, " +
                                                   KEY_DRUG_INPUT_COUNT + " INTEGER, " +
                                                   KEY_DRUG_INPUT_PERIOD + " INTEGER, " +
                                                   KEY_DRUG_COAST + " REAL, " +
                                                   KEY_COUNT_ON_TAKING + " REAL)");
        db.close();
    }

    public void addDrug(Drug drug)
    {
        ContentValues values = new ContentValues();
        values.put(KEY_DRUG_ID, drug.get_id());
        values.put(KEY_DRUG_NAME, drug.get_name());
        values.put(KEY_DRUG_COMMENTS, drug.get_comments());
        values.put(KEY_DRUG_COUNT, drug.get_count());
        values.put(KEY_DRUG_PERIOD, drug.get_period());
        values.put(KEY_DRUG_INPUT_COUNT, drug.get_input_cont());
        values.put(KEY_DRUG_INPUT_PERIOD, drug.get_input_period());
        values.put(KEY_DRUG_COAST, drug.get_coast());
        values.put(KEY_COUNT_ON_TAKING, drug.get_count_on_taking());

        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Drug getDrug(int id)
    {
        String[] kList = new String[]{KEY_DRUG_ID, KEY_DRUG_NAME, KEY_DRUG_COMMENTS,
                KEY_DRUG_COUNT, KEY_DRUG_PERIOD, KEY_DRUG_INPUT_COUNT,
                KEY_DRUG_INPUT_PERIOD, KEY_DRUG_COAST, KEY_COUNT_ON_TAKING};
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        Cursor cursor = db.query(TABLE_NAME, kList ,KEY_DRUG_ID + "=?",
                new String[]{String.valueOf(id)}, null,null, null, null);

        if(null != cursor)
        {
            cursor.moveToFirst();
        }

        Drug drug = new Drug();
        drug.set_id(cursor.getInt(eDrugKeyPos.K_DRUG_ID.ordinal()));
        drug.set_name(cursor.getString(eDrugKeyPos.K_DRUG_NAME.ordinal()));
        drug.set_comments(cursor.getString(eDrugKeyPos.K_DRUG_COMMENTS.ordinal()));
        drug.set_count(cursor.getInt(eDrugKeyPos.K_DRUG_COUNT.ordinal()));
        drug.set_period(cursor.getInt(eDrugKeyPos.K_DRUG_PERIOD.ordinal()));
        drug.set_input_cont(cursor.getInt(eDrugKeyPos.K_DRUG_INPUT_COUNT.ordinal()));
        drug.set_input_period(cursor.getInt(eDrugKeyPos.K_DRUG_INPUT_PERIOD.ordinal()));
        drug.set_coast(cursor.getFloat(eDrugKeyPos.K_DRUG_COAST.ordinal()));
        drug.set_count_on_taking(cursor.getFloat(eDrugKeyPos.K_DRUG_COUNT_ON_TAKING.ordinal()));
        return  drug;
    }

    public List<Drug> getAllDrugs()
    {
        List<Drug> list = new ArrayList<Drug>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst())
        {
            do {
                Drug drug = new Drug();
                drug.set_id(cursor.getInt(eDrugKeyPos.K_DRUG_ID.ordinal()));
                drug.set_name(cursor.getString(eDrugKeyPos.K_DRUG_NAME.ordinal()));
                drug.set_comments(cursor.getString(eDrugKeyPos.K_DRUG_COMMENTS.ordinal()));
                drug.set_count(cursor.getInt(eDrugKeyPos.K_DRUG_COUNT.ordinal()));
                drug.set_period(cursor.getInt(eDrugKeyPos.K_DRUG_PERIOD.ordinal()));
                drug.set_input_cont(cursor.getInt(eDrugKeyPos.K_DRUG_INPUT_COUNT.ordinal()));
                drug.set_input_period(cursor.getInt(eDrugKeyPos.K_DRUG_INPUT_PERIOD.ordinal()));
                drug.set_coast(cursor.getFloat(eDrugKeyPos.K_DRUG_COAST.ordinal()));
                drug.set_count_on_taking(cursor.getFloat(eDrugKeyPos.K_DRUG_COUNT_ON_TAKING.ordinal()));
                list.add(drug);
            }
            while (cursor.moveToNext());
        }
        return list;
    }

    public List<Drug> getDrugsById(String ids)
    {
        List<Drug> list = new ArrayList<Drug>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst())
        {
            do {
                if(ids.contains(Integer.toString(cursor.getInt(eDrugKeyPos.K_DRUG_ID.ordinal()))))
                {
                    Drug drug = new Drug();
                    drug.set_id(cursor.getInt(eDrugKeyPos.K_DRUG_ID.ordinal()));
                    drug.set_name(cursor.getString(eDrugKeyPos.K_DRUG_NAME.ordinal()));
                    drug.set_comments(cursor.getString(eDrugKeyPos.K_DRUG_COMMENTS.ordinal()));
                    drug.set_count(cursor.getInt(eDrugKeyPos.K_DRUG_COUNT.ordinal()));
                    drug.set_period(cursor.getInt(eDrugKeyPos.K_DRUG_PERIOD.ordinal()));
                    drug.set_input_cont(cursor.getInt(eDrugKeyPos.K_DRUG_INPUT_COUNT.ordinal()));
                    drug.set_input_period(cursor.getInt(eDrugKeyPos.K_DRUG_INPUT_PERIOD.ordinal()));
                    drug.set_coast(cursor.getFloat(eDrugKeyPos.K_DRUG_COAST.ordinal()));
                    drug.set_count_on_taking(cursor.getFloat(eDrugKeyPos.K_DRUG_COUNT_ON_TAKING.ordinal()));
                    list.add(drug);
                }
            }
            while (cursor.moveToNext());
        }
        return list;
    }

    public int updateDrug(Drug drug)
    {
        ContentValues values = new ContentValues();
        values.put(KEY_DRUG_ID, drug.get_id());
        values.put(KEY_DRUG_NAME, drug.get_name());
        values.put(KEY_DRUG_COMMENTS, drug.get_comments());
        values.put(KEY_DRUG_COUNT, drug.get_count());
        values.put(KEY_DRUG_PERIOD, drug.get_period());
        values.put(KEY_DRUG_INPUT_COUNT, drug.get_input_cont());
        values.put(KEY_DRUG_INPUT_PERIOD, drug.get_input_period());
        values.put(KEY_DRUG_COAST, drug.get_coast());
        values.put(KEY_COUNT_ON_TAKING, drug.get_count_on_taking());

        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        return db.update(TABLE_NAME, values, KEY_DRUG_ID + " =?",
                         new String[]{String.valueOf(drug.get_id())});
    }

    public void deleteDrug(Drug drug)
    {
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        db.delete(TABLE_NAME, KEY_DRUG_ID + " =?", new String[]{String.valueOf(drug.get_id())});
        db.close();
    }

    public void deleteAll()
    {
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public int getDrugsCount()
    {
        return getAllDrugs().size();
    }
}
