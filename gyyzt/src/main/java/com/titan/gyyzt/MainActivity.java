package com.titan.gyyzt;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.arcgisservices.ArcGISMapServiceSublayerInfo;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISSublayer;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.layers.Layer;
import com.esri.arcgisruntime.layers.SublayerList;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.view.BackgroundGrid;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.titan.baselibrary.treerecyclerview.adpater.TreeRecyclerAdapter;
import com.titan.baselibrary.treerecyclerview.factory.ItemHelperFactory;
import com.titan.baselibrary.treerecyclerview.item.TreeItem;
import com.titan.baselibrary.util.ToastUtil;
import com.titan.drawtool.DrawTool;
import com.titan.gyyzt.base.BasePresenter;
import com.titan.gyyzt.base.IBase;
import com.titan.gyyzt.base.permission.PermissionsChecker;
import com.titan.gyyzt.base.permission.PermissionsData;
import com.titan.gyyzt.comment.Constant;
import com.titan.gyyzt.comment.adapter.recycle.BaseRecycleAdapter;
import com.titan.gyyzt.comment.decoration.DividerItemDecoration;
import com.titan.gyyzt.layerManage.LayerType;
import com.titan.gyyzt.layerManage.adapter.LayerManageAdapter;
import com.titan.gyyzt.layerManage.bean.LayerManageBean;
import com.titan.gyyzt.layerManage.bean.SublayerBean;
import com.titan.gyyzt.layerManage.bean.VectorBean;
import com.titan.gyyzt.layerManage.item.vector.VectorGroupItem;
import com.titan.gyyzt.layerManage.item.vector.VectorItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IBase, LocationDisplay.LocationChangedListener,
        View.OnClickListener, BaseRecycleAdapter.IViewClickListener {

    @BindView(R.id.layermanage_close)
    ImageView layermanageClose;
    @BindView(R.id.baselayer_ctv)
    CheckedTextView baselayerCtv;
    @BindView(R.id.baselayer_location)
    ImageView baselayerLocation;
    @BindView(R.id.mapview)
    MapView mapview;
    @BindView(R.id.tv_menu)
    TextView tvMenu;
    @BindView(R.id.tucengkongzhi)
    TextView tucengkongzhi;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.baselayer_image)
    CheckedTextView baselayerImage;
    private Context _context;
    private RelativeLayout rLayoutLeft;
    private RelativeLayout rLayoutRight;
    private RecyclerView imageRecyc, vectorRecyc;

    private ArrayList<LayerManageBean> imageLayers = new ArrayList<>();
    private LayerManageAdapter imageLayerAdapter = null;

    private ArrayList<VectorBean> vectorBeans = new ArrayList<>();
    //private LayerManageAdapter vectorLayerAdapter = null;


    private LocationDisplay _locDisplay;
    private boolean isFirst = true;

    private BasePresenter presenter;

    private DrawTool drawTool;
    private Point mappoint, gpspoint;

    //数据集合
    private TreeRecyclerAdapter vectorAdapter = new TreeRecyclerAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();

        initData();

        addLayer();

        //checkLocationPermission();

        initImageRecycView();

        initVectorLayer();

    }


    private void initView() {
        _context = MainActivity.this;
        rLayoutLeft = findViewById(R.id.rl_left);
        rLayoutRight = findViewById(R.id.rl_right);


        imageRecyc = findViewById(R.id.recyc_image);
        vectorRecyc = findViewById(R.id.recyc_vector);
    }

    private void initData() {
        presenter = new BasePresenter(this);
        drawTool = new DrawTool(mapview, mapview.getSpatialReference());

        //去除licensed for developer use。。。。 水印
        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud8065403504,none,RP5X0H4AH7CLJ9HSX018");
        //隐藏网格
        BackgroundGrid mainBackgroundGrid = new BackgroundGrid();
        mainBackgroundGrid.setColor(0xffffffff);
        mainBackgroundGrid.setGridLineColor(0xffffffff);
        mainBackgroundGrid.setGridLineWidth(0);
        mapview.setBackgroundGrid(mainBackgroundGrid);

        mapview.setAttributionTextVisible(false);

    }

    private void addLayer() {
        presenter.addBaseLayer();
    }

    private void initArcgisLocation() {
        // 当前位置
        _locDisplay = mapview.getLocationDisplay();
        // 设置显示的位置
        _locDisplay.setNavigationPointHeightFactor(0.5f);
        // 定位显示
        _locDisplay.startAsync();
        // 设置位置变化监听
        _locDisplay.addLocationChangedListener(this);

    }

    @Override
    public Context getContext() {
        return _context;
    }

    @Override
    public MapView getMapView() {
        return mapview;
    }


    @Override
    public void onLocationChanged(LocationDisplay.LocationChangedEvent event) {
        mappoint = _locDisplay.getMapLocation();
        gpspoint = _locDisplay.getLocation().getPosition();

        if (isFirst) {
            mapview.setViewpointCenterAsync(mappoint, Constant.SCALE);
            isFirst = false;
        }

    }


    private void checkLocationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionsChecker permissionsChecker = new PermissionsChecker(this);
            // 缺少权限时, 进入权限配置页面
            if (permissionsChecker.lacksPermissions(PermissionsData.LOCATION)) {
                ActivityCompat.requestPermissions(this, PermissionsData.LOCATION, PermissionsData.LOCATION_CODE);
            } else {
                initArcgisLocation();
            }
        } else {
            initArcgisLocation();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case PermissionsData.LOCATION_CODE:
                    initArcgisLocation();
                    break;
            }
        }
    }

    @OnClick({R.id.tv_menu, R.id.tucengkongzhi, R.id.layermanage_close, R.id.baselayer_location,
            R.id.baselayer_ctv, R.id.isearche, R.id.ceju, R.id.cemian, R.id.mylocation,R.id.baselayer_image})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.baselayer_image:
                baselayerImage.toggle();
                if(baselayerImage.isChecked()){
                    imageRecyc.setVisibility(View.VISIBLE);
                }else{
                    imageRecyc.setVisibility(View.GONE);
                }

                break;
            case R.id.isearche:
                break;
            case R.id.ceju:
                break;
            case R.id.cemian:
                break;
            case R.id.mylocation:
                if (mappoint != null) mapview.setViewpointCenterAsync(mappoint);

                break;
            case R.id.tv_menu:
                //左侧滑  出菜单
                if (drawerLayout.isDrawerOpen(rLayoutLeft)) {
                    drawerLayout.closeDrawer(rLayoutLeft);
                } else {
                    drawerLayout.openDrawer(rLayoutLeft);
                }

                break;
            case R.id.tucengkongzhi:
                //侧滑  图层控制
                if (drawerLayout.isDrawerOpen(rLayoutRight)) {
                    drawerLayout.closeDrawer(rLayoutRight);
                } else {
                    drawerLayout.openDrawer(rLayoutRight);
                }
                break;
            case R.id.layermanage_close:
                if (drawerLayout.isDrawerOpen(rLayoutRight)) {
                    drawerLayout.closeDrawer(rLayoutRight);
                }
                break;
            case R.id.baselayer_location:
                if (mapview.getMap().getBasemap().getBaseLayers().get(0).getFullExtent() != null) {
                    mapview.setViewpointGeometryAsync(mapview.getMap().getBasemap().getBaseLayers().get(0).getFullExtent());
                }
                break;
            case R.id.baselayer_ctv:
                baselayerCtv.toggle();
                mapview.getMap().getBasemap().getBaseLayers().get(0).setVisible(baselayerCtv.isChecked());
                break;
        }
    }


    private void initImageRecycView() {
        String[] images = _context.getResources().getStringArray(R.array.imglayerUrl);
        imageLayers = new ArrayList<>();
        for (String key : images) {
            LayerManageBean bean = new LayerManageBean();
            bean.setName(key.split(",")[0]);
            bean.setPath(key.split(",")[1]);
            bean.setType(LayerType.IMAGE);
            imageLayers.add(bean);
        }
        imageLayerAdapter = new LayerManageAdapter(imageRecyc);
        imageLayerAdapter.appendList(imageLayers);
        LinearLayoutManager layout = new LinearLayoutManager(_context);
        //设置布局管理器
        imageRecyc.setLayoutManager(layout);
        //设置adapter
        imageRecyc.setAdapter(imageLayerAdapter);
        //添加分割线
        imageRecyc.addItemDecoration(new DividerItemDecoration(_context,
                DividerItemDecoration.VERTICAL_LIST));

        imageLayerAdapter.setIViewClickListener(this);
    }

    /*加载矢量数据*/
    private void initVectorLayer() {
        String[] vectors = _context.getResources().getStringArray(R.array.verctor_online_layer);
        for (String key : vectors) {
            VectorBean bean = new VectorBean();
            bean.setName(key.split(",")[0]);
            bean.setPath(key.split(",")[1]);
            bean.setType(LayerType.VECTOR);
            vectorBeans.add(bean);
        }
        //vectorLayerAdapter = new LayerManageAdapter(vectorRecyc);
        //vectorLayerAdapter.appendList(vectorLayers);
        LinearLayoutManager layout = new LinearLayoutManager(_context);
        //设置布局管理器
        vectorRecyc.setLayoutManager(layout);
        //设置adapter
        vectorRecyc.setAdapter(vectorAdapter);
        //添加分割线
        vectorRecyc.addItemDecoration(new DividerItemDecoration(_context,
                DividerItemDecoration.VERTICAL_LIST));


        final List<TreeItem> groupItem = ItemHelperFactory.createItems(vectorBeans, VectorGroupItem.class, null);
        vectorAdapter.getItemManager().replaceAllItem(groupItem);
        vectorAdapter.notifyDataSetChanged();


        vectorAdapter.setOnItemClickListener((viewHolder, position) -> {
            //因为外部和内部会冲突

            //因为外部和内部会冲突
            final TreeItem item = vectorAdapter.getData(position);
            if (item instanceof VectorGroupItem) {

                VectorBean bean = vectorBeans.get(position);
                boolean flag = bean.isVisible();

                if(flag){
                    ((VectorGroupItem) item).setExpand(false);
                    bean.setVisible(false);
                }else{
                    String url = bean.getPath();
                    final ArcGISMapImageLayer layer = new ArcGISMapImageLayer(url);
                    mapview.getMap().getOperationalLayers().add(layer);

                    layer.setVisible(false);
                    bean.setLayer(layer);

                    layer.addDoneLoadingListener(new Runnable() {
                        @Override
                        public void run() {
                            LoadStatus loadStatus = layer.getLoadStatus();
                            if (loadStatus == LoadStatus.LOADED) {

                                bean.setVisible(true);

                                ArrayList<SublayerBean> list = new ArrayList<>();

                                SublayerList sublayers = layer.getSublayers();
                                for (ArcGISSublayer sublayer : sublayers) {
                                    SublayerBean sublayerBean = new SublayerBean();
                                    sublayerBean.setName(sublayer.getName());
                                    sublayerBean.setVisible(sublayer.isVisible());
                                    sublayerBean.setLayer(sublayer);
                                    list.add(sublayerBean);
                                }

                                List<TreeItem> childItems = ItemHelperFactory.createItems(list, VectorItem.class, (VectorGroupItem) item);
                                ((VectorGroupItem) item).setExpand(true);
                                ((VectorGroupItem) item).setChild(childItems);

                                Log.e("=============", childItems.size() + "");
                            } else if (loadStatus == LoadStatus.FAILED_TO_LOAD) {
                                Log.e("=============", "图层加载失败");
                            }
                        }
                    });
                }
            }

        });

    }


    @Override
    public void onClick(View view, Object data, int position) {
        final LayerManageBean bean = (LayerManageBean) data;
        if (bean.getType() == LayerType.IMAGE) {
            switch (view.getId()) {
                case R.id.layer_ctv:
                    CheckedTextView textView = (CheckedTextView) view;
                    textView.toggle();
                    bean.setVisible(textView.isChecked());
                    if (textView.isChecked()) {
                        String url = bean.getPath();
                        ArcGISTiledLayer imageLayer = new ArcGISTiledLayer(url);
                        mapview.getMap().getOperationalLayers().add(imageLayer);
                        mapview.getMap().getBasemap().getBaseLayers().get(0).setVisible(false);
                        imageLayerAdapter.notifyDataSetChanged();
                        bean.setLayer(imageLayer);
                    } else {
                        mapview.getMap().getOperationalLayers().remove(bean.getLayer());
                        bean.setLayer(null);
                    }

                    break;
                case R.id.layer_location:

                    zoomTolayer(bean);
                    break;
            }
        } else if (bean.getType() == LayerType.VECTOR) {
            switch (view.getId()) {
                case R.id.layer_ctv:
                    CheckedTextView textView = (CheckedTextView) view;
                    textView.toggle();
                    bean.setVisible(textView.isChecked());
                    if (textView.isChecked()) {
                        String url = bean.getPath();
                        final ArcGISMapImageLayer mapImageLayer = new ArcGISMapImageLayer(url);
                        // Add a listener that is invoked when layer loading has completed.
                        mapImageLayer.addDoneLoadingListener(new Runnable() {
                            @Override
                            public void run() {
                                if (mapImageLayer.getLoadStatus() == LoadStatus.LOADED) {
                                    // work with map service info here
                                    SublayerList sublayers = mapImageLayer.getSublayers();
                                    for (ArcGISSublayer sublayer : sublayers) {
                                        String name = sublayer.getName();
                                        Log.e("=====", name);
                                        sublayer.setVisible(true);
                                    }
                                }
                            }
                        });
                        bean.setLayer(mapImageLayer);
                        mapview.getMap().getOperationalLayers().add(mapImageLayer);
                        mapImageLayer.setVisible(true);

                    } else {
                        mapview.getMap().getOperationalLayers().remove(bean.getLayer());
                        bean.setLayer(null);
                    }

                    break;
                case R.id.layer_location:

                    zoomTolayer(bean);
                    break;
            }
        }
    }

    /**
     * 缩放到图层
     */
    public void zoomTolayer(LayerManageBean bean) {
        if (bean.isVisible()) {
            if (bean.getLayer().getFullExtent() != null) {
                mapview.setViewpointGeometryAsync(bean.getLayer().getFullExtent());
            }

            if (drawerLayout.isDrawerOpen(rLayoutRight)) {
                drawerLayout.closeDrawer(rLayoutRight);
            }

        } else {
            ToastUtil.setToast(_context, "图层未加载");
        }
    }

    /**
     * 缩放到图层
     */
    public void zoomTolayer(SublayerBean bean,int position) {
        if (bean == null) {
            ToastUtil.setToast(_context, "图层未加载");
            return;
        }

        if (bean.isVisible()) {
            ArcGISMapServiceSublayerInfo sublayerInfo = bean.getLayer().getMapServiceSublayerInfo();

            if (sublayerInfo != null) {
                Envelope extent = sublayerInfo.getExtent();
                if(extent != null){
                    mapview.setViewpointGeometryAsync(extent);
                }
            }

            if (drawerLayout.isDrawerOpen(rLayoutRight)) {
                drawerLayout.closeDrawer(rLayoutRight);
            }

        } else {
            ToastUtil.setToast(_context, "图层未加载");
        }
    }

    public void addVectorLayer(View view, SublayerBean bean,int position) {

        CheckedTextView checkedTextView = (CheckedTextView) view;
        checkedTextView.toggle();
        boolean flag = checkedTextView.isChecked();

        bean.setVisible(flag);
        bean.getLayer().setVisible(flag);

    }



}
