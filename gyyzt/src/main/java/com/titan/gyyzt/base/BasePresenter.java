package com.titan.gyyzt.base;

import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.titan.gyyzt.R;

public class BasePresenter {


    private IBase _base;

    public BasePresenter(IBase iBase){
        this._base = iBase;
    }

    public void addBaseLayer(){

        //Basemap basemap = Basemap.createImagery();
        String path = _base.getContext().getResources().getString(R.string.base_url);
        ArcGISTiledLayer tiledLayer = new ArcGISTiledLayer(path);
        ArcGISMap gisMap = new ArcGISMap(new Basemap(tiledLayer));
        _base.getMapView().setMap(gisMap);
    }

}
