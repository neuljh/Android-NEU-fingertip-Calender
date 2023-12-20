package com.example.finalwork2.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.example.finalwork2.R;


//终于找到报错“10: 定位服务启动失败”的问题
//这个MapActivity放置的位置有关系，当Activity放在TabSpec中就有问题
//如果单独作为一个activity则没有问题
public class MapActivity extends Activity implements LocationSource,
        AMapLocationListener{
    private MapView mapView;
    private AMap map;

    //定位功能
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapView = (MapView)findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        map = mapView.getMap();

        //修改地图的中心点位置
        CameraPosition cp = map.getCameraPosition();
        CameraPosition cpNew = CameraPosition.fromLatLngZoom(new LatLng(41.8, 123.4), cp.zoom);
        CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cpNew);
        map.moveCamera(cu);
//
//        //初始化定位服务
//        initLocationService();



    }

//    //初始化定位服务,这个地方有错误，这个地方map已经初始化
//    //因此定位的初始化代码就进不去了。
//    private void initLocationService() {
//        if (map != null) {
//            MyLocationStyle locationStyle = new MyLocationStyle();
//            locationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_android_black_24dp));
//            locationStyle.strokeColor(Color.BLACK);
//            locationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));
//            locationStyle.strokeWidth(1.0f);
//            map.setMyLocationStyle(locationStyle);
//            map.setLocationSource(this);
//            map.getUiSettings().setMyLocationButtonEnabled(true);
//            map.setMyLocationEnabled(true);
//        }
//    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        mapView.onPause();
        deactivate();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        mapView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onLocationChanged(AMapLocation amaplocation) {
        // TODO Auto-generated method stub
        if (amaplocation != null && mListener != null) {
            if (amaplocation != null && amaplocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amaplocation);
            }
            else {
                String errText = "failed to locate," + amaplocation.getErrorCode()+ ": "
                        + amaplocation.getErrorInfo();
                Log.e("error",errText);
            }

        }
    }


    @Override
    public void activate(OnLocationChangedListener listener) {
        // TODO Auto-generated method stub
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getApplicationContext());
            mLocationOption = new AMapLocationClientOption();
            mlocationClient.setLocationListener(this);
            mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();
        }
    }


    @Override
    public void deactivate() {
        // TODO Auto-generated method stub
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
        mLocationOption = null;
    }


}