package com.blacklgames.healthdairy.db.DO;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Recipe
{
    private float _coast;
    private String _date;
    private String _rate;
    private ImageView _photo;
    private String _comments;
    private String _diagnosis;
    private List<Drug> _drug_list = new ArrayList<Drug>();

    public String get_rate() {
        return _rate;
    }

    public void set_rate(String _rate) {
        this._rate = _rate;
    }

    public String get_comments() {
        return _comments;
    }

    public void set_comments(String _comments) {
        this._comments = _comments;
    }

    public String get_diagnosis() {
        return _diagnosis;
    }

    public void set_diagnosis(String _diagnosis) {
        this._diagnosis = _diagnosis;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public ImageView get_photo() {
        return _photo;
    }

    public void set_photo(ImageView _photo) {
        this._photo = _photo;
    }


    public float get_coast() {
        return _coast;
    }

    public void set_coast(float _coast) {
        this._coast = _coast;
    }
}
