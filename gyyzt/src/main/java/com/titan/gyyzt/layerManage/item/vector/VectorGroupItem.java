package com.titan.gyyzt.layerManage.item.vector;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.titan.baselibrary.treerecyclerview.base.ViewHolder;
import com.titan.baselibrary.treerecyclerview.factory.ItemHelperFactory;
import com.titan.baselibrary.treerecyclerview.item.TreeItem;
import com.titan.baselibrary.treerecyclerview.item.TreeSelectItemGroup;
import com.titan.gyyzt.R;
import com.titan.gyyzt.layerManage.bean.VectorBean;

import java.util.List;

/**
 * Created by a123 on 2018/6/5.
 */

public class VectorGroupItem<T> extends TreeSelectItemGroup<T> {

    @Override
    public int getLayoutId() {
        return R.layout.item_layer_group;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder) {
        viewHolder.setChecked(R.id.layer_ctv, isChildSelect());
        viewHolder.setText(R.id.layer_item,((VectorBean)getData()).toString());

        /*viewHolder.setOnClickListener(R.id.layer_ctv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                ((MainActivity) viewHolder.itemView.getContext()).zoomTolayer(position);
            }
        });*/
    }

    @Nullable
    @Override
    protected List<TreeItem> initChildList(T data) {
        return ItemHelperFactory.createTreeItemList(getChild(), VectorItem.class, this);
    }

    @Override
    public void onClick(ViewHolder viewHolder) {
        super.onClick(viewHolder);
    }


    @Override
    public boolean onInterceptClick(TreeItem child) {
        return super.onInterceptClick(child);
    }
}
