package com.blacklgames.healthdairy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blacklgames.healthdairy.db.DB;
import com.blacklgames.healthdairy.db.dataobjects.Receipt;
import com.blacklgames.healthdairy.db.dataobjects.User;

import java.util.ArrayList;

public class AddReceiptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receipt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onAddClick(View view)
    {
        Log.d("AddReceiptActivity", "add receipt " + DB.get().receipts().getReceiptsCount());
        EditText tf = (EditText) findViewById(R.id.txtDiagnose);
        Receipt receipt = new Receipt();
        receipt.set_id(DB.get().receipts().getReceiptsCount());
        receipt.set_diagnosis(tf.getText().toString());
        DB.get().receipts().addReceipt(receipt);

        User user = DB.get().users().getUser(0);
        user.set_receipt_list("0");
        DB.get().users().updateUser(user);
    }

}
