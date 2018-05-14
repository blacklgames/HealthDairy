package com.blacklgames.healthdairy.db.dataobjects;

public class Drug
{
    private int _id;
    private String _name = "";
    private int _input_count;
    private int _input_period;
    private float _drug_count;
    private int _pack;
    private int _duration;
    private int _duration_period;
    private int _input_type;
    private float _coast;
    private String _comments = "";

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

    public int get_duration() {
        return _duration;
    }

    public void set_duration(int _duration) {
        this._duration = _duration;
    }

    public int get_duration_period() {return _duration_period;}

    public void set_duration_period(int _duration_period) {this._duration_period = _duration_period;}

    public int get_input_count() {
        return _input_count;
    }

    public void set_input_count(int _input_cont_) {this._input_count = _input_cont_;}

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

    public float get_drug_count() { return _drug_count; }

    public void set_drug_count(float _drug_count) { this._drug_count = _drug_count; }

    public int get_input_type() {return _input_type;}

    public void set_input_type(int _input_type) {this._input_type = _input_type;}

    public int get_pack() { return _pack;}

    public void set_pack(int _pack) {this._pack = _pack;}
}

