package com.blacklgames.healthdairy.receipt_main_screen;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blacklgames.healthdairy.R;
import com.blacklgames.healthdairy.db.dataobjects.Drug;

import java.util.ArrayList;


public class ReceiptMainListAdapter extends RecyclerView.Adapter<ReceiptMainListAdapter.ViewHolder>
{
    private ArrayList<Drug> mDrugsList;

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        Context mContext;

        // наш пункт состоит только из одного TextView
        public TextView mDrugName;
        public TextView mDrugMethod;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            mDrugName = (TextView) v.findViewById(R.id.ad_txtDiagnose);
            mDrugMethod = (TextView) v.findViewById(R.id.ad_txtDrugMethod);
        }

        @Override
        public void onClick(View view) {
            Log.d("onClick", "onClick " + getPosition());
        }

        public void setContext(Context c)
        {
            mContext = c;
        }
    }

    // Конструктор
    public ReceiptMainListAdapter(ArrayList<Drug> list)
    {
        mDrugsList = list;
    }

    // Создает новые views (вызывается layout manager-ом)
    @Override
    public ReceiptMainListAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
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
        Drug d = mDrugsList.get(position);
        String c = holder.mContext.getString(R.string.ad_label_count_in);
        String b = holder.mContext.getString(R.string.ad_label_by);
        Resources res = holder.mContext.getResources();
        String period = res.getStringArray(R.array.drug_period_list)[d.get_input_period()];
        String pack = res.getStringArray(R.array.drug_pack_list)[d.get_pack()];
        String method = d.get_input_count() + " " + c + period + " " + b + " " + d.get_drug_count() + " " + pack;
        holder.mDrugName.setText(d.get_name());
        holder.mDrugMethod.setText(method);

    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount()
    {
        return mDrugsList.size();
    }
}
