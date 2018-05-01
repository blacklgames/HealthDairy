package com.blacklgames.healthdairy.db.DO;

import java.util.ArrayList;
import java.util.List;

public class User {
    
    private int _id;
    private String _name;
    private String _pass;
    private boolean _current;
    private List<Recipe> _receipt_list = new ArrayList<Recipe>();

    public boolean is_current() {
        return _current;
    }

    public void set_current(boolean _current) {
        this._current = _current;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

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