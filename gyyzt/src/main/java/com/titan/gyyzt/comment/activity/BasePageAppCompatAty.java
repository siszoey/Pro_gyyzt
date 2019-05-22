package com.titan.gyyzt.comment.activity;

import android.os.Bundle;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

/**
 * Created by zy on 2019/4/22.
 * 分页列表activity
 */

public abstract class BasePageAppCompatAty extends BaseAppCompatAty {

    TwinklingRefreshLayout refreshLayout;

    protected int pageNumber = 1;

    protected int pageSize = 10;

    protected abstract TwinklingRefreshLayout initTwinklingRefreshLayout();

    protected IRefreshListener iRefreshListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshLayout = initTwinklingRefreshLayout();
        if (refreshLayout != null) refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                refreshLayout.finishRefreshing();
                pageNumber = 1;
                if (iRefreshListener != null) iRefreshListener.onRefresh();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                refreshLayout.finishLoadmore();
                pageNumber++;
                if (iRefreshListener != null) iRefreshListener.onLoadMore();
            }
        });
    }

    public interface IRefreshListener {
        public void onRefresh();

        public void onLoadMore();
    }

}
