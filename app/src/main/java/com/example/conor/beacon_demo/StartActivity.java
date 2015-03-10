package com.example.conor.beacon_demo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class StartActivity extends Activity {

    Integer REQ_BT_ENABLE=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);




        final TextView t = (TextView) findViewById(R.id.iTour);
        final TextView s = (TextView) findViewById(R.id.ClicktoBegin);
        final ImageView image1 = (ImageView) findViewById(R.id.imageView);
        image1.setImageResource(R.drawable.nuig);
        // final ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar);

        final Button startButton = (Button) findViewById(R.id.StartTour);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startButton.setVisibility(View.INVISIBLE);


                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                if  (!mBluetoothAdapter.isEnabled()) {

                    t.setText("Sorry cannot continue");
                    s.setText("Enable Bluetooth");
                    image1.setImageResource(R.drawable.nuig);

                }

                else if (mBluetoothAdapter.isEnabled()) {

                    Intent i = new Intent(StartActivity.this, MyService.class);
                    startService(i);

                    t.setText("Tour is starting....");
                    s.setText("Tour is starting....");
                    image1.setImageResource(R.drawable.nuig);
                }


            }


        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        if(requestCode == REQ_BT_ENABLE){
            if (resultCode == RESULT_OK){
                Toast.makeText(getApplicationContext(), "BlueTooth is now Enabled", Toast.LENGTH_LONG).show();
            }
            if(resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(), "Unfortunately you wont be able to use this App without Bluetooth", Toast.LENGTH_LONG).show();
                ///finish();
            }
        }
    }

    protected void onStart() {
        super.onStart();

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQ_BT_ENABLE);

        }



    }

    @Override
    protected void onStop() {
        super.onStop();


    }

    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MyService.class));


    }

}




