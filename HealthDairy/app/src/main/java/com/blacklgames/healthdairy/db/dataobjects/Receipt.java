package com.blacklgames.healthdairy.db.dataobjects;

public class Receipt
{
    private int _id;
    private float _coast;
    private String _date;
    private int _rate;
    private /*ImageView*/ int _photo;
    private String _comments;
    private String _diagnosis;
    private String _drug_list ;

    public int get_id() { return _id; }

    public void set_id(int _id) { this._id = _id; }

    public String get_drug_list() { return _drug_list;}

    public void add_drug_id(String _id) {this._drug_list += _id; }

    public int get_rate() {return _rate;}

    public void set_rate(int _rate) {this._rate = _rate;}

    public String get_comments() {return _comments;}

    public void set_comments(String _comments) {this._comments = _comments;}

    public String get_diagnosis() {return _diagnosis;}

    public void set_diagnosis(String _diagnosis) {this._diagnosis = _diagnosis;}

    public String get_date() {return _date;}

    public void set_date(String _date) {this._date = _date;}

    public int get_photo() {return _photo;}

    public void set_photo(int _photo) {this._photo = _photo; }

    public float get_coast() {return _coast; }

    public void set_coast(float _coast) {this._coast = _coast;}
}
