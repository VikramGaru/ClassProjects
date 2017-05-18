package com.example.vikramgaru.numberconverter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity{
    private SensorManager mSensorManager;
    private Sensor mSensor;
    //private Button mButton;
    private EditText mEdit;
    String res = "";
    String inp1 = "";
    String inp2 = "";
    double x,y,z;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener s = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                TextView a=(TextView) findViewById(R.id.textView);
                TextView b=(TextView) findViewById(R.id.textView2);
                TextView c=(TextView) findViewById(R.id.textView3);
                x = event.values[0];
                y = event.values[1];
                z = event.values[2];
                a.setText("x-axis = "+x);
                b.setText("y-axis = "+y);
                c.setText("z-axis = "+z);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        mSensorManager.registerListener(s, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Button mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView a=(TextView) findViewById(R.id.textView4);
                TextView b=(TextView) findViewById(R.id.textView6);
                TextView c=(TextView) findViewById(R.id.textView7);
                inp1=((int)x*50)+"";
                inp2=((int)y*50)+"";
                int r=convert( inp1, inp2);
                res=r+"";
                a.setText(inp1);
                b.setText(inp2);
                c.setText(res);
            }
        });
        Button startBtn = (Button) findViewById(R.id.button2);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String mes = "Input=" + inp1 +" and " +inp2 + " Result=" + res;
                Log.i("Send E-mail", "");
                EditText mEdit = (EditText) findViewById(R.id.editText);
                String[] to = {mEdit.toString()};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Application Result");
                emailIntent.putExtra(Intent.EXTRA_TEXT, mes);
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                    Log.i("Finished sending email.", "");
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public int convert(String n, String n1) {
        int a1 = 0;
        int b1 = 0;
        String a = n;
        String b = n1;
        int highest = a.charAt(0);
        for (int i = 0; i < a.length(); i++) {
            int currentNumber = a.charAt(i);
            if (currentNumber > highest) {
                highest = a.charAt(i);
            }
        }
        int base = highest - '0' + 1;
        if (base > 17) {
            base = base - 7;
        }
        for (int i = a.length() - 1; i >= 0; i--) {
            int value = a.charAt(i);
            int k = value - '0';
            if (k >= 17) {
                k = k - 7;
            }
            a1 = a1 + (int) (k * (Math.pow(base, a.length() - i - 1)));
        }
        highest = b.charAt(0);
        for (int i = 0; i < b.length(); i++) {
            int currentNumber = b.charAt(i);
            if (currentNumber > highest) {
                highest = b.charAt(i);
            }
        }
        base = highest - '0' + 1;
        if (base > 17) {
            base = base - 7;
        }
        for (int i = b.length() - 1; i >= 0; i--) {
            int value = b.charAt(i);
            int k = value - '0';
            if (k >= 17) {
                k = k - 7;
            }
            b1 = b1 + (int) (k * (Math.pow(base, b.length() - i - 1)));
        }
        int sum = a1 + b1;
        return sum;
    }
}
