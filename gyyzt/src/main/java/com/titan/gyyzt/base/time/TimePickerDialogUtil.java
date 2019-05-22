package com.titan.gyyzt.base.time;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.titan.gyyzt.R;
import com.titan.gyyzt.comment.Constant;

import java.util.Date;

public class TimePickerDialogUtil {

    //时间选择
    private static TimePickerDialog mTimePickerDialog;


    /**
     * 时间弹框  年月日
     */
    public static void bindTimePicker(final Context context, final View view) {
        if (!(context instanceof FragmentActivity)) return;
        final FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialogUtil.showPickerDialog(context, new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        String s = Constant.yearFormat.format(new Date(millseconds));
                        if (view instanceof EditText) ((EditText) view).setText(s);
                        if (view instanceof TextView) ((TextView) view).setText(s);
                    }
                }).show(manager, "==");
            }
        });

    }


    public static TimePickerDialog showPickerDialog(Context context, OnDateSetListener listener) {
//        if (mTimePickerDialog == null) {
        long fiveYears = 5L * 365 * 1000 * 60 * 60 * 24L;
        long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
        mTimePickerDialog = new TimePickerDialog.Builder()
                .setCallBack(listener)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("时间选择")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setThemeColor(context.getResources().getColor(R.color.colorPrimary))
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis() - fiveYears)
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(context.getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(context.getResources().getColor(R.color.colorAccent))
                .setWheelItemTextSize(12)
                .build();
//        }
        return mTimePickerDialog;
    }

    public static TimePickerDialog showAllPickerDialog(Context context, OnDateSetListener listener) {
        long fiveYears = 5L * 365 * 1000 * 60 * 60 * 24L;
        long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
        mTimePickerDialog = new TimePickerDialog.Builder()
                .setCallBack(listener)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("时间选择")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setHourText("时")
                .setMinuteText("分")
                .setThemeColor(context.getResources().getColor(R.color.colorPrimary))
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis() - fiveYears)
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(context.getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(context.getResources().getColor(R.color.colorAccent))
                .setWheelItemTextSize(12)
                .build();
        return mTimePickerDialog;
    }
}
