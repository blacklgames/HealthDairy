package com.blacklgames.healthdairy.add_receipt_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.blacklgames.healthdairy.receipt_main_screen.ReceiptMainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddReceiptActivity extends AppCompatActivity
{
    final private static String TAG = "AddReceiptActivity";

    private int mReceiptId;
    private String mNewDrugId = "";

    private TextView mDiagnose;
    private TextView mDate;
    private TextView mCoast;
    private TextView mComments;

    private RecyclerView mList;
    private LinearLayoutManager mLayoutManager;
    private AddReceiptListAdapter mListAdapter;

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

        mList = (RecyclerView)findViewById(R.id.ar_DrugsList);
        mList.hasFixedSize();
        mLayoutManager = new LinearLayoutManager(this);
        mList.setLayoutManager(mLayoutManager);

        Receipt receipt = DB.get().receipts().getReceipt(mReceiptId);
        mListAdapter = new AddReceiptListAdapter((ArrayList)DB.get().drugs().getDrugsById(receipt.get_drug_list()));
        mList.setAdapter(mListAdapter);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
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
            mListAdapter.setDataSet((ArrayList)DB.get().drugs().getDrugsById(receipt.get_drug_list()));
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
        DB.get().drugs().deleteDrugs(mNewDrugId);
        this.finish();
    }

    public void onAddClick()
    {
        Receipt receipt;
        EditText tf = (EditText) findViewById(R.id.ar_txtDiagnose);
        //receipt.set_coast(Float.parseFloat(mCoast.getText()));

        if(isNewReceipt())
        {
            receipt = new Receipt();
            receipt.set_id(DB.get().receipts().getReceiptsCount());
            receipt.set_diagnosis(tf.getText().toString());
            receipt.set_date(mDate.getText().toString());
            receipt.set_comments(mComments.getText().toString());
            receipt.add_drug_id(mNewDrugId);

            User user = DB.get().users().getUser(0);
            user.add_receipt_id(Integer.toString(receipt.get_id()));
            DB.get().receipts().addReceipt(receipt);
            DB.get().users().updateUser(user);
        }
        else
        {
            receipt = DB.get().receipts().getReceipt(mReceiptId);
            receipt.set_diagnosis(tf.getText().toString());
            receipt.set_date(mDate.getText().toString());
            receipt.set_comments(mComments.getText().toString());
            receipt.add_drug_id(mNewDrugId);
            DB.get().receipts().updateReceipt(receipt);
        }
        finish();
    }

    private void addDrugClick()
    {
        Intent intent = new Intent(this, AddDrugActivity.class);
        intent.putExtra(ReceiptMainActivity.KEY_RECEIPT_ID, mReceiptId);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult " + data.getStringExtra("drug_id"));
        if(resultCode == RESULT_OK && data != null)
        {
            mNewDrugId += data.getStringExtra("drug_id");
            Log.d(TAG, "mNewDrugId " + mNewDrugId);
        }
    }
}
