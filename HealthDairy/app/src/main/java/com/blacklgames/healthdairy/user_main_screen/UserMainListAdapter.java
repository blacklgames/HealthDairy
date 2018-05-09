package com.blacklgames.healthdairy.user_main_screen;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blacklgames.healthdairy.R;
import com.blacklgames.healthdairy.db.dataobjects.Receipt;

import java.util.ArrayList;


public class UserMainListAdapter extends RecyclerView.Adapter<UserMainListAdapter.ViewHolder>
{
    private ArrayList<Receipt> mReceiptList;

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        // наш пункт состоит только из одного TextView
        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.diagnose);
        }
    }

    // Конструктор
    public UserMainListAdapter(ArrayList<Receipt> list)
    {
        Log.d("UserMainListAdapter", "position " + list.size());
        mReceiptList = list;
    }

    // Создает новые views (вызывается layout manager-ом)
    @Override
    public UserMainListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_receipt_list_item, parent,false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Log.d("onBindViewHolder", "position " + position);
        holder.mTextView.setText(mReceiptList.get(position).get_diagnosis());
    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount()
    {
        return mReceiptList.size();
    }
}
