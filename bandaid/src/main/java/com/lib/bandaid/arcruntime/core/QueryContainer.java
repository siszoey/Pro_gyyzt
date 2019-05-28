package com.lib.bandaid.arcruntime.core;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.data.FeatureTable;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.data.ServiceFeatureTable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zy on 2019/5/9.
 */

public class QueryContainer extends BaseContainer {

    public void queryFeatures(String uri, QueryParameters params, final ICallBack iCallBack) {
        try {
            if (iCallBack != null) iCallBack.ready();
            FeatureTable featureTable = arcMap.getTocContainer().getFeatureTableById(uri);
            final ListenableFuture<FeatureQueryResult> future = featureTable.queryFeaturesAsync(params);
            future.addDoneListener(new Runnable() {
                @Override
                public void run() {
                    try {
                        FeatureQueryResult result = future.get();
                        Iterator<Feature> iterator = result.iterator();
                        Feature feature;
                        List<Feature> features = new ArrayList<>();
                        while (iterator.hasNext()) {
                            feature = iterator.next();
                            features.add(feature);
                        }
                        if (iCallBack != null) iCallBack.success(features);
                    } catch (Exception e) {
                        if (iCallBack != null) iCallBack.fail(e);
                    }
                }
            });
        }catch (Exception e){
            if (iCallBack != null) iCallBack.fail(e);
        }
    }

    public void queryRemoteFeatures(String uri, QueryParameters params, final ICallBack iCallBack) {
        if (iCallBack != null) iCallBack.ready();
        try {
            ServiceFeatureTable featureTable = arcMap.getTocContainer().getServiceFeatureTableById(uri);
            final ListenableFuture<FeatureQueryResult> future = featureTable.queryFeaturesAsync(params, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
            future.addDoneListener(new Runnable() {
                @Override
                public void run() {
                    try {
                        FeatureQueryResult result = future.get();
                        Iterator<Feature> iterator = result.iterator();
                        Feature feature;
                        List<Feature> features = new ArrayList<>();
                        while (iterator.hasNext()) {
                            feature = iterator.next();
                            features.add(feature);
                        }
                        if (iCallBack != null) iCallBack.success(features);
                    } catch (Exception e) {
                        if (iCallBack != null) iCallBack.fail(e);
                    }
                }
            });
        }catch (Exception e){
            if (iCallBack != null) iCallBack.fail(e);
        }
    }

    public interface ICallBack {
        public void ready();

        public void success(List<Feature> features);

        public void fail(Exception e);
    }
}