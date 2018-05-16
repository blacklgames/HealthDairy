package com.blacklgames.healthdairy.db.dataobjects;

import android.util.Log;

import com.blacklgames.healthdairy.db.DB;

import java.util.ArrayList;
import java.util.List;

public class User {
    
    private int _id;
    private String _name = "";
    private String _pass = "";
    private String _receipt_list = "";

    public String get_receipt_list() { return _receipt_list; }

    public void add_receipt_id(String _receipt_list) { this._receipt_list += _receipt_list;}

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {return _name;}

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_pass() {
        return _pass;
    }

    public void set_pass(String _pass) {
        this._pass = _pass;
    }

}