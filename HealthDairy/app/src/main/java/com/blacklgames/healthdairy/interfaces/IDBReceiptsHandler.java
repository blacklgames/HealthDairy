package com.blacklgames.healthdairy.interfaces;

import com.blacklgames.healthdairy.db.dataobjects.Receipt;

import java.util.List;

public interface IDBReceiptsHandler extends IDBHandler
{
    public void addReceipt(Receipt receipt);
    public Receipt getReceipt(int id);
    public List<Receipt> getAllReceipts();
    public List<Receipt> getReceiptsById(String ids);
    public int updateReceipt(Receipt user);
    public void deleteReceipt(int receiptId);
    public void deleteAll();
    public int getReceiptsCount();
}
