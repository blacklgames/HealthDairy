package com.blacklgames.healthdairy.user_main_screen;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blacklgames.healthdairy.R;
import com.blacklgames.healthdairy.db.DB;
import com.blacklgames.healthdairy.db.dataobjects.Drug;
import com.blacklgames.healthdairy.db.dataobjects.Receipt;
import com.blacklgames.healthdairy.receipt_main_screen.ReceiptMainActivity;

import java.util.ArrayList;
import java.util.List;


public class UserMainListAdapter extends RecyclerView.Adapter<UserMainListAdapter.ViewHolder>
{
    private ArrayList<Receipt> mReceiptList;

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        Context mContext;

        // наш пункт состоит только из одного TextView
        public TextView mDiagnoseText;
        public TextView mDateText;
        public TextView mDrugsText;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            mDiagnoseText = (TextView) v.findViewById(R.id.um_txtDiagmose);
            mDateText = (TextView) v.findViewById(R.id.um_txtDate);
            mDrugsText = (TextView) v.findViewById(R.id.um_txtDrugList);
        }

        @Override
        public void onClick(View view) {
            Log.d("onClick", "onClick " + getPosition());

            Intent intent = new Intent(mContext, ReceiptMainActivity.class);
            intent.putExtra("RECEIPT_ID_POSITION", getPosition());
            mContext.startActivity(intent);
        }

        public void setContext(Context c)
        {
            mContext = c;
        }
    }

    // Конструктор
    public UserMainListAdapter(ArrayList<Receipt> list)
    {
        mReceiptList = list;
    }

    // Создает новые views (вызывается layout manager-ом)
    @Override
    public UserMainListAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        // create a new view
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user_receipt, parent,false);
        ViewHolder vh = new ViewHolder(v);
        vh.setContext(parent.getContext());

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        String drugs = "";
        List<Drug> dList = DB.get().drugs().getDrugsById(mReceiptList.get(position).get_drug_list());
        for (Drug d : dList) {
            Log.d("umla", "d.get_name()" + d.get_name());
            drugs += d.get_name() + ", ";
        }
        Log.d("umla", "drugs " + drugs);
        holder.mDiagnoseText.setText(mReceiptList.get(position).get_diagnosis());
        holder.mDateText.setText(mReceiptList.get(position).get_date());
        holder.mDrugsText.setText(drugs);
    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount()
    {
        return mReceiptList.size();
    }
}
