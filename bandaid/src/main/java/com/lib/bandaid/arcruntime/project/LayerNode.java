package com.lib.bandaid.arcruntime.project;

import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISSublayer;
import com.esri.arcgisruntime.layers.Layer;
import com.esri.arcgisruntime.layers.LayerContent;
import com.esri.arcgisruntime.layers.SublayerList;
import com.esri.arcgisruntime.util.ListenableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 2019/5/24.
 */

public class LayerNode implements Serializable {

    private Object id;

    private String name;

    private LayerContent layerContent;

    private List<LayerNode> nodes;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LayerContent getLayerContent() {
        return layerContent;
    }

    public void setLayerContent(LayerContent layerContent) {
        this.layerContent = layerContent;
    }

    public List<LayerNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<LayerNode> nodes) {
        this.nodes = nodes;
    }

    public void addNode(LayerNode node) {
        if (this.nodes == null) this.nodes = new ArrayList<>();
        this.nodes.add(node);
    }

    public boolean getVisible() {
        return layerContent == null ? false : layerContent.isVisible();
    }

    public void setVisible(boolean visible) {
        iteration(this, visible);
    }

    //----------------------------------------------------------------------------------------------

    public static void iteration(LayerNode layerNode, boolean visible) {
        if (layerNode == null) return;
        if (layerNode.getLayerContent() != null) {
            System.out.println(layerNode.getName());
            layerNode.getLayerContent().setVisible(visible);
        }
        List<LayerNode> nodes = layerNode.getNodes();
        if (nodes == null) return;
        LayerNode _layerNode;
        for (int i = 0; i < nodes.size(); i++) {
            _layerNode = nodes.get(i);
            iteration(_layerNode, visible);
        }
    }


    public static void iteration(ArcGISMapImageLayer layers) {
        SublayerList list = layers.getSublayers();
        ArcGISSublayer gisSublayer;
        for (int i = 0; i < list.size(); i++) {
            gisSublayer = list.get(i);
            System.out.println("---------" + gisSublayer.getName() + "---------");
            iteration(gisSublayer);
        }
    }


    public static void iteration(ArcGISSublayer gisSublayer) {
        ListenableList<ArcGISSublayer> list = gisSublayer.getSublayers();
        for (int i = 0; i < list.size(); i++) {
            gisSublayer = list.get(i);
            System.out.println(gisSublayer.getName());
            iteration(gisSublayer);
        }
    }
}
