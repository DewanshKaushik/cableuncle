package com.example.mscomputers.cableuncle;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.multidex.MultiDex;

import java.util.ArrayList;

import mmsl.DeviceUtility.DeviceBluetoothCommunication;

/**
 * Created by User on 3/7/2017.
 */
public class CableUncleApplication extends Application {
    private static CableUncleApplication instance;

    public boolean isLogin=false;
    public boolean isOnline=false;
    public ArrayList<String> areaList=new ArrayList<>();
    public String selectedArea;
    public String previousBalance;
    public String previousBasics;
    public String previousTotalAmount;

    public DeviceBluetoothCommunication bluetoothCommunication;
    public BluetoothDevice device;

    @Override
    public void onCreate() {
        super.onCreate();
        System.gc();
        System.out.println("Hi");


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    public static synchronized CableUncleApplication getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new CableUncleApplication();
            return instance;
        }
    }

}
