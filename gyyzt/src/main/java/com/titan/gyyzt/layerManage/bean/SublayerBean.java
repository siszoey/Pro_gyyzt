package com.titan.gyyzt.layerManage.bean;

import com.esri.arcgisruntime.layers.ArcGISSublayer;
import com.esri.arcgisruntime.layers.Layer;

import java.io.Serializable;

public class SublayerBean implements Serializable {

    private static final long serialVersionUID = 816342309388706308L;
    private String name;
    private boolean visible = false;
    private ArcGISSublayer layer;


    public ArcGISSublayer getLayer() {
        return layer;
    }

    public void setLayer(ArcGISSublayer layer) {
        this.layer = layer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }



    @Override
    public String toString() {
        return this.name;
    }
}
