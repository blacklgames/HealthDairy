package com.blacklgames.healthdairy.db.dataobjects;

public class Drug
{

    private int _id;
    private String _name;
    private String _comments;
    private int _count;
    private int _period;
    private int _input_cont;
    private int _input_period;
    private float _coast;
    private float _count_on_taking;

    public int get_id(){ return _id;}

    public void set_id(int _id) {this._id = _id;}

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_comments() {
        return _comments;
    }

    public void set_comments(String _comments) {
        this._comments = _comments;
    }

    public int get_count() {
        return _count;
    }

    public void set_count(int _count) {
        this._count = _count;
    }

    public int get_period() {return _period;}

    public void set_period(int _period) {
        this._period = _period;
    }

    public int get_input_cont() {
        return _input_cont;
    }

    public void set_input_cont(int _input_cont_) {this._input_cont = _input_cont_;}

    public int get_input_period() {
        return _input_period;
    }

    public void set_input_period(int _input_period) {
        this._input_period = _input_period;
    }

    public float get_coast() {
        return _coast;
    }

    public void set_coast(float _coast) {
        this._coast = _coast;
    }

    public float get_count_on_taking() {
        return _count_on_taking;
    }

    public void set_count_on_taking(float _count_on_taking) {this._count_on_taking = _count_on_taking;}
}

