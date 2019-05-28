package com.lib.bandaid.arcruntime.core;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.esri.arcgisruntime.layers.Layer;
import com.lib.bandaid.utils.ViewUtil;
import com.lib.bandaid.R;

import java.util.List;

/**
 * Created by zy on 2019/5/10.
 */

public class WidgetContainer extends BaseContainer implements View.OnClickListener {

    private ViewGroup rootView;

    private IHandle iHandle;


    @Override
    public void create(ArcMap arcMap) {
        super.create(arcMap);
        rootView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.band_aid_core_widget_layout, null);
        arcMap.removeView(rootView);
        arcMap.addView(rootView);


    }


    @Override
    public void ready(List<Layer> layers) {
        super.ready(layers);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void onClick(View v) {

    }

    public interface IHandle {
        public void handle(int id);
    }

    public void setHandle(IHandle iHandle) {
        this.iHandle = iHandle;
    }

    public <T extends View> T $(int resId) {
        return ViewUtil.findViewById(rootView, resId);
    }
}
