package com.blacklgames.healthdairy.db;

public class User {

    int mId;
    String mName;

    public User(){}

    public User(int id, String name)
    {
        mId = id;
        mName = name;
    }

    public User(String name)
    {
        mName = name;
    }
    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

}
