package com.example.locationmaster;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private static final LatLng SEOUL = new LatLng(37.566535, 126.977969);
    private static final LatLng DAEJEON = new LatLng(36.350412, 127.384548);
    private static final LatLng BUSAN = new LatLng(35.179554, 129.075642);

    private Marker seoul;
    private Marker daejeon;
    private Marker busan;

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) { // 지도가 준비되면 호출됨
        googleMap = map;

        seoul = googleMap.addMarker(new MarkerOptions().position(SEOUL).title("서울"));
        seoul.setTag(0);

        daejeon = googleMap.addMarker(new MarkerOptions().position(DAEJEON).title("대전"));
        daejeon.setTag(0);

        busan = googleMap.addMarker(new MarkerOptions().position(BUSAN).title("부산"));
        busan.setTag(0);

        googleMap.setOnMarkerClickListener(this);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL)); // 카메라를 지정한 경도/위도로 이동함
    }


    @Override
    public boolean onMarkerClick(@NonNull Marker marker) { // 마커를 클릭하면 호출됨 -> 클릭하면 발생하는 동작을 작성함(여기서는 간단히 클릭 횟수)
        Integer clickCount = (Integer)marker.getTag();

        if(clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);

            Toast.makeText(this, marker.getTitle() + "이 클릭되었습니다, 클릭 횟수 : " + clickCount, Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}