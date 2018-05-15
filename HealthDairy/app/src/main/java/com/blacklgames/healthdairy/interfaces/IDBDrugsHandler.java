package com.blacklgames.healthdairy.interfaces;

import com.blacklgames.healthdairy.db.dataobjects.Drug;

import java.util.List;

public interface IDBDrugsHandler extends IDBHandler
{
    public void addDrug(Drug drug);
    public Drug getDrug(int id);
    public List<Drug> getAllDrugs();
    public List<Drug> getDrugsById(String ids);
    public int updateDrug(Drug drug);
    public void deleteDrugs(String ids);
    public void deleteAll();
    public int getDrugsCount();
}
