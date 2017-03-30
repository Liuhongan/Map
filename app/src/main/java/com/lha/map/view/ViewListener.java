package com.lha.map.view;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

/**
 * Created by liuhongan on 2017/3/30.
 */

public interface ViewListener {
    void onLocatedSuccess(BDLocation location);
    void onLocatedError();

    MapView getMapView();
    BaiduMap getBaiduMap();
}
