package com.blacklgames.healthdairy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blacklgames.healthdairy.db.DB;
import com.blacklgames.healthdairy.db.dataobjects.Drug;
import com.blacklgames.healthdairy.db.dataobjects.Receipt;
import com.blacklgames.healthdairy.receipt_main_screen.ReceiptMainActivity;

public class AddDrugActivity extends AppCompatActivity
{
    public enum eDrugInputMethods
    {
        IM_ORAL,
        IM_INHALATION,
        IM_INTRAMUSCULAR,
        IM_INTRAVENOUS,
        IM_SUBLINGUAL,
        IM_BUCCAL,
        IM_RECTAL,
        IM_VAGINAL,
        IM_URETHRAL,
        IM_MAX
    }

    public enum eDrugInputPeriod
    {
        IP_EVERY_HOUR,
        IP_DAY,
        IP_WEEK,
        IP_MONTH,
        IP_YEAR,
        IP_MAX
    }

    private static final String TAG = "AddDrugActivity";

    private TextView mName;
    private TextView mInputCount;
    private Spinner  mInputPeriod;
    private TextView mDrugCount;
    private Spinner  mPack;
    private TextView mDuration;
    private Spinner  mDurationPeriod;
    private Spinner  mInputType;
    private TextView mCoast;
    private TextView mComments;

    private int mReceiptId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drug);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Добавьте Препарат");

        mReceiptId = getIntent().getIntExtra(ReceiptMainActivity.KEY_RECEIPT_ID, 0);

        mName = findViewById(R.id.ad_txtName);
        mInputCount = findViewById(R.id.ad_txtInputCount);
        mInputPeriod = findViewById(R.id.ad_spinInputPeriod);
        mDrugCount = findViewById(R.id.ad_txtDrugCount);
        mPack = findViewById(R.id.ad_spinPack);
        mDuration = findViewById(R.id.ad_txtDuration);
        mDurationPeriod = findViewById(R.id.ad_spinDurationPeriod);
        mInputType = findViewById(R.id.ad_spinInputType);
        mCoast = findViewById(R.id.ad_txtCoast);
        mComments = findViewById(R.id.ad_txtComments);

        ArrayAdapter<CharSequence> periodAdapter = ArrayAdapter.createFromResource(this, R.array.drug_period_list, R.layout.spinner_item);
        periodAdapter.setDropDownViewResource(R.layout.spinner_item);
        mInputPeriod.setAdapter(periodAdapter);
        mInputPeriod.setSelection(0);

        ArrayAdapter<CharSequence> methodAdapter = ArrayAdapter.createFromResource(this, R.array.drug_pack_list, R.layout.spinner_item);
        methodAdapter.setDropDownViewResource(R.layout.spinner_item);
        mPack.setAdapter(methodAdapter);
        mPack.setSelection(0);

        ArrayAdapter<CharSequence> commonPeriodAdapter = ArrayAdapter.createFromResource(this, R.array.drug_common_period_list, R.layout.spinner_item);
        methodAdapter.setDropDownViewResource(R.layout.spinner_item);
        mDurationPeriod.setAdapter(commonPeriodAdapter);
        mDurationPeriod.setSelection(0);

        ArrayAdapter<CharSequence> inputTypeAdapter = ArrayAdapter.createFromResource(this, R.array.drug_input_type_list, R.layout.spinner_item);
        methodAdapter.setDropDownViewResource(R.layout.spinner_item);
        mInputType.setAdapter(inputTypeAdapter);
        mInputType.setSelection(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_drug_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.ad_action_done:
                addDrug();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        this.finish();
    }

    private void addDrug()
    {
        String nll = "0";
        String count = mInputCount.getText().toString();
        String coast = mCoast.getText().toString();
        String drugCount = mDrugCount.getText().toString();
        String duration = mDuration.getText().toString();

        Drug drug = new Drug();
        Log.d(TAG, "count " + DB.get().drugs().getDrugsCount());
        drug.set_id(DB.get().drugs().getDrugsCount());
        drug.set_name(mName.getText().toString());
        drug.set_input_count(Integer.parseInt(count.length() > 0 ? count : nll));
        drug.set_input_period(mInputPeriod.getSelectedItemPosition());
        drug.set_drug_count(Float.parseFloat(drugCount.length() > 0 ? drugCount : nll));
        drug.set_pack(mPack.getSelectedItemPosition());
        drug.set_duration(Integer.parseInt(duration.length() > 0 ? duration : nll));
        drug.set_duration_period(mDurationPeriod.getSelectedItemPosition());
        drug.set_input_type(mInputType.getSelectedItemPosition());
        drug.set_coast(Float.parseFloat(coast.length() > 0 ? coast : nll ));
        drug.set_comments(mComments.getText().toString());
        DB.get().drugs().addDrug(drug);

        Intent intent = new Intent();
        intent.putExtra("drug_id", String.valueOf(drug.get_id()));
        setResult(RESULT_OK, intent);
        finish();
    }
}
