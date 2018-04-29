package com.blacklgames.healthdairy;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blacklgames.healthdairy.db.DB;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setInfo("инициализация...");

        DB.get().setBaseContext(getBaseContext());
        db.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT)");

        Cursor query = db.rawQuery("SELECT * FROM users;", null);
        if(query.getCount() > 0)
        {
            setInfo("Пользователь найден. Переходим на историю...");
            Intent intent = new Intent(this, UserHistoryActivity.class);
            startActivity(intent);
        }
        else
        {
            setInfo("Введите имя пользователя...");
        }
        query.close();
        db.close();
    }

    public void onAddClick(View view)
    {
        TextView tv = (TextView) findViewById(R.id.txtUserName);
        String strName = tv.getText().toString();

        if(strName.length() > 0)
        {
            SQLiteDatabase db = getBaseContext().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, strName);
            db.insert(TABLE_USERS, null, values);
            db.close();

            Intent intent = new Intent(this, UserHistoryActivity.class);
            startActivity(intent);
        }
        else
        {
            setInfo("Введите корректное имя пользователя...");
        }
    }

    public void setInfo(String str)
    {
        TextView tv = (TextView) findViewById(R.id.txtInfo);
        tv.setText(str);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
