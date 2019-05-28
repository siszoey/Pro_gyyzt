package com.lib.bandaid.arcruntime.tools;

import android.support.annotation.NonNull;
import android.view.MotionEvent;

import com.lib.bandaid.R;
import com.lib.bandaid.arcruntime.tools.core.BaseTool;

/**
 * Created by zy on 2019/5/28.
 */

public class ToolSel extends BaseTool {

    public ToolSel() {
        id = getClass().getSimpleName();
        name = "选择";
        resId = R.mipmap.tool_map_zoom_in_normal;
        checkedResId = R.mipmap.tool_map_zoom_in_pressed;
    }

    @Override
    public boolean isCheckBtn() {
        return true;
    }

    @Override
    public void activate() {
        super.activate();
        if (isRegisterMapEvent) {
            // arcMap.getSelContainer().deactivate();
        }
    }

    @Override
    public void deactivate() {
        super.deactivate();
        if (isRegisterMapEvent) {
            // arcMap.getSelContainer().deactivate();
        }
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return super.onSingleTapUp(e);
    }

    @Override
    public void onLongPress(MotionEvent e) {
        super.onLongPress(e);
    }
}
