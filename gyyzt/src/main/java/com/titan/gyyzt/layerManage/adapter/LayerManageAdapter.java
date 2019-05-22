package com.titan.gyyzt.layerManage.adapter;


import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import com.titan.gyyzt.R;
import com.titan.gyyzt.comment.adapter.recycle.BaseRecycleAdapter;
import com.titan.gyyzt.comment.adapter.recycle.BaseViewHolder;
import com.titan.gyyzt.layerManage.bean.LayerManageBean;


public class LayerManageAdapter extends BaseRecycleAdapter<LayerManageBean,BaseViewHolder<LayerManageBean>> {


    public LayerManageAdapter(@Nullable RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(parent, R.layout.item_layermanage);
    }

    private class Holder extends BaseViewHolder<LayerManageBean> implements View.OnClickListener {

        CheckedTextView textView;
        ImageView imageView;

        public Holder(ViewGroup parent, int resId) {
            super(parent, resId);
            textView = getView(R.id.layer_ctv);
            textView.setOnClickListener(this);
            imageView = getView(R.id.layer_location);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (iViewClickListener != null) iViewClickListener.onClick(v, data, position);
        }

        @Override
        public void setData(LayerManageBean data, int position) {
            textView.setText(data.getName());
        }
    }
}
