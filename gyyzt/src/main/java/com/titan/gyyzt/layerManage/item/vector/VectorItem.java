package com.titan.gyyzt.layerManage.item.vector;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.titan.baselibrary.treerecyclerview.base.ViewHolder;
import com.titan.baselibrary.treerecyclerview.item.TreeItem;
import com.titan.baselibrary.treerecyclerview.item.TreeItemGroup;
import com.titan.gyyzt.MainActivity;
import com.titan.gyyzt.R;
import com.titan.gyyzt.layerManage.bean.SublayerBean;


/**
 * Created by a123 on 2018/6/5.
 */

public class VectorItem extends TreeItem<Integer> {


    @Override
    public int getLayoutId() {
        return R.layout.item_layermanage;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder) {
        TreeItemGroup parentItem = getParentItem();
        if (parentItem instanceof VectorGroupItem) {
            viewHolder.setChecked(R.id.layer_ctv, ((VectorGroupItem) parentItem).getSelectItems().contains(this));
        }
        viewHolder.setText(R.id.layer_ctv, getData()+"");

        viewHolder.getView(R.id.layer_ctv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SublayerBean bean = (SublayerBean) getData();
                int position = viewHolder.getAdapterPosition();
                ((MainActivity) viewHolder.itemView.getContext()).addVectorLayer(v,bean,position);
            }
        });

        viewHolder.getView(R.id.layer_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SublayerBean bean = (SublayerBean) getData();
                int position = viewHolder.getAdapterPosition();
                ((MainActivity) viewHolder.itemView.getContext()).zoomTolayer(bean,position);
            }
        });
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, RecyclerView.LayoutParams layoutParams, int position) {
        super.getItemOffsets(outRect, layoutParams, position);
        outRect.bottom = 1;
    }
}
