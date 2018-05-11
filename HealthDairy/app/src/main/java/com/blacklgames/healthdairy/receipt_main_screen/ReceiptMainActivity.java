package com.blacklgames.healthdairy.receipt_main_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.blacklgames.healthdairy.R;
import com.blacklgames.healthdairy.db.DB;
import com.blacklgames.healthdairy.db.dataobjects.Receipt;
import com.blacklgames.healthdairy.db.dataobjects.User;

import org.w3c.dom.Text;

public class ReceiptMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        User user = DB.get().users().getUser(0);
        int intentData = intent.getIntExtra("RECEIPT_ID_POSITION", 0);
        Log.d("onCreate", "intent data " + intentData);
        int receiptId = Character.getNumericValue(user.get_receipt_list().charAt(intentData));
        Receipt receipt = DB.get().receipts().getReceipt(receiptId);

        TextView tfDiagnose = (TextView)findViewById(R.id.dTextDiagnose);
        TextView tfDate = (TextView)findViewById(R.id.dTextDate);
        TextView tfCoast = (TextView)findViewById(R.id.dTextCoast);
        TextView tfComments = (TextView)findViewById(R.id.dTextComments);
        TextView tfRate = (TextView)findViewById(R.id.dTextRate);
        TextView tfDrugs = (TextView)findViewById(R.id.dTextDrugs);

        Log.d("onCreate", receipt.get_diagnosis());
        tfDiagnose.setText(receipt.get_diagnosis());
        tfDate.setText(receipt.get_date());
        tfCoast.setText(Float.toString(receipt.get_coast()));
        tfComments.setText(receipt.get_comments());
        tfDrugs.setText(receipt.get_drug_list());
    }

}
