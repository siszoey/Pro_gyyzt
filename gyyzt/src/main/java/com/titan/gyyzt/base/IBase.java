package com.titan.gyyzt.base;

import android.content.Context;

import com.esri.arcgisruntime.mapping.view.MapView;

public interface IBase {

    Context getContext();

    MapView getMapView();
}