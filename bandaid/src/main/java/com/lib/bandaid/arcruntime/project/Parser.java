package com.lib.bandaid.arcruntime.project;

import com.esri.arcgisruntime.layers.Layer;
import com.esri.arcgisruntime.layers.LayerContent;

import java.util.List;

/**
 * Created by zy on 2019/5/27.
 */

public class Parser {
    LayerNode _layerNode;

    public Parser(Layer layer) {
        this._layerNode = new LayerNode();
        iteration(layer);
    }

    private void iteration(Layer layer) {
        List<LayerContent> list = layer.getSubLayerContents();
        LayerContent content;

        _layerNode.setName(layer.getName());
        _layerNode.setId(layer.getId());
        _layerNode.setLayerContent(layer);
        LayerNode layerNode;
        for (int i = 0; i < list.size(); i++) {
            content = list.get(i);
            layerNode = new LayerNode();
            _layerNode.addNode(layerNode);
            layerNode.setName(content.getName());
            if (content.getSubLayerContents().size() == 0) {
                layerNode.setLayerContent(content);
                continue;
            } else {
                layerNode.setLayerContent(content);
                iteration(content, layerNode);
            }
        }
    }

    private void iteration(LayerContent content, LayerNode _layerNode) {
        LayerNode layerNode;
        List<LayerContent> list = content.getSubLayerContents();
        for (int i = 0; i < list.size(); i++) {
            content = list.get(i);
            layerNode = new LayerNode();
            layerNode.setName(content.getName());
            //layerNode.setId(content.getId());
            _layerNode.addNode(layerNode);
            if (content.getSubLayerContents().size() == 0) {
                layerNode.setLayerContent(content);
                continue;
            } else {
                iteration(content, layerNode);
            }
        }
    }

    public LayerNode getLayerNode() {
        return _layerNode;
    }
}
