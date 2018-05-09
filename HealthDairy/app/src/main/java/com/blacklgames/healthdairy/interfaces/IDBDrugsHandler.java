package com.blacklgames.healthdairy.interfaces;

import com.blacklgames.healthdairy.db.dataobjects.Drug;

import java.util.List;

public interface IDBDrugsHandler extends IDBHandler
{
    public void addDrug(Drug drug);
    public Drug getDrug(int id);
    public List<Drug> getAllDrugs();
    public int updateDrug(Drug drug);
    public void deleteDrug(Drug drug);
    public void deleteAll();
    public int getDrugsCount();
}
