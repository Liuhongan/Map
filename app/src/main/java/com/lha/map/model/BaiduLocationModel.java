package com.lha.map.model;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by liuhongan on 2017/3/30.
 */

public class BaiduLocationModel {

    private static BaiduLocationModel mInstance;

    private Context mContext;

    private LocationClient mClient;

    private LocationListener mLocationListener;

    public interface LocationListener{
        void onLocatedSuccess(BDLocation location);
        void onLocatedError();
    }

    private BaiduLocationModel(Context context) {
        mContext = context;
        init();
    }

    public void setLocationListener(LocationListener locationListener) {
        this.mLocationListener = locationListener;
    }

    private void init() {
        BDLocationListener listener = new MyLocationListener();
        mClient = new LocationClient(mContext);

        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setIsNeedLocationDescribe(true);

        mClient.setLocOption(option);
        mClient.registerLocationListener(listener);
    }

    public void startLocate() {
        mClient.start();
    }

    public void stopLocate() {
        mClient.stop();
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (mLocationListener != null) {
                mLocationListener.onLocatedSuccess(bdLocation);
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
            if (mLocationListener != null) {
                mLocationListener.onLocatedError();
            }
        }
    }

    public static synchronized BaiduLocationModel getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new BaiduLocationModel(context);
        }
        return mInstance;
    }

}
