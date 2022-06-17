package com.gamzeuysal.broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String TAG ="Main";
    TextView textView;
    @Override
//1
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"OnCreate");

        textView = findViewById(R.id.textView);



        //start receiving the message
       // registerReceiver(updateTextViewReceiver,new IntentFilter("ACTION_UPDATE_TEXT_VIEW"));



    }
//onStart activity de ilk çalışandır
    //2
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"OnStart");
        //start receiving the message

        registerReceiver(updateTextViewReceiver,new IntentFilter("ACTION_UPDATE_TEXT_VIEW"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Thread arka plan  kodlar
                //Send result from a background thread to the main thread
                Intent intent = new Intent("ACTION_UPDATE_TEXT_VIEW");
                intent.putExtra("text","Send message from thread");
                getApplicationContext().sendBroadcast(intent);


            }
        }).start();

    }
//3
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"OnResume");

    }

    @Override
    protected void onStop() {
        unregisterReceiver(updateTextViewReceiver);
        super.onStop();
        Log.d(TAG,"OnStop");
        //stop receiving the message

    }

    //Create a broadcast to receive message from the background thread
    private final BroadcastReceiver updateTextViewReceiver  = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "receiver");
               //Broadcast yayın yapılıp  mesajın alındığı yer
            String textReceived = intent.getStringExtra("text");//Broadcast alınana text
            textView.setText(textReceived);
        }
    };
}