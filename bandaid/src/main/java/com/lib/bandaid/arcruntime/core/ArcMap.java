package com.lib.bandaid.arcruntime.core;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISSublayer;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.layers.Layer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.loadable.LoadStatusChangedEvent;
import com.esri.arcgisruntime.loadable.LoadStatusChangedListener;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.lib.bandaid.arcruntime.event.ArcMapEventDispatch;
import com.lib.bandaid.arcruntime.event.IArcMapEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 2019/5/9.
 */

public class ArcMap extends RelativeLayout implements LoadStatusChangedListener, IContainer {
    private Context context;
    /**
     * 卫星影像地图
     */
    private List<String> baseMapUrls = new ArrayList<>();
    private List<String> featureUrls = new ArrayList<>();
    private List<String> mapServerUrls = new ArrayList<>();

    private TocContainer tocContainer = new TocContainer();
    private QueryContainer queryContainer = new QueryContainer();
    private MapControl mapControl = new MapControl();
    private WidgetContainer widgetContainer = new WidgetContainer();
    private SketchContainer sketchContainer = new SketchContainer();
    private GraphicContainer graphicContainer = new GraphicContainer();
    private ToolContainer toolContainer = new ToolContainer();

    private MapView mapView;

    private IMapReady iMapReady;

    //事件
    private ArcMapEventDispatch arcMapEventDispatch;

    public ArcMap(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ArcMap(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public ArcMap(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    void init() {
        initMap();
        create(this);
    }

    void initMap() {
        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud8065403504,none,RP5X0H4AH7CLJ9HSX018");
        this.removeAllViews();
        this.mapView = new MapView(context);
        this.mapView.setAttributionTextVisible(false);
        this.addView(mapView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.arcMapEventDispatch = new ArcMapEventDispatch(this);
        //注册地图事件
        this.mapView.setOnTouchListener(arcMapEventDispatch);
    }

    void setEvent(IArcMapEvent event) {
        arcMapEventDispatch.setCurIMapEvent(event);
    }

    public void mapLoad(IMapReady iMapReady) {
        this.iMapReady = iMapReady;

        {
            ArcGISMap gisMap = new ArcGISMap();
            if (baseMapUrls == null || baseMapUrls.size() == 0) {
                gisMap.setBasemap(Basemap.createImagery());
            } else {
                ArcGISTiledLayer tiledLayer = new ArcGISTiledLayer(baseMapUrls.get(0));
                gisMap.setBasemap(new Basemap(tiledLayer));
            }
            gisMap.addLoadStatusChangedListener(this);
            mapView.setMap(gisMap);
        }

        {
            if (mapServerUrls == null) return;
            for (int i = 0; i < mapServerUrls.size(); i++) {
                ArcGISMapImageLayer mapImageLayer = new ArcGISMapImageLayer(mapServerUrls.get(i));
                mapView.getMap().getOperationalLayers().add(mapImageLayer);
            }
        }

        {
            if (featureUrls == null) return;
            ServiceFeatureTable featureTable;
            FeatureLayer featureLayer;
            for (int i = 0; i < featureUrls.size(); i++) {
                featureTable = new ServiceFeatureTable(featureUrls.get(i));
                featureLayer = new FeatureLayer(featureTable);
                featureLayer.setId(featureUrls.get(i));
                mapView.getMap().getOperationalLayers().add(featureLayer);
            }
        }
    }

    @Override
    public void loadStatusChanged(LoadStatusChangedEvent loadStatusChangedEvent) {
        if (LoadStatus.LOADED == loadStatusChangedEvent.getNewLoadStatus()) {
            ready(mapView.getMap().getOperationalLayers());
            if (iMapReady != null) iMapReady.onMapReady();
        }
    }

    @Override
    public void create(ArcMap arcMap) {
        tocContainer.create(arcMap);
        queryContainer.create(arcMap);
        mapControl.create(arcMap);
        widgetContainer.create(arcMap);
        sketchContainer.create(arcMap);
        graphicContainer.create(arcMap);
        toolContainer.create(arcMap);
    }

    @Override
    public void ready(List<Layer> layers) {
        tocContainer.ready(layers);
        queryContainer.ready(layers);
        mapControl.ready(layers);
        widgetContainer.ready(layers);
        sketchContainer.ready(layers);
        graphicContainer.ready(layers);
        toolContainer.ready(layers);
    }

    @Override
    public void destroy() {
        tocContainer.destroy();
        queryContainer.destroy();
        mapControl.destroy();
        widgetContainer.destroy();
        sketchContainer.destroy();
        graphicContainer.destroy();
        toolContainer.destroy();
    }

    public void resume() {
        mapView.resume();
    }

    public void pause() {
        mapView.pause();
    }


    @Override
    public void destroyDrawingCache() {
        super.destroyDrawingCache();
        destroy();
    }

    public interface IMapReady {
        public void onMapReady();
    }

    public TocContainer getTocContainer() {
        return tocContainer;
    }

    public QueryContainer getQueryContainer() {
        return queryContainer;
    }

    public MapControl getMapControl() {
        return mapControl;
    }

    public WidgetContainer getWidgetContainer() {
        return widgetContainer;
    }

    public SketchContainer getSketchContainer() {
        return sketchContainer;
    }

    public ToolContainer getToolContainer() {
        return toolContainer;
    }

    public ArcMap setBaseMapUrl(String... urls) {
        if (urls == null) return this;
        for (int i = 0; i < urls.length; i++) {
            baseMapUrls.add(urls[i]);
        }
        return this;
    }

    public ArcMap setFeatureLayerUrl(String... urls) {
        if (urls == null) return this;
        for (int i = 0; i < urls.length; i++) {
            featureUrls.add(urls[i]);
        }
        return this;
    }

    public ArcMap setMapServerUrl(String... urls) {
        if (urls == null) return this;
        for (int i = 0; i < urls.length; i++) {
            mapServerUrls.add(urls[i]);
        }
        return this;
    }


    public MapView getMapView() {
        return mapView;
    }
}