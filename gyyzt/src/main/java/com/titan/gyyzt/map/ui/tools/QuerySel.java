package com.titan.gyyzt.map.ui.tools;

import android.view.MotionEvent;
import android.view.View;

import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.Point;
import com.lib.bandaid.arcruntime.core.QueryContainer;
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
    }

    @Override
    public boolean isCheckBtn() {
        return false;
    }

    @Override
    public void viewClick(View view) {
        super.viewClick(view);
        DrawDialog.newInstance(new DrawDialog.ICallBack() {

            @Override
            public void callback(Object o) {
                arcMap.getSketchTool().activate((int) o);
            }
        }).show(context);
    }
}
