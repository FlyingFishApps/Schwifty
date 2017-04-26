package edu.montclair.mobilecomputing.r_soltes.schwifty;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HowToUsePage extends AppCompatActivity {

    @BindView(R.id.how) TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_use_page);
        ButterKnife.bind(this);

        textview.setMovementMethod(new ScrollingMovementMethod());

        // Create string for data
        String data;
        // Create new string buffer
        StringBuffer sBuffer = new StringBuffer();
        // Create new input stream
        InputStream is = this.getResources().openRawResource(R.raw.how);
        // Create new buffer reader
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        // If the input stream is not null then read info from data
        if (is != null) {
            try{
                while ((data = reader.readLine()) != null) {
                    sBuffer.append(data + "\n" + "\t");
                }
                // Set text view to the info read from data
                textview.setText(sBuffer);
                // Close input stream
                is.close();

            // Catch exception
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * When back button is pressed go to home page.
     * **/
    @Override
    public void onBackPressed () {
        Intent intent = new Intent(HowToUsePage.this, HomePage.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);

    }
}
