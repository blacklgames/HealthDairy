package com.blacklgames.healthdairy.interfaces;

import com.blacklgames.healthdairy.db.dataobjects.Receipt;

import java.util.List;

public interface IDBReceiptsHandler extends IDBHandler
{
    public void addReceipt(Receipt receipt);
    public Receipt getReceipt(int id);
    public List<Receipt> getAllReceipts();
    public int updateReceipt(Receipt user);
    public void deleteReceipt(Receipt user);
    public void deleteAll();
    public int getReceiptsCount();
}