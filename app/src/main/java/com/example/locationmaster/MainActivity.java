package com.example.locationmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.zip.DeflaterOutputStream;

/*
*위치 관리자(LocationManager) : 시스템 서비스가 관리함
*android.location 패키지에는 이 클래스를 포함하여 위치 정보를 확인하거나 확인된 위치 정보를 사용하는데 필요한 클래스들이 정의되어있음
*위치 리스너(Location Listener) : 위치 관리자에서 전달하는 위치 정보를 받기 위해 정의된 인터페이스

*위치 관리자에게서 위치 정보를 요청함으로써 나의 현재 위치를 확인할 수 있음
-1. 위치 관리자 객체 참조 : 시스템 서비스로 제공되므로, getSystemService()로 위치 관리자 객체르르 참조함
-2. 위치 리스너 구현 : 리스너를 구현하여 위치 관리자에게 전달 받은 현재 위치 정보를 처리함
-3. 위치 정보 업데이트 요청 : 위치 정보가 변경될 때마다 requestLocationUpdates()로 위치 관리자에게 알려달라고 요청함(이 메소드의 파라미터로는 위치 리스너 객체를 전달)
-4. 매니페스트에 권한 추가 : GPS를 사용할 수 있도록 매니페스트 파일에 ACCESS_FINE_LOCATION 권한을 추가하고 위험권한을 위한 설정과 코드를 추가함
*/

public class MainActivity extends AppCompatActivity  {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.tv);
        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLocationService();
            }
        });
    }

    public void MyLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); // 1. 위치 관리자 객체 참조

        try {
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER); // getLastKnownLocation() : 최근 위치 정보 확인, GPS_provider : 위치 제공자
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String msg = "최근 나의 위치 -> 위도 : " + latitude + "\n경도 : " + longitude;

                tv.setText(msg);
            }

            // 3. 위치 정보 업데이터 요청
            GPSListener gpsListener = new GPSListener(); // 리스너 객체 생성
            // 10초마다 내 위치 정보를 전달받게됨
            long minTime = 10000; // 최소 시간 10초
            float minDistance = 0; // 최소 거리 0

            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener); // 위치 요청
            Toast.makeText(getApplicationContext(), "내 위치 확인 요청함. ", Toast.LENGTH_SHORT).show();

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    class GPSListener implements LocationListener { // 2. 위치 리스너 구현

        @Override
        public void onLocationChanged(@NonNull Location location) { // 위치가 확인 되었을 때 그 정보를 받아서 처리
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            String msg = "내 위치 -> 위도 : " + latitude + "\n경도 : " + longitude;
            tv.setText(msg);
        }
    }
}