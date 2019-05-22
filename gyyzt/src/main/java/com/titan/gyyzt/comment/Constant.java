package com.titan.gyyzt.comment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.titan.gyyzt.base.time.TimePickerDialogUtil;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Constant {

    public static final String TAG = "日志错误";

    public static final int SCALE = 2000;

    public static final int PICK_CAMERA= 0x000007;
    public static final int PICK_PHOTO = 0x000003;
    public static final int PICK_AUDIO = 0x000004;
    public static final int PICK_VIDEO = 0x000005;
    public static final int PICK_PEOPLE = 0x000006;

    public static final String PREFS_NAME = "MYSP";

    public static DecimalFormat disFormat = new DecimalFormat("0.00");
    public static DecimalFormat sixFormat = new DecimalFormat("0.000000");
    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");

    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat nameFormat = new SimpleDateFormat("yyyyMMdd HHmmss");


    /*String 字符串保留两位小数*/
    public static String strFormat(String value){
        return Constant.disFormat.format(new BigDecimal(value));
    }

    public static RequestBody requestBody(Object obj) {
        String json = new Gson().toJson(obj);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
    }

    public static MultipartBody fileBody(File file) {
        //构建body
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        requestBody.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        MultipartBody multipartbody = requestBody.build();
        return multipartbody;
    }


    public static SharedPreferences getSharedPre(Context context){
        return context.getSharedPreferences(PREFS_NAME, 0);
    }


    public static void showTime(FragmentActivity activity, final TextView textView){
        TimePickerDialogUtil.showAllPickerDialog(activity, new OnDateSetListener() {
            @Override
            public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                textView.setText(Constant.dateFormat.format(new Date(millseconds)));
            }
        }).show(activity.getSupportFragmentManager(), "时间选择");
    }

    public static void showTime(AppCompatActivity activity, final TextView textView, final ValueCallback callback){
        TimePickerDialogUtil.showAllPickerDialog(activity, new OnDateSetListener() {
            @Override
            public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                textView.setText(Constant.dateFormat.format(new Date(millseconds)));
                callback.onSuccess(millseconds);
            }
        }).show(activity.getSupportFragmentManager(), "时间选择");
    }

    //计算listivew自孩子的高度，在setAdapter之后调用。
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


}
