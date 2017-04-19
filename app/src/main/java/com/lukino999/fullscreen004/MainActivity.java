package com.lukino999.fullscreen004;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter BA = BluetoothAdapter.getDefaultAdapter();

    public static final int BLUETOOTH_HAS_BEEN_ENABLED = 1001;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    // String currentDateandTime = sdf.format(new Date());




    // from stackoverflow: exec code upon activity finishes...
    // This override makes so that when the intent of enabling the bluetooth returns a positive result, it then calls the afterBluetoothHasBeenEnabled();
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

    // This gets called from the previous @overrided onActivityResult
    public void afterBluetoothHasBeenEnabled() {
        if (BA.isEnabled()) {
            //Toast.makeText(getApplicationContext(), "Bluetooth has been turned on", Toast.LENGTH_SHORT).show();
            appendToLog("Bluetooth has been turned on");
        }
    }


    /*
    You will want to register a BroadcastReceiver to listen for any changes in the state of the BluetoothAdapter:

    As a private instance variable in your Activity (or in a separate class file... whichever one you prefer):
     */
    // The actual code
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        // when STATE_OFF
                        appendToLog("BluetoothAdapter.STATE_OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        // when STATE_TURNING_OFF
                        appendToLog("BluetoothAdapter.STATE_TURNING_OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        // when STATE_ON
                        appendToLog("BluetoothAdapter.STATE_ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        // when STATE_TURNING_ON
                        appendToLog("BluetoothAdapter.STATE_TURNING_ON");
                        break;
                }
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Register for broadcasts on BluetoothAdapter state change
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    /* ... */

        // Unregister broadcast listeners
        unregisterReceiver(mReceiver);
    }

    private void appendToLog(String textToAppend) {
        Log.i("appendToLog()", textToAppend);
        TextView tv = (TextView) findViewById(R.id.textViewLog);
        String textInTextView = (String) tv.getText();
        tv.setText(textInTextView + "\n" + sdf.format(new Date()) + ": " + textToAppend);
        final ScrollView scrollview = ((ScrollView) findViewById(R.id.scrollView));
        scrollview.post(new Runnable() {
            @Override
            public void run() {
                scrollview.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    // --- On click
    public void buttonTestBluetoothClick(View view) {

        appendToLog("Click on: Test Bluetooth");
        if (BA.isEnabled()) {
            //Toast.makeText(getApplicationContext(), "Bluetooth is on", Toast.LENGTH_SHORT).show();
            appendToLog("Bluetooth is on");
        }   else {
            appendToLog("Intent to turn bluetooth ON");
            Intent i = new Intent(BA.ACTION_REQUEST_ENABLE);
            startActivityForResult(i, BLUETOOTH_HAS_BEEN_ENABLED);
        }

    }

    public void buttonGetSizeClick(View view) {

        RelativeLayout layoutToBeMeasured = (RelativeLayout) findViewById(R.id.mainLayout);
        String logText = layoutToBeMeasured.getWidth() + "x" + layoutToBeMeasured.getHeight();
        appendToLog(logText);
        //Toast.makeText(getApplicationContext(), logText, Toast.LENGTH_SHORT).show();

    }

    public void buttonTestMultilineTextClick(View view) {
        String textToAppend = String.valueOf(Math.random()) + "\n";
        appendToLog(textToAppend);
    }

    public void buttonTurnBluetoothOffClick(View view) {
        appendToLog("Click on: Turn bluetooth OFF");
        BA.disable();
    }

    public void buttonFindDiscoverableDevicesClick(View view) {
        appendToLog("Click on: Find discoverable devices");
        Intent i = new Intent(BA.ACTION_REQUEST_DISCOVERABLE);
        startActivity(i);
    }

    public void buttonShowPairedDevicesClick(View view) {
        appendToLog("Click on: Show paired devices");

        Set<BluetoothDevice> pairedDevices = BA.getBondedDevices();
        ListView pairedDevicedListView = (ListView) findViewById(R.id.pairedDevicesListView);
        ArrayList pairedDevicesArrayList = new ArrayList();
        for (BluetoothDevice bluetoothDevice : pairedDevices) {
            pairedDevicesArrayList.add(bluetoothDevice.getName());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, pairedDevicesArrayList);
        pairedDevicedListView.setAdapter(arrayAdapter);

    }



}
