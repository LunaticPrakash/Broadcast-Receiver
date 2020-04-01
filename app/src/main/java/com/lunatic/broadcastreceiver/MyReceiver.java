package com.lunatic.broadcastreceiver;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;


public class MyReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        String TAG = "Prakash";
        BluetoothDevice bluetoothDevice;

        //when device completely booted
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Toast.makeText(context, "Boot Completed", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Boot Completed");
        }


        //when screen is off
        if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
            Toast.makeText(context, "Screen Off", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Screen Off");

        }

        //when screen is on
        if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
            Toast.makeText(context, "Screen Onn", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Screen Onn");

        }

        //when device is unlocked
        if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
            Toast.makeText(context, "Device is unlocked", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Device is unlocked");
        }

        //when device is locked
        if (!Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
            Toast.makeText(context, "Device is locked", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Device is locked");
        }

        //when charging is on or some power source is connected
        if (Intent.ACTION_POWER_CONNECTED.equals(intent.getAction())) {
            Toast.makeText(context, "Power is Connected", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Power is Connected");
        }

        //when charging is removed or some power source is disconnected
        if (Intent.ACTION_POWER_DISCONNECTED.equals(intent.getAction())) {
            Toast.makeText(context, "Power is Disconnected", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Power is Disconnected");
        }

        //when battery is low
        if (Intent.ACTION_BATTERY_LOW.equals(intent.getAction())) {
            Toast.makeText(context, "Batter is Low", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Battery is Low");
        }

        //when headphone or earphone is plugged in
        if (Intent.ACTION_HEADSET_PLUG.equals(intent.getAction())) {
            Toast.makeText(context, "Headphone is plugged in", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Headphone is plugged in");
        }


        //when internet is connected
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                Toast.makeText(context, "Internet is connected", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Internet is connected");
            } else if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.DISCONNECTED) {
                Toast.makeText(context, "No internet :(", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "No internet :(");
            }
        }

        //when bluetooth state changed
        if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction())) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
            if (state == BluetoothAdapter.STATE_OFF) {
                Toast.makeText(context, "Bluetooth is off", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Bluetooth is off");
            }
            if (state == BluetoothAdapter.STATE_TURNING_OFF) {
                Toast.makeText(context, "Bluetooth is turning off", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Bluetooth is turning off");
            }
            if (state == BluetoothAdapter.STATE_TURNING_ON) {
                Toast.makeText(context, "Bluetooth is turning on", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Bluetooth is turning on");
            }

            if (state == BluetoothAdapter.STATE_ON) {
                Log.d(TAG, "Bluetooth is on");
                Toast.makeText(context, "Bluetooth is on", Toast.LENGTH_SHORT).show();
            }

        }

            //when bluetooth is connected
            if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(intent.getAction())) {
                bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    Toast.makeText(context, "Connected to " + bluetoothDevice.getName(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Connected to " + bluetoothDevice.getName());
            }


            //when bluetooth is disconnected
            if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(intent.getAction())) {
                bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    Toast.makeText(context, "Disconnected from " + bluetoothDevice.getName(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Disconnected from " + bluetoothDevice.getName());
            }


            //when GPS is toggled
            if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(intent.getAction())) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                boolean isGpsEnabled = false;
                boolean isNetworkEnabled = false;
                if (locationManager != null) {
                    isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                }

                if (locationManager != null) {
                    isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                }

                if (isGpsEnabled || isNetworkEnabled) {
                    Toast.makeText(context, "GPS is on", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "GPS is on");
                }

                if (!isGpsEnabled) {
                    Toast.makeText(context, "GPS is of", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "GPS is off");
                }
            }


            //detects when wifi is connected
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo;
            if (wifiManager != null && wifiManager.isWifiEnabled()) {
                wifiInfo = wifiManager.getConnectionInfo();
                if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
                    Log.d(TAG, "Wifi connected");
                    Toast.makeText(context, "Wifi connected", Toast.LENGTH_SHORT).show();
                }
                if (wifiInfo.getSupplicantState() == SupplicantState.DISCONNECTED) {
                    Log.d(TAG, "Wifi is enabled but not connected");
                    Toast.makeText(context, "Wifi not connected", Toast.LENGTH_SHORT).show();
                }
            }

            if (wifiManager != null && !wifiManager.isWifiEnabled()) {
                Log.d(TAG, "Wifi not enabled");
                Toast.makeText(context, "Wifi not enabled", Toast.LENGTH_SHORT).show();
            }



    }

}



