package com.lukino999.fullscreen004;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter BA = BluetoothAdapter.getDefaultAdapter();

    public static final int BLUETOOTH_HAS_BEEN_ENABLED = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    //


    public void buttonGetSizeClick(View view) {

        RelativeLayout layoutToBeMeasured = (RelativeLayout) findViewById(R.id.mainLayout);
        String logText = "\n" + layoutToBeMeasured.getWidth() + "x" + layoutToBeMeasured.getHeight();

        Log.i("toastText", logText);
        appendToLog(logText);
        //Toast.makeText(getApplicationContext(), logText, Toast.LENGTH_SHORT).show();

    }


    //from stackoverflow
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == BLUETOOTH_HAS_BEEN_ENABLED){
            // It came from our call
            if(resultCode == Activity.RESULT_OK){
                // The result was successful
                afterBluetoothHasBeenEnabled(); // you can use the data returned by the Intent do figure out what to do
            }
        }
    }

    public void afterBluetoothHasBeenEnabled() {
        if (BA.isEnabled()) {
            //Toast.makeText(getApplicationContext(), "Bluetooth has been turned on", Toast.LENGTH_SHORT).show();
            appendToLog("\nBluetooth has been turned on");
        }
    }

    public void buttonTestBluetoothClick(View view) {

        Log.i("BA.isEnabled", String.valueOf(BA.isEnabled()));
        if (BA.isEnabled()) {
            //Toast.makeText(getApplicationContext(), "Bluetooth is on", Toast.LENGTH_SHORT).show();
            appendToLog("\nBluetooth is on");
        }   else {
            appendToLog("\nIntent to turn on bluetooth");


            Intent i = new Intent(BA.ACTION_REQUEST_ENABLE);
            startActivityForResult(i, BLUETOOTH_HAS_BEEN_ENABLED);




        }


    }

    public void buttonTestMultilineTextClick(View view) {
        String textToAppend = "\n" + String.valueOf(Math.random()) + "\n";
        appendToLog(textToAppend);
    }

    private void appendToLog(String textToAppend) {
        TextView tv = (TextView) findViewById(R.id.textViewLog);
        String textInTextView = (String) tv.getText();
        tv.setText(textInTextView + textToAppend);
        final ScrollView scrollview = ((ScrollView) findViewById(R.id.scrollView));
        scrollview.post(new Runnable() {
            @Override
            public void run() {
                scrollview.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }





}
