package com.titan.gyyzt.layerManage.bean;

import com.esri.arcgisruntime.layers.Layer;

import java.io.Serializable;

public class VectorBean implements Serializable {


    private static final long serialVersionUID = -3041050452532583904L;

    /*图层名称*/
    private String name;
    /*图层url*/
    private String path;
    /*类别 */
    private Enum type;
    /*图层*/
    private Layer layer;
    /*是否显示子目录列表*/
    private boolean visible = false;

    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }


    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Enum getType() {
        return type;
    }

    public void setType(Enum type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return name;
    }
}
