package edu.montclair.mobilecomputing.r_soltes.schwifty;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.button;

public class HowToUsePage extends AppCompatActivity {

    @BindView(R.id.how) TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_use_page);
        ButterKnife.bind(this);

        textview.setMovementMethod(new ScrollingMovementMethod());

        String data = "";

        StringBuffer sBuffer = new StringBuffer();

        InputStream is = this.getResources().openRawResource(R.raw.how);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        if (is != null) {
            try{
                while ((data = reader.readLine()) != null) {
                    sBuffer.append(data + "\n" + "\t");
                }

                textview.setText(sBuffer);
                is.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(HowToUsePage.this, HomePage.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);

    }


}
