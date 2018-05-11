package com.blacklgames.healthdairy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blacklgames.healthdairy.db.DB;
import com.blacklgames.healthdairy.db.dataobjects.Receipt;
import com.blacklgames.healthdairy.db.dataobjects.User;
import com.blacklgames.healthdairy.user_main_screen.UserMainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddReceiptActivity extends AppCompatActivity
{
    TextView mDiagnose;
    TextView mDate;
    TextView mCoast;
    TextView mComments;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receipt);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Добавьте рецепт");

        mDiagnose = (TextView)findViewById(R.id.txtAddDiagnose);
        mDate = (TextView)findViewById(R.id.txtAddDate);
        mCoast = (TextView)findViewById(R.id.txtAddCoast);
        mComments = (TextView)findViewById(R.id.txtAddComments);

        Date date = new Date();
        SimpleDateFormat dateFormatWithZone = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormatWithZone.format(date);
        mDate.setText(currentDate);

        mDiagnose.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if (keyCode == KeyEvent.KEYCODE_ENTER)
                    {

                    }
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_done:
                onAddClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_receipt_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, UserMainActivity.class));
    }

    public void onAddClick()
    {
        EditText tf = (EditText) findViewById(R.id.txtAddDiagnose);
        Receipt receipt = new Receipt();
        receipt.set_id(DB.get().receipts().getReceiptsCount());
        receipt.set_diagnosis(tf.getText().toString());
        receipt.set_date(mDate.getText().toString());
        receipt.set_comments(mComments.getText().toString());
        //receipt.set_coast(Float.parseFloat(mCoast.getText()));
        DB.get().receipts().addReceipt(receipt);

        User user = DB.get().users().getUser(0);
        user.add_receipt_id(Integer.toString(receipt.get_id()));
        DB.get().users().updateUser(user);

        Intent intent = new Intent(this, UserMainActivity.class);
        startActivity(intent);
    }
}
