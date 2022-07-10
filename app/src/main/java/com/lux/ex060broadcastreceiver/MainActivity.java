package com.lux.ex060broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //이 예제는 브로드캐스트 리시버에 대한 수신 연습을 위해서 만드는 예제
    //원래는 OS[운영체제 : android]가 방송(즉, Broadcast)을 해야 함
    //이 앱에서는 버튼을 눌러서 방송[Broadcast]을 직접 해보고 이를 수신해보기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(view -> {
            //방송 보내기 [4대 컴포넌트 중 Activity, Service, BroadcastReceiver 는 모두 Intent로 실행함

            //BroadcastReceiver를 상속하는 클래스 설계 [MyReceiver]
            //4대 컴포넌트는 반드시 AndroidManifest.xml에 등록해야만 함.

            //1. 명시적 Intent - 직접 Receiver를 지정하여 방송하는 방식
            //Intent intent=new Intent(this,MyReceiver.class);
            //sendBroadcast(intent);
            //명시적 Intent는 같은 앱안에 있는 리시버만 방송을 들을 수 있음.

            //2. 묵시적(암시적) Intent
            //Oreo(api 26) 버전부터 암시적 intent는 오직 시스템 인텐트만 가능함.[OS만 방송할 수 있는 인텐트]
            //단, 그럼에도 꼭 해보고 싶다면 암시적 intent를 듣는 리시버를 동적으로 등록해야만 함.[자바코드로 리시버를 등록]

            //"aaa"라는 이름의 방송을 송출 - 디바이스 안에 있는 모든 앱들이 방송을 들을 수 있음.
            Intent intent=new Intent();
            intent.setAction("aaa");

            sendBroadcast(intent);
        });
    }//onCreate
    MyReceiver receiver;

    //이 액티비티가 화면에 보여질때 자동으로 발동하는 라이프사이클 메소드

    @Override
    protected void onResume() {
        super.onResume();

        //"aaa"라는 암시적 인텐트 방송을 수신하는 리시버 동적 등록
        receiver = new MyReceiver();

        IntentFilter filter=new IntentFilter();
        filter.addAction("aaa");

        registerReceiver(receiver,filter);
    }

    //액티비티가 안 보이기 시작할때 자동으로 발동하는 라이프사이클 메소드

    @Override
    protected void onPause() {
        super.onPause();

        //receiver 제거
        unregisterReceiver(receiver);
    }
}