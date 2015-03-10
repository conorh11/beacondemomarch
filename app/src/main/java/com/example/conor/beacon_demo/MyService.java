package com.example.conor.beacon_demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;

import java.util.List;
import java.util.concurrent.TimeUnit;



public class MyService extends Service {
    public MyService() {
    }

    private static final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
    private static final Region ALL_ESTIMOTE_BEACONS = new Region("regionId", ESTIMOTE_PROXIMITY_UUID, null, null);
    private BeaconManager beaconManager = new BeaconManager(this);
    public boolean result = false;

    public double meters2222;
    public double meters4444;
    public double meters6666;


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        // filter.addAction(Intent.ACTION_SCREEN_OFF);
        // BroadcastReceiver mReceiver = new ScreenReceiver();
        // registerReceiver(mReceiver, filter);


        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {

            @Override
            public void onServiceReady() {
                try {
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
                } catch (RemoteException e) {
                    Log.e("EstimoteDemo", "Cannot start ranging", e);
                }
            }
        });

        beaconManager.setBackgroundScanPeriod(TimeUnit.SECONDS.toMillis(5), 0);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            //  beaconManager.setBackgroundScanPeriod(TimeUnit.SECONDS.toMillis(1), 0);

            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> beacons) {
                Log.d("EstimoteDemo", "Ranged beacons: " + beacons);

                if (beacons != null) {
                    for (Beacon beacon : beacons) {


                        if (beacon.getMinor() == 2222) {


                            meters2222 = Utils.computeAccuracy(beacon);
                            // meters = Utils.computeAccuracy(beacon);
                            Log.d("EstimoteDemo", " Minor " + beacon.getMinor() + " Major " + beacon.getMajor() + "Meters" + meters2222);


                            if (meters2222 <= 0.5) {


                                BeaconActivityLaunch(2222);

                            }


                        }

                        if (beacon.getMinor() == 4444) {


                            meters4444 = Utils.computeAccuracy(beacon);
                            // meters = Utils.computeAccuracy(beacon);
                            Log.d("EstimoteDemo", " Minor " + beacon.getMinor() + " Major " + beacon.getMajor() + "Meters" + meters4444);


                            if (meters4444 <= 0.5) {


                                BeaconActivityLaunch(4444);

                            }
                        }


                        if (beacon.getMinor() == 6666) {


                            meters6666 = Utils.computeAccuracy(beacon);
                            // meters = Utils.computeAccuracy(beacon);
                            Log.d("EstimoteDemo", " Minor " + beacon.getMinor() + " Major " + beacon.getMajor() + "Meters" + meters6666);


                            if (meters6666 <= 0.5) {


                                BeaconActivityLaunch(6666);

                            }

                        }


                    }


                }

            }


        });


    }


    // @Override
    // public void onStart(Intent intent, int startId) {
    // For time consuming an long tasks you can launch a new thread here...
    //    Toast.makeText(this, " Service Started", Toast.LENGTH_LONG).show();

    //   super.onStart(intent, startId);
    //   beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
    //       @Override public void onServiceReady() {
    //           try {
    //               beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
    //           } catch (RemoteException e) {
    //               Log.e("EstimoteDemp", "Cannot start ranging", e);
    //          }
    //    }
    // });

    // }

    public void BeaconActivityLaunch(double i) {


        //Notification Code

        //Screen ON/OFF Code
        //  IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        //  filter.addAction(Intent.ACTION_SCREEN_OFF);
        //  BroadcastReceiver mReceiver = new ScreenReceiver();
        //  registerReceiver(mReceiver, filter);

        //  Intent intent = new Intent();
        //  screenOn = intent.getBooleanExtra("screen_state", false);


        if (i == 2222) {


            Intent j = new Intent(MyService.this, BeaconID2222.class);
            j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(j);

        }


            else if (i == 4444) {

                Intent z = new Intent(MyService.this, BeaconID4444.class);
                z.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(z);


            }


              else  if (i == 6666) {

                    Intent x = new Intent(MyService.this, BeaconID6666.class);
                    x.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(x);

                }

        }














    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS);
        } catch (RemoteException e) {
            Log.e("EstimoteDemo", "Cannot stop but it does not matter now", e);
        }
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
        beaconManager.disconnect();




    }


}




