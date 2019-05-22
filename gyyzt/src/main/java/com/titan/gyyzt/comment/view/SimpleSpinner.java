package com.titan.gyyzt.comment.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;


import com.titan.gyyzt.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 2019/4/25.
 */

public class SimpleSpinner extends AppCompatSpinner implements AdapterView.OnItemSelectedListener {

    Object key;

    Object val;

    ItemSelected itemSelected;

    List<String> keys = new ArrayList<>();

    boolean firstLoadDataComplete = false;

    public SimpleSpinner(Context context) {
        super(context);
        init();
    }

    public SimpleSpinner(Context context, int mode) {
        super(context, mode);
        init();
    }

    public SimpleSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SimpleSpinner);
        CharSequence[] _keyStr = array.getTextArray(R.styleable.SimpleSpinner_simple_spinner_keys);
        if (_keyStr != null) {
            for (int i = 0; i < _keyStr.length; i++) {
                keys.add(_keyStr[i].toString());
            }
        }
        array.recycle();
        init();
    }

    public SimpleSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SimpleSpinner);
        CharSequence[] _keys = array.getTextArray(R.styleable.SimpleSpinner_simple_spinner_keys);
        if (_keys != null) {
            for (int i = 0; i < _keys.length; i++) {
                keys.add(_keys[i].toString());
            }
        }
        array.recycle();
        init();
    }

    public SimpleSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SimpleSpinner);
        CharSequence[] _keys = array.getTextArray(R.styleable.SimpleSpinner_simple_spinner_keys);
        if (_keys != null) {
            for (int i = 0; i < _keys.length; i++) {
                keys.add(_keys[i].toString());
            }
        }
        array.recycle();
        init();
    }


    void init() {
        setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (!firstLoadDataComplete) firstLoadDataComplete = true;
        val = getAdapter().getItem(position);
        if (position <= keys.size() - 1) key = keys.get(position);
        else key = "";
        if (itemSelected != null) itemSelected.onItemSelected(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface ItemSelected {
        public void onItemSelected(int position);
    }

    public void setItemSelected(ItemSelected itemSelected) {
        this.itemSelected = itemSelected;
    }

    public Object getSelVal() {
        try {
            if (val == null) val = getAdapter().getItem(0);
        } catch (Exception e) {
            val = "";
        }
        return val;
    }

    public Object getKey() {
        try {
            if (key == null) key = keys.get(0);
        } catch (Exception e) {
            key = "";
        }
        return key;
    }
}
