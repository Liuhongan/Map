package com.lha.map.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.lha.map.R;
import com.lha.map.presenter.BaiduLocationPresenter;

public class MainActivity extends AppCompatActivity implements ViewListener, View.OnClickListener{

    private static final String TAG = "MainActivity";
    
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    private Button mBtnLocated;

    private BaiduLocationPresenter mBLPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //init BaiduLBS
        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_main);
        initBaiduMap();

        mBtnLocated = (Button) findViewById(R.id.btn_located);
        mBtnLocated.setOnClickListener(this);

        mBLPresenter = new BaiduLocationPresenter();
        mBLPresenter.setViewListener(this);
    }
    
    private void initBaiduMap() {
        mMapView = (MapView) findViewById(R.id.map_view);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLocatedSuccess(BDLocation location) {
        Log.d(TAG, "onLocatedSuccess: " + location.getCity());
    }

    @Override
    public MapView getMapView() {
        return mMapView;
    }

    @Override
    public BaiduMap getBaiduMap() {
        return mBaiduMap;
    }

    @Override
    public void onLocatedError() {
        Log.d(TAG, "onLocatedError: ");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_located) {
            mBLPresenter.getUserLocation(getApplicationContext());
        }
    }
}
