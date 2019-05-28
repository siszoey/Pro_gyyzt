package com.lib.bandaid.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lib.bandaid.utils.MaterialDialogUtil;
import com.lib.bandaid.utils.MeasureScreen;
import com.lib.bandaid.utils.ViewUtil;


/**
 * Created by zy on 2019/4/21.
 */

public abstract class BaseDialogFrg extends DialogFragment {

    protected Context context;

    private int layoutId;

    private View layout;

    MaterialDialog dialog;
    /**
     * 对话框计数器
     */
    Integer dialogCount = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    protected void adjustActivitySize(float w, float h) {
        Dialog dialog = getDialog();
        if (null != dialog) {
            Window window = dialog.getWindow();
            WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
            int width = (int) (MeasureScreen.getScreenWidth(context) * w);
            int height = (int) (MeasureScreen.getScreenHeight(context) * h);
            lp.width = width;
            lp.height = height;
            if (window != null) {
                window.setLayout(lp.width, lp.height);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(layoutId, null);
        initialize();
        registerEvent();
        initClass();
        return layout;
    }

    protected abstract void initialize();

    protected abstract void registerEvent();

    protected abstract void initClass();

    public void setContentView(@LayoutRes int id) {
        this.layoutId = id;
    }

    public <T extends View> T $(int resId) {
        return ViewUtil.findViewById(layout, resId);
    }


    protected void showProgressDialog() {
        if (dialog == null) dialog = MaterialDialogUtil.showLoadProgress(context, "加载中...", true);
        synchronized (dialogCount) {
            dialogCount++;
        }
        if (dialogCount == 1 && !dialog.isShowing() && !isHidden()) {
            dialog.show();
        }
    }

    protected void stopProgressDialog() {
        synchronized (dialogCount) {
            if (dialogCount > 0) dialogCount--;
        }
        if (dialogCount == 0 && dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public BaseDialogFrg show(FragmentManager manager) {
        super.show(manager, "");
        return this;
    }
}
