package com.titan.gyyzt.map.ui.frame;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lib.bandaid.arcruntime.core.BaseMapWidget;
import com.lib.bandaid.system.dialog.ATEDialog;
import com.titan.gyyzt.R;
import com.titan.gyyzt.map.ui.aty.MapActivity;
import com.titan.gyyzt.map.ui.dialog.LocalDialog;

/**
 * Created by zy on 2019/5/24.
 */

public class FrameMenu extends BaseMapWidget implements View.OnClickListener {

    TextView tvLayer, tvLoc, tvTrail, tvExit;

    public FrameMenu(Context context) {
        super(context);
        setContentView(R.layout.map_ui_frame_menu);
    }


    @Override
    public void initialize() {
        tvLayer = $(R.id.tvLayer);
        tvLoc = $(R.id.tvLoc);
        tvTrail = $(R.id.tvTrail);
        tvExit = $(R.id.tvExit);
    }

    @Override
    public void registerEvent() {
        tvLayer.setOnClickListener(this);
        tvLoc.setOnClickListener(this);
        tvTrail.setOnClickListener(this);
        tvExit.setOnClickListener(this);
    }

    @Override
    public void initClass() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvLayer) {
            //showMsg("图层");
            ((MapActivity) context).toggle();
        }
        if (v.getId() == R.id.tvLoc) {
            LocalDialog.newInstance(new LocalDialog.ICallBack() {
                @Override
                public void sure(double dLon, double dLat) {
                    arcMap.getMapControl().zoomP(dLon, dLat);
                }
            }).show(context);
        }
        if (v.getId() == R.id.tvTrail) {
            showMsg("轨迹");
        }
        if (v.getId() == R.id.tvExit) {
            new ATEDialog.Theme_Setting(context)
                    .title("提示")
                    .content("确认退出？")
                    .positiveText("退出")
                    .negativeText("取消")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).show();
        }
    }
}
