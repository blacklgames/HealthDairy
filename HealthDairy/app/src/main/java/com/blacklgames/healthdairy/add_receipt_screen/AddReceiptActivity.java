package com.blacklgames.healthdairy.add_receipt_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blacklgames.healthdairy.AddDrugActivity;
import com.blacklgames.healthdairy.R;
import com.blacklgames.healthdairy.db.DB;
import com.blacklgames.healthdairy.db.dataobjects.Receipt;
import com.blacklgames.healthdairy.db.dataobjects.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddReceiptActivity extends AppCompatActivity
{
    private int mReceiptId;
    private TextView mDiagnose;
    private TextView mDate;
    private TextView mCoast;
    private TextView mComments;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        mReceiptId = getIntent().getIntExtra("RECEIPT_ID", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receipt);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Добавьте описание рецепта");

        mDiagnose = (TextView)findViewById(R.id.ar_txtDiagnose);
        mDate = (TextView)findViewById(R.id.ar_txtDate);
        mCoast = (TextView)findViewById(R.id.ar_txtCoast);
        mComments = (TextView)findViewById(R.id.ar_txtComments);

        Button btn = (Button)findViewById(R.id.ar_btnAddDrug);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addDrugClick();
            }
        });

        if(isNewReceipt())
        {
            Date date = new Date();
            SimpleDateFormat dateFormatWithZone = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String currentDate = dateFormatWithZone.format(date);
            mDate.setText(currentDate);
        }
        else
        {
            Receipt receipt = DB.get().receipts().getReceipt(mReceiptId);
            mDiagnose.setText(receipt.get_diagnosis());
            mDate.setText(receipt.get_date());
            mCoast.setText(Float.toString(receipt.get_coast()));
            mComments.setText(receipt.get_comments());
        }
    }

    private boolean isNewReceipt()
    {
        return mReceiptId == -1;
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
        this.finish();
    }

    public void onAddClick()
    {
        EditText tf = (EditText) findViewById(R.id.ar_txtDiagnose);
        Receipt receipt = new Receipt();

        receipt.set_id(isNewReceipt() ? DB.get().receipts().getReceiptsCount() : mReceiptId);
        receipt.set_diagnosis(tf.getText().toString());
        receipt.set_date(mDate.getText().toString());
        receipt.set_comments(mComments.getText().toString());
        //receipt.set_coast(Float.parseFloat(mCoast.getText()));

        if(isNewReceipt())
        {
            User user = DB.get().users().getUser(0);
            user.add_receipt_id(Integer.toString(receipt.get_id()));
            DB.get().receipts().addReceipt(receipt);
            DB.get().users().updateUser(user);
        }
        else
        {
            DB.get().receipts().updateReceipt(receipt);
        }
        onBackPressed();
    }

    private void addDrugClick()
    {
        Intent intent = new Intent(this, AddDrugActivity.class);
        startActivity(intent);
    }
}
