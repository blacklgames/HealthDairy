package com.blacklgames.healthdairy.add_receipt_screen;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blacklgames.healthdairy.R;
import com.blacklgames.healthdairy.db.dataobjects.Drug;
import com.blacklgames.healthdairy.db.dataobjects.Receipt;
import com.blacklgames.healthdairy.receipt_main_screen.ReceiptMainActivity;

import java.util.ArrayList;


public class AddReceiptListAdapter extends RecyclerView.Adapter<AddReceiptListAdapter.ViewHolder>
{
    private ArrayList<Drug> mDrugList;

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        Context mContext;

        // наш пункт состоит только из одного TextView
        public TextView mName;
        public TextView mMethod;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            mName = (TextView) v.findViewById(R.id.di_textDrugName);
            mMethod = (TextView) v.findViewById(R.id.di_textDrugMethod);
        }

        @Override
        public void onClick(View view) {
            Log.d("onClick", "onClick " + getPosition());

            //Intent intent = new Intent(mContext, ReceiptMainActivity.class);
            //intent.putExtra("RECEIPT_ID_POSITION", getPosition());
            //mContext.startActivity(intent);
        }

        public void setContext(Context c)
        {
            mContext = c;
        }
    }

    // Конструктор
    public AddReceiptListAdapter(ArrayList<Drug> list)
    {
        mDrugList = list;
    }

    // Создает новые views (вызывается layout manager-ом)
    @Override
    public AddReceiptListAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        // create a new view
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_receipt_drugs, parent,false);
        ViewHolder vh = new ViewHolder(v);
        vh.setContext(parent.getContext());

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Drug d = mDrugList.get(position);
        String method = d.get_input_count() + " " + R.string.ad_label_count_in + d.get_input_period()+ " " + R.string.ad_label_by  + " " + d.get_drug_count();

        holder.mName.setText(d.get_name());
        holder.mMethod.setText(method);
    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount()
    {
        return mDrugList.size();
    }
}
