package com.titan.gyyzt.map.apt;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.esri.arcgisruntime.data.Feature;
import com.lib.bandaid.arcruntime.project.LayerNode;
import com.lib.bandaid.arcruntime.util.FeatureTaker;
import com.titan.gyyzt.R;
import com.titan.gyyzt.comment.adapter.recycle.BaseRecycleAdapter;
import com.titan.gyyzt.comment.adapter.recycle.BaseViewHolder;

/**
 * Created by zy on 2019/5/30.
 */

public class SelFeatureApt extends BaseRecycleAdapter<FeatureTaker<LayerNode>, BaseViewHolder<FeatureTaker<LayerNode>>> {

    public SelFeatureApt(@Nullable RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    public BaseViewHolder<FeatureTaker<LayerNode>> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(parent, R.layout.map_apt_sel_feature);
    }

    class Holder extends BaseViewHolder<FeatureTaker<LayerNode>> implements View.OnClickListener {

        TextView tvName;

        public Holder(ViewGroup parent, int resId) {
            super(parent, resId);
            tvName = getView(R.id.tvName);
            tvName.setOnClickListener(this);
        }

        @Override
        public void setData(FeatureTaker<LayerNode> data, int position) {
            if (data.getFeature().getAttributes().get("OBJECTID") != null) {
                LayerNode node = data.getData();
                Feature feature = data.getFeature();
                tvName.setText(node.getName() + "_" + feature.getAttributes().get("OBJECTID").toString());
            }
        }

        @Override
        public void onClick(View v) {
            if (iViewClickListener != null) iViewClickListener.onClick(v, data, position);
        }
    }
}
