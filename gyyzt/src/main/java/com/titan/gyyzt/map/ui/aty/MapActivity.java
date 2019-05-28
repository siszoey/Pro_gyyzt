package com.titan.gyyzt.map.ui.aty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lib.bandaid.activity.BaseActivity;
import com.lib.bandaid.arcruntime.core.ArcMap;
import com.lib.bandaid.arcruntime.core.ToolContainer;
import com.lib.bandaid.arcruntime.tools.ToolSel;
import com.lib.bandaid.arcruntime.tools.ZoomIn;
import com.lib.bandaid.arcruntime.tools.ZoomOut;
import com.lib.bandaid.widget.base.EGravity;
import com.lib.bandaid.widget.drag.CustomDrawerLayout;
import com.titan.gyyzt.R;
import com.titan.gyyzt.map.ui.frame.FrameLayer;
import com.titan.gyyzt.map.ui.frame.FrameMenu;

/**
 * Created by zy on 2019/5/23.
 */

public class MapActivity extends BaseActivity implements ArcMap.IMapReady {


    CustomDrawerLayout drawerLayout;
    LinearLayout menuRight, menuLeft;
    FrameLayout mainFrame;
    FrameLayer frameLayer;
    FrameMenu frameMenu;
    ArcMap arcMap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ToolContainer.registerTool("辅助", EGravity.LEFT_BOTTOM, ZoomIn.class, ZoomOut.class);
        ToolContainer.registerTool("通用", EGravity.RIGHT_BOTTOM, ToolSel.class);


        setContentView(R.layout.map_ui_aty_map);
    }

    @Override
    protected void initialize() {
        arcMap = $(R.id.arcMap);
        drawerLayout = $(R.id.drawerLayout);
        drawerLayout.setMargin(0.618f);
        menuRight = $(R.id.menuRight);
        menuLeft = $(R.id.menuLeft);
        mainFrame = $(R.id.mainFrame);

        frameMenu = new FrameMenu(this);
        frameLayer = new FrameLayer(this);
        frameMenu.create(arcMap);
        frameLayer.create(arcMap);
        menuLeft.addView(frameMenu.getView());
        menuRight.addView(frameLayer.getView());
    }

    @Override
    protected void registerEvent() {

    }

    @Override
    protected void initClass() {

    }


    @Override
    public void onMapReady() {
    }

    public void toggle() {
        drawerLayout.toggle();
    }
}
