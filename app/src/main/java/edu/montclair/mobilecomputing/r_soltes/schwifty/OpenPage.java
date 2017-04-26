package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OpenPage extends AppCompatActivity {

    @BindView(R.id.button_schwift) Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_page);
        ButterKnife.bind(this);
    }

    /**
     * OnClick method thats creates an intent to move from OpenPage to Login Page
     * **/
    public void mButton(View view) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shifty = new Intent(OpenPage.this, edu.montclair.mobilecomputing.r_soltes.schwifty.LoginPage.class);
                startActivity(shifty);
            }
        });
    }


}
