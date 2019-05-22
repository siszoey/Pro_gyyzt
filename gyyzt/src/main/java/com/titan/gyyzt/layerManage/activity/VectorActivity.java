package com.titan.gyyzt.layerManage.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.esri.arcgisruntime.layers.ArcGISSublayer;
import com.esri.arcgisruntime.layers.SublayerList;
import com.titan.gyyzt.R;
import com.titan.gyyzt.comment.adapter.recycle.BaseRecycleAdapter;
import com.titan.gyyzt.comment.decoration.DividerItemDecoration;
import com.titan.gyyzt.layerManage.LayerType;
import com.titan.gyyzt.layerManage.adapter.LayerManageAdapter;
import com.titan.gyyzt.layerManage.bean.LayerManageBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VectorActivity extends AppCompatActivity implements BaseRecycleAdapter.IViewClickListener {

    @BindView(R.id.rv_vector)
    RecyclerView rvVector;

    private Context _context;

    SublayerList sublayerList;

    ArrayList<LayerManageBean> layerManageBeans = new ArrayList<>();
    private LayerManageAdapter vectorAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector);
        ButterKnife.bind(this);
        _context = VectorActivity.this;

        initImageRecycView();

    }

    private void initImageRecycView() {
        for (ArcGISSublayer sublayer:sublayerList) {
            LayerManageBean bean = new LayerManageBean();
            bean.setName(sublayer.getName());
            bean.setType(LayerType.VECTOR);
            layerManageBeans.add(bean);
        }
        vectorAdapter = new LayerManageAdapter(rvVector);
        vectorAdapter.appendList(layerManageBeans);
        LinearLayoutManager layout = new LinearLayoutManager(_context);
        //设置布局管理器
        rvVector.setLayoutManager(layout);
        //设置adapter
        rvVector.setAdapter(vectorAdapter);
        //添加分割线
        rvVector.addItemDecoration(new DividerItemDecoration(_context,
                DividerItemDecoration.VERTICAL_LIST));

        vectorAdapter.setIViewClickListener(this);
    }

    @Override
    public void onClick(View view, Object data, int position) {

    }
}
