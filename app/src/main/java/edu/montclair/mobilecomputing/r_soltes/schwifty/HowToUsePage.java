package edu.montclair.mobilecomputing.r_soltes.schwifty;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HowToUsePage extends AppCompatActivity {

    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_use_page);


        textview = (TextView) findViewById(R.id.how);

        textview.setMovementMethod(new ScrollingMovementMethod());

        String data = "";

        StringBuffer sBuffer = new StringBuffer();

        InputStream is = this.getResources().openRawResource(R.raw.how);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        if(is !=null) {

            try{

                while((data=reader.readLine())!=null){
                    sBuffer.append(data + "\n"+ "\t");
                }

                textview.setText(sBuffer);
                is.close();

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
