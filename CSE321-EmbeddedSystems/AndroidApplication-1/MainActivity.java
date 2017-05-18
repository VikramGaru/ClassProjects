package com.example.vikramgaru.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mButton;
    private EditText mEdit;
    String res="";
    String inp="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button mButton = (Button)findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           EditText mEdit   = (EditText)findViewById(R.id.editText);
                                           TextView a=(TextView)findViewById(R.id.textView);
                                           String temp=mEdit.getText().toString();
                                           inp=temp;
                                           int number=Integer.parseInt(temp);
                                           int factors=0;
                                           for(int i=1;i<=number/2;i++){
                                               if(number%i==0){
                                                   factors=factors+1;
                                               }
                                           }
                                           if(factors>=2){
                                               a.setText("Composite");
                                               res="Composite";
                                           }
                                           else {
                                               a.setText("Prime");
                                               res="Prime";
                                           }
                                       }
                                   }
        );
        Button startBtn = (Button) findViewById(R.id.button2);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String mes="Input="+inp+" Result="+res;
                Log.i("Send E-mail","");
                EditText mEdit   = (EditText)findViewById(R.id.editText3);
                String[] to={mEdit.toString()};
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
                }
                catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }

        }
        });
    }
}
