package com.ayocodetest.activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ayocodetest.R;
import com.ayocodetest.adapter.HistoryAdapter;
import com.ayocodetest.base.BaseActivity;
import com.ayocodetest.misc.DatabaseManager;

import butterknife.BindView;

public class HistoryActivity extends BaseActivity {

    @BindView(R.id.rv_search_history)
    RecyclerView rvSearchHistory;

    HistoryAdapter historyAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        historyAdapter = new HistoryAdapter(this);
        rvSearchHistory.setLayoutManager(new LinearLayoutManager(this));
        rvSearchHistory.setItemAnimator(new DefaultItemAnimator());
        rvSearchHistory.setAdapter(historyAdapter);
        loadHistoryData();
    }

    private void loadHistoryData() {
        historyAdapter.setHistoryDatas(DatabaseManager.getInstance().getSearchHistory());
    }

    @Override
    public int getLayout() {
        return R.layout.content_history;
    }

}
