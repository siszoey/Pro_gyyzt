package com.titan.gyyzt.map.ui.tools;

import android.view.MotionEvent;
import android.view.View;

import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.Point;
import com.lib.bandaid.arcruntime.core.QueryContainer;
import com.lib.bandaid.arcruntime.core.draw.ValueCallback;
import com.lib.bandaid.arcruntime.layer.project.LayerNode;
import com.lib.bandaid.arcruntime.tools.core.BaseTool;
import com.lib.bandaid.arcruntime.util.FeatureTaker;
import com.lib.bandaid.arcruntime.util.FeatureUtil;
import com.titan.drawtool.DrawType;
import com.titan.gyyzt.R;
import com.titan.gyyzt.map.ui.dialog.DrawDialog;
import com.titan.gyyzt.map.ui.frame.FrameQuery;

import java.util.List;

/**
 * Created by zy on 2019/5/28.
 */

public class QuerySel extends BaseTool {

    public QuerySel() {
        id = getClass().getSimpleName();
        name = "查询";
        resId = R.mipmap.tool_map_identify_nomal;
        checkedResId = R.mipmap.tool_map_identify_pressed;
        isRegisterMapEvent = false;
    }

    @Override
    public boolean isCheckBtn() {
        return true;
    }

    @Override
    public void activate() {
        super.activate();

        DrawDialog.newInstance(new DrawDialog.ICallBack() {
            @Override
            public void callback(Object o) {
                arcMap.getSketchTool().setCallBack(new ValueCallback() {
                    @Override
                    public void onSuccess(Object t) {
                        System.out.println(t);
                    }

                    @Override
                    public void onFail(String value) {

                    }

                    @Override
                    public void onGeometry(Geometry geometry) {
                        System.out.println(geometry);
                    }
                });
                arcMap.getSketchTool().activate((int) o);
            }
        }).show(context);
    }

    @Override
    public void deactivate() {
        super.deactivate();
        arcMap.getSketchTool().deactivate();
    }
}
