package com.blacklgames.healthdairy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.blacklgames.healthdairy.db.dataobjects.User;
import com.blacklgames.healthdairy.db.DB;
import com.blacklgames.healthdairy.user_main_screen.UserMainActivity;

public class MainActivity extends AppCompatActivity
{
    // Used to load the 'native-lib' library on application startup.
    static
    {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB.get().setBaseContext(getBaseContext());

        if(DB.get().users().getUsersCount() > 0)
        {
            Intent intent = new Intent(this, UserMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else {
            setInfo("Введите имя пользователя...");
        }
    }

    public void onAddClick(View view)
    {
        TextView tv = (TextView) findViewById(R.id.txtUserName);
        String strName = tv.getText().toString();

        if(strName.length() > 0)
        {
            User user = new User();
            user.set_id(DB.get().users().getUsersCount());
            user.set_name(strName);
            user.set_pass("");
            DB.get().users().addUser(user);

            Intent intent = new Intent(this, UserMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else
        {
            setInfo("Введите корректное имя пользователя...");
        }
    }

    public void setInfo(String str)
    {
        ((TextView) findViewById(R.id.txtInfo)).setText(str);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
