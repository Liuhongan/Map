package com.lha.map.presenter;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.lha.map.R;
import com.lha.map.model.BaiduLocationModel;
import com.lha.map.view.ViewListener;

/**
 * Created by liuhongan on 2017/3/30.
 */

public class BaiduLocationPresenter implements BaiduLocationModel.LocationListener{

    private ViewListener mViewListener;
    private Context mContext;

    private static final String TAG = "BaiduLocationPresenter";

    public void getUserLocation(Context context) {
        mContext = context;

        BaiduLocationModel model = BaiduLocationModel.getInstance(context);
        model.setLocationListener(this);
        model.startLocate();

    }

    public void setViewListener(ViewListener listener) {
        mViewListener = listener;
    }

    @Override
    public void onLocatedSuccess(BDLocation location) {
        mViewListener.onLocatedSuccess(location);
        BaiduMap baiduMap = mViewListener.getBaiduMap();
        baiduMap.setMyLocationEnabled(true);
        MyLocationData locationData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                .direction(location.getDirection())
                .latitude(location.getLatitude()).longitude(location.getLongitude())
                .build();
        baiduMap.setMyLocationData(locationData);
        BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true,
                null);
        baiduMap.setMyLocationConfigeration(config);
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MapStatus status = new MapStatus.Builder().target(latLng).build();
        MapStatusUpdate update = MapStatusUpdateFactory.newMapStatus(status);
        baiduMap.setMapStatus(update);

        Log.d(TAG, "onLocatedSuccess: latitude = " + location.getLatitude());
        Log.d(TAG, "onLocatedSuccess: longitude = " + location.getLongitude());
    }

    @Override
    public void onLocatedError() {
        mViewListener.onLocatedError();
    }
}
