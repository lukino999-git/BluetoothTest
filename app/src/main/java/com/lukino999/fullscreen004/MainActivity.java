package com.lukino999.fullscreen004;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //


    public void buttonGetSizeClick(View view) {
        RelativeLayout layoutToBeMeasured = (RelativeLayout) findViewById(R.id.mainLayout);
        String toastText = layoutToBeMeasured.getWidth() + "x" + layoutToBeMeasured.getHeight();

        Log.i("toastText", toastText);

        Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
    }

    public void buttonTestBluetoothClick(View view) {

        Log.i("BA.isEnabled", String.valueOf(BA.isEnabled()));
        if (BA.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Bluetooth is on", Toast.LENGTH_SHORT).show();
        }   else {
            Intent i = new Intent(BA.ACTION_REQUEST_ENABLE);
            startActivity(i);
            if (BA.isEnabled()) {
                Toast.makeText(getApplicationContext(), "Bluetooth has been turned on", Toast.LENGTH_SHORT).show();
            }
        }


    }

    public void buttonTestMultilineTextClick(View view) {


        TextView tv = (TextView) findViewById(R.id.textViewLog);
        String textInTextView = (String) tv.getText();
        String textToAppend = "\n" + String.valueOf(Math.random()) + "\n";
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
