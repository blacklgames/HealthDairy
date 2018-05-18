package com.blacklgames.healthdairy.receipt_main_screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.blacklgames.healthdairy.add_receipt_screen.AddReceiptActivity;
import com.blacklgames.healthdairy.R;
import com.blacklgames.healthdairy.db.DB;
import com.blacklgames.healthdairy.db.dataobjects.Drug;
import com.blacklgames.healthdairy.db.dataobjects.Receipt;
import com.blacklgames.healthdairy.db.dataobjects.User;

import java.util.ArrayList;

public class ReceiptMainActivity extends AppCompatActivity
{
    final public static String TAG = "ReceiptMainActivity";
    final public static String KEY_RECEIPT_ID_POSITION = "RECEIPT_ID_POSITION";
    final public static String KEY_RECEIPT_ID = "RECEIPT_ID";

    int mReceiptId;
    private RecyclerView mList;
    private RecyclerView.LayoutManager mLayoutManager;
    private ReceiptMainListAdapter mListAdapter;

    public static Intent getReceiptIdPositionIntent(Context context, int idPosition)
    {
        Intent intent = new Intent(context, ReceiptMainActivity.class);
        intent.putExtra(KEY_RECEIPT_ID_POSITION, idPosition);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Информация о рецепте");

        Intent intent = getIntent();
        int intentData = intent.getIntExtra(KEY_RECEIPT_ID_POSITION, 0);

        Log.d(TAG, "intentData " + intentData );
        Log.d(TAG, "DB.get().users().getUser(0).get_receipt_list() " + DB.get().users().getUser(0).get_receipt_list() );
        Log.d(TAG, "DB.get().users().getUser(0).get_receipt_list().charAt(intentData) " + DB.get().users().getUser(0).get_receipt_list().charAt(intentData) );

        mReceiptId = Character.getNumericValue(DB.get().users().getUser(0).get_receipt_list().charAt(intentData));
        Receipt receipt = DB.get().receipts().getReceipt(mReceiptId);

        TextView tfDiagnose =   (TextView)findViewById(R.id.ar_txtDiagnose);
        TextView tfDate =       (TextView)findViewById(R.id.ar_txtDate);
        TextView tfCoast =      (TextView)findViewById(R.id.ar_txtCoast);
        TextView tfComments =   (TextView)findViewById(R.id.ar_txtComments);
        TextView tfRate =       (TextView)findViewById(R.id.ar_txtRate);

        tfDiagnose.setText(receipt.get_diagnosis());
        tfDate.setText(receipt.get_date());
        tfCoast.setText(Float.toString(receipt.get_coast()));
        tfComments.setText(receipt.get_comments());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.rm_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editReceipt();
            }
        });

        ArrayList<Drug> drugList = (ArrayList)DB.get().drugs().getDrugsById(receipt.get_drug_list());
        mList = (RecyclerView)findViewById(R.id.rm_drugsList);
        mList.hasFixedSize();
        mLayoutManager = new LinearLayoutManager(this);
        mList.setLayoutManager(mLayoutManager);
        mListAdapter = new ReceiptMainListAdapter(drugList);
        mList.setAdapter(mListAdapter);

        TextView tf = (TextView)findViewById(R.id.ar_txtEmptyDrugs);
        if(drugList.size() > 0)
        {
            tf.setVisibility(TextView.INVISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void editReceipt()
    {
        Intent intent = AddReceiptActivity.getReceiptIdIntent(this, mReceiptId);
        startActivity(intent);
    }
}
