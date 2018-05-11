package com.blacklgames.healthdairy.user_main_screen;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blacklgames.healthdairy.R;
import com.blacklgames.healthdairy.db.dataobjects.Receipt;
import com.blacklgames.healthdairy.receipt_main_screen.ReceiptMainActivity;

import java.util.ArrayList;


public class UserMainListAdapter extends RecyclerView.Adapter<UserMainListAdapter.ViewHolder>
{
    private ArrayList<Receipt> mReceiptList;

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        // наш пункт состоит только из одного TextView
        public TextView mDiagnoseText;
        public TextView mDateText;
        public TextView mDrugsText;

        public ViewHolder(View v) {
            super(v);
            mDiagnoseText = (TextView) v.findViewById(R.id.text_diagnose);
            mDateText = (TextView) v.findViewById(R.id.text_date);
            mDrugsText = (TextView) v.findViewById(R.id.text_drugs);
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
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_receipt_list_item, parent,false);
        final ViewHolder vh = new ViewHolder(v);

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                long newposition = vh.getItemId();

                Log.d("onCreate ", "position " + newposition);
                Intent intent = new Intent(parent.getContext(), ReceiptMainActivity.class);
                intent.putExtra("Position", newposition);
                //parent.getContext().startActivity(intent);
            }
        });
        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.mDiagnoseText.setText(mReceiptList.get(position).get_diagnosis());
        holder.mDateText.setText(mReceiptList.get(position).get_date());
        holder.mDrugsText.setText(mReceiptList.get(position).get_drug_list());
    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount()
    {
        return mReceiptList.size();
    }
}
