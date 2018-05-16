package com.blacklgames.healthdairy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
    private static final String KEY_DRUG_INPUT_COUNT = "key_drug_input_count";
    private static final String KEY_DRUG_INPUT_PERIOD = "key_drug_input_period";
    private static final String KEY_DRUG_COUNT = "key_drug_count";
    private static final String KEY_DRUG_PACK = "key_drug_pack";
    private static final String KEY_DRUG_DURATION = "key_drug_duration";
    private static final String KEY_DRUG_DURATION_PERIOD = "key_drug_duration_period";
    private static final String KEY_DRUG_INPUT_TYPE = "key_drug_input_type";
    private static final String KEY_DRUG_COAST = "key_drug_coast";
    private static final String KEY_DRUG_COMMENTS = "key_drug_comments";

    enum eDrugKeyPos
    {
        K_DRUG_ID,
        K_DRUG_NAME,
        K_DRUG_INPUT_COUNT ,
        K_DRUG_INPUT_PERIOD,
        K_DRUG_DRUG_COUNT,
        K_DRUG_PACK,
        K_DRUG_DURATION,
        K_DRUG_DURATION_PERIOD,
        K_DRUG_INPUT_TYPE,
        K_DRUG_COAST,
        K_DRUG_COMMENTS,
    }

    public DBDrugsHandler(Context context)
    {
        mContext = context;
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                                                    KEY_DRUG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                    KEY_DRUG_NAME + " TEXT, " +
                                                    KEY_DRUG_INPUT_COUNT + " INTEGER, " +
                                                    KEY_DRUG_INPUT_PERIOD + " INTEGER, " +
                                                    KEY_DRUG_COUNT + " REAL, " +
                                                    KEY_DRUG_PACK + " INTEGER, " +
                                                    KEY_DRUG_DURATION + " INTEGER, " +
                                                    KEY_DRUG_DURATION_PERIOD + " INTEGER, " +
                                                    KEY_DRUG_INPUT_TYPE + " INTEGER, " +
                                                    KEY_DRUG_COAST + " REAL, " +
                                                    KEY_DRUG_COMMENTS + " TEXT)");
        db.close();
    }

    public void addDrug(Drug drug)
    {
        ContentValues values = new ContentValues();
        values.put(KEY_DRUG_ID, drug.get_id());
        values.put(KEY_DRUG_NAME, drug.get_name());
        values.put(KEY_DRUG_COMMENTS, drug.get_comments());
        values.put(KEY_DRUG_DURATION, drug.get_duration());
        values.put(KEY_DRUG_DURATION_PERIOD, drug.get_duration_period());
        values.put(KEY_DRUG_INPUT_COUNT, drug.get_input_count());
        values.put(KEY_DRUG_INPUT_PERIOD, drug.get_input_period());
        values.put(KEY_DRUG_COAST, drug.get_coast());
        values.put(KEY_DRUG_PACK, drug.get_pack());
        values.put(KEY_DRUG_INPUT_TYPE, drug.get_input_type());
        values.put(KEY_DRUG_COUNT, drug.get_drug_count());

        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Drug getDrug(int id)
    {
        String[] kList = new String[]{KEY_DRUG_ID,
                                    KEY_DRUG_NAME,
                                    KEY_DRUG_INPUT_COUNT,
                                    KEY_DRUG_INPUT_PERIOD,
                                    KEY_DRUG_COUNT,
                                    KEY_DRUG_PACK,
                                    KEY_DRUG_DURATION,
                                    KEY_DRUG_DURATION_PERIOD,
                                    KEY_DRUG_INPUT_TYPE,
                                    KEY_DRUG_COAST,
                                    KEY_DRUG_COMMENTS
        };
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
        drug.set_input_count(cursor.getInt(eDrugKeyPos.K_DRUG_INPUT_COUNT.ordinal()));
        drug.set_input_period(cursor.getInt(eDrugKeyPos.K_DRUG_INPUT_PERIOD.ordinal()));
        drug.set_drug_count(cursor.getFloat(eDrugKeyPos.K_DRUG_DRUG_COUNT.ordinal()));
        drug.set_pack(cursor.getInt(eDrugKeyPos.K_DRUG_PACK.ordinal()));
        drug.set_duration(cursor.getInt(eDrugKeyPos.K_DRUG_DURATION.ordinal()));
        drug.set_duration_period(cursor.getInt(eDrugKeyPos.K_DRUG_DURATION_PERIOD.ordinal()));
        drug.set_input_type(cursor.getInt(eDrugKeyPos.K_DRUG_INPUT_TYPE.ordinal()));
        drug.set_coast(cursor.getFloat(eDrugKeyPos.K_DRUG_COAST.ordinal()));
        drug.set_comments(cursor.getString(eDrugKeyPos.K_DRUG_COMMENTS.ordinal()));
        return  drug;
    }

    public List<Drug> getAllDrugs()
    {
        List<Drug> list = new ArrayList<Drug>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d("getAllDrugs ", " cout " + cursor.getCount());

        if(cursor.moveToFirst())
        {
            do {
                Drug drug = new Drug();
                drug.set_id(cursor.getInt(eDrugKeyPos.K_DRUG_ID.ordinal()));
                drug.set_name(cursor.getString(eDrugKeyPos.K_DRUG_NAME.ordinal()));
                drug.set_input_count(cursor.getInt(eDrugKeyPos.K_DRUG_INPUT_COUNT.ordinal()));
                drug.set_input_period(cursor.getInt(eDrugKeyPos.K_DRUG_INPUT_PERIOD.ordinal()));
                drug.set_drug_count(cursor.getFloat(eDrugKeyPos.K_DRUG_DRUG_COUNT.ordinal()));
                drug.set_pack(cursor.getInt(eDrugKeyPos.K_DRUG_PACK.ordinal()));
                drug.set_duration(cursor.getInt(eDrugKeyPos.K_DRUG_DURATION.ordinal()));
                drug.set_duration_period(cursor.getInt(eDrugKeyPos.K_DRUG_DURATION_PERIOD.ordinal()));
                drug.set_input_type(cursor.getInt(eDrugKeyPos.K_DRUG_INPUT_TYPE.ordinal()));
                drug.set_coast(cursor.getFloat(eDrugKeyPos.K_DRUG_COAST.ordinal()));
                drug.set_comments(cursor.getString(eDrugKeyPos.K_DRUG_COMMENTS.ordinal()));
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
                    drug.set_input_count(cursor.getInt(eDrugKeyPos.K_DRUG_INPUT_COUNT.ordinal()));
                    drug.set_input_period(cursor.getInt(eDrugKeyPos.K_DRUG_INPUT_PERIOD.ordinal()));
                    drug.set_drug_count(cursor.getFloat(eDrugKeyPos.K_DRUG_DRUG_COUNT.ordinal()));
                    drug.set_pack(cursor.getInt(eDrugKeyPos.K_DRUG_PACK.ordinal()));
                    drug.set_duration(cursor.getInt(eDrugKeyPos.K_DRUG_DURATION.ordinal()));
                    drug.set_duration_period(cursor.getInt(eDrugKeyPos.K_DRUG_DURATION_PERIOD.ordinal()));
                    drug.set_input_type(cursor.getInt(eDrugKeyPos.K_DRUG_INPUT_TYPE.ordinal()));
                    drug.set_coast(cursor.getFloat(eDrugKeyPos.K_DRUG_COAST.ordinal()));
                    drug.set_comments(cursor.getString(eDrugKeyPos.K_DRUG_COMMENTS.ordinal()));
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
        values.put(KEY_DRUG_DURATION, drug.get_duration());
        values.put(KEY_DRUG_DURATION_PERIOD, drug.get_duration_period());
        values.put(KEY_DRUG_INPUT_COUNT, drug.get_input_count());
        values.put(KEY_DRUG_INPUT_PERIOD, drug.get_input_period());
        values.put(KEY_DRUG_COAST, drug.get_coast());
        values.put(KEY_DRUG_PACK, drug.get_pack());
        values.put(KEY_DRUG_INPUT_TYPE, drug.get_input_type());
        values.put(KEY_DRUG_COUNT, drug.get_drug_count());

        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        return db.update(TABLE_NAME, values, KEY_DRUG_ID + " =?",
                         new String[]{String.valueOf(drug.get_id())});
    }

    public void deleteDrugs(String ids)
    {
        SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, 0, null);
        for (int i = 0, n = ids.length(); i < n; i++)
        {
            db.delete(TABLE_NAME, KEY_DRUG_ID + " =?", new String[]{Character.toString(ids.charAt(i))});
        }
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
