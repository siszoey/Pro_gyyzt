package com.lib.bandaid.arcruntime.core;

import com.esri.arcgisruntime.data.FeatureTable;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.layers.Layer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.lib.bandaid.arcruntime.project.LayerNode;
import com.lib.bandaid.arcruntime.project.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zy on 2019/5/9.
 */

public class TocContainer extends BaseContainer {

    private List<LayerNode> layerNodes = new ArrayList<>();

    private Map<String, Layer> layers = new HashMap<>();

    private List<ILayerLoaded> layersLoaded = new ArrayList<>();

    @Override
    public void ready(List<Layer> layers) {
        if (layers == null) return;
        addLayers(layers);
        for (final Layer layer : layers) {
            layer.addDoneLoadingListener(new Runnable() {
                @Override
                public void run() {
                    if (layer.getLoadStatus() == LoadStatus.LOADED) {
                        LayerNode node = new Parser(layer).getLayerNode();
                        //node.setVisible(true);
                        addLayerNode(node);
                        notifyLayerLoad(node);
                    }
                }
            });
        }
    }

    void addLayer(Layer layer) {
        if (layer == null) return;
        this.layers.put(layer.getId(), layer);
    }

    void addLayers(List<Layer> layers) {
        if (layers == null) return;
        Layer layer;
        for (int i = 0; i < layers.size(); i++) {
            layer = layers.get(i);
            this.layers.put(layer.getId(), layer);
        }
    }

    public Layer getLayerById(String id) {
        return layers.get(id);
    }

    public FeatureLayer getFeatureLayerById(String id) {
        Layer layer = layers.get(id);
        if (layer == null) return null;
        if (layer instanceof FeatureLayer) return (FeatureLayer) layer;
        return null;
    }

    public FeatureTable getFeatureTableById(String id) {
        Layer layer = layers.get(id);
        if (layer == null) return null;
        if (layer instanceof FeatureLayer) return ((FeatureLayer) layer).getFeatureTable();
        return null;
    }

    public ServiceFeatureTable getServiceFeatureTableById(String id) {
        Layer layer = layers.get(id);
        if (layer == null) return null;
        if (layer instanceof FeatureLayer)
            return (ServiceFeatureTable) ((FeatureLayer) layer).getFeatureTable();
        return null;
    }

    public void addLayerNode(LayerNode node) {
        layerNodes.add(node);
    }

    public interface ILayerLoaded {
        public void iLayerLoaded(LayerNode node);
    }

    public void addILayerLoaded(ILayerLoaded layerLoad) {
        layersLoaded.add(layerLoad);
    }

    public void notifyLayerLoad(LayerNode node) {
        for (ILayerLoaded layerLoad : layersLoaded) {
            layerLoad.iLayerLoaded(node);
        }
    }

}
