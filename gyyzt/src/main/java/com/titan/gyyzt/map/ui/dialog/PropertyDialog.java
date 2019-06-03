package com.titan.gyyzt.map.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;

import com.lib.bandaid.widget.dialog.BaseDialogFrg;
import com.lib.bandaid.widget.layout.EntityLayoutView;

import java.util.Map;

/**
 * Created by zy on 2019/6/3.
 */

public class PropertyDialog extends BaseDialogFrg {

    public static PropertyDialog newInstance(String title, Map property) {
        PropertyDialog fragment = new PropertyDialog();
        fragment.setProperty(property);
        fragment.setTitle(title);
        return fragment;
    }

    private String title;
    private Map property;
    private EntityLayoutView entityLayoutView;

    private void setProperty(Map property) {
        this.property = property;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle(null, title, Gravity.CENTER);
        w = 0.8f;
        h = 0.8f;
        entityLayoutView = new EntityLayoutView<Map>(getContext());
        setContentView(entityLayoutView);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void registerEvent() {

    }

    @Override
    protected void initClass() {
        entityLayoutView.setData(property).resolutionData();
    }
}
