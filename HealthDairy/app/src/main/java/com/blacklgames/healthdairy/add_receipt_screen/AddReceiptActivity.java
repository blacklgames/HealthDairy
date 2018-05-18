package com.blacklgames.healthdairy.add_receipt_screen;

import android.content.Context;
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
    final private static String KEY_RECEIPT_ID = "com.blacklgames.healthdairy.add_receipt_screen.KEY_RECEIPT_ID";

    private boolean mNew;
    private int mReceiptId;
    private Receipt mReceipt;

    private String mNewDrugId = "";

    private TextView mDiagnose;
    private TextView mDate;
    private TextView mCoast;
    private TextView mComments;

    private RecyclerView mList;
    private LinearLayoutManager mLayoutManager;
    private AddReceiptListAdapter mListAdapter;

    public static Intent getReceiptIdIntent(Context context, int receiptId)
    {
        Intent intent = new Intent(context, AddReceiptActivity.class);
        intent.putExtra(KEY_RECEIPT_ID, receiptId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        int id = getIntent().getIntExtra(KEY_RECEIPT_ID, -1);

        Log.d(TAG, "id " + id);

        mNew = id == -1;
        if(mNew)
        {
            // add new receipt
            Date date = new Date();
            SimpleDateFormat dateFormatWithZone = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String currentDate = dateFormatWithZone.format(date);

            mReceiptId = DB.get().receipts().getReceiptsCount();
            mReceipt = new Receipt();
            mReceipt.set_id(mReceiptId);
            mReceipt.set_diagnosis("");
            mReceipt.set_date(currentDate);
            mReceipt.set_comments("");
            mReceipt.add_drug_id("");
            DB.get().receipts().addReceipt(mReceipt);
        }
        else
        {
            // edit existing receipt
            mReceiptId = id;
            mReceipt = DB.get().receipts().getReceipt(mReceiptId);
        }

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
        mListAdapter = new AddReceiptListAdapter((ArrayList)DB.get().drugs().getDrugsById(mReceipt.get_drug_list()));
        mList.setAdapter(mListAdapter);

    }

    private void fillWidgets()
    {
        mDiagnose.setText(mReceipt.get_diagnosis());
        mDate.setText(mReceipt.get_date());
        mCoast.setText(Float.toString(mReceipt.get_coast()));
        mComments.setText(mReceipt.get_comments());
        mListAdapter.setDataSet((ArrayList)DB.get().drugs().getDrugsById(mReceipt.get_drug_list()));
    }

    private void fillReceipt()
    {
        mReceipt.set_diagnosis(mDiagnose.getText().toString());
        mReceipt.set_date(mDate.getText().toString());
        mReceipt.set_comments(mComments.getText().toString());
        DB.get().receipts().updateReceipt(mReceipt);
    }

    @Override
    protected void onResume()
    {
        Log.d(TAG, "onResume");
        super.onResume();
        fillWidgets();
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
    public void onBackPressed()
    {
        super.onBackPressed();
        DB.get().drugs().deleteDrugs(mNewDrugId);
        if(mNew)
            DB.get().receipts().deleteReceipt(mReceiptId);
        this.finish();
    }

    public void onAddClick()
    {
        fillReceipt();
        Log.d(TAG, "receipt id " + mReceipt.get_id());
        Log.d(TAG, "receipt count " + DB.get().receipts().getReceiptsCount());
        if(mNew)
        {
            User user = DB.get().users().getUser(0);
            user.add_receipt_id(Integer.toString(mReceipt.get_id()));
            Log.d(TAG, "user receipt ids " + user.get_receipt_list());
            DB.get().users().updateUser(user);
        }
        finish();
    }

    private void addDrugClick()
    {
        fillReceipt();
        Intent intent = new Intent(this, AddDrugActivity.class);
        intent.putExtra(ReceiptMainActivity.KEY_RECEIPT_ID, mReceiptId);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null)
        {
            mNewDrugId += data.getStringExtra("drug_id");
            mReceipt.add_drug_id(mNewDrugId);
        }
    }
}
