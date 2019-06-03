package com.titan.gyyzt.comment.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.titan.gyyzt.R;
import com.titan.gyyzt.comment.view.MaterialDialogUtil;

/**
 * Created by zy on 2019/4/22.
 */

public abstract class BaseAppCompatAty extends AppCompatActivity {

    protected Context context;

    Toolbar toolbar;

    MaterialDialog dialog;

    /**
     * 对话框计数器
     */
    Integer dialogCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(setLayoutView());
        initTitle();
        initView();
    }

    private void initTitle() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected abstract void initView();

    public abstract int setLayoutView();


    protected void showProgressDialog() {
        if (dialog == null) dialog = MaterialDialogUtil.showLoadProgress(this, "加载中...", true);
        synchronized (dialogCount) {
            dialogCount++;
        }
        if (dialogCount == 1 && !dialog.isShowing() && !isFinishing()) {
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
}
