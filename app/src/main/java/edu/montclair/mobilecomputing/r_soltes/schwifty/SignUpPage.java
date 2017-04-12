package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpPage extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.su_signup_btn) Button signupBtn;
    @BindView(R.id.su_forgot_pass_btn) TextView forgotpassBtn;
    @BindView(R.id.su_login_btn) TextView loginBtn;
    @BindView(R.id.su_signup_email) EditText inputEmail;
    @BindView(R.id.su_signup_password) EditText inputPass;
    RelativeLayout activity_sign_up_page;
    Snackbar snackbar;
    private FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        ButterKnife.bind(this);

        activity_sign_up_page = (RelativeLayout)findViewById(R.id.activity_sign_up_page);
        signupBtn.setOnClickListener(this);
        forgotpassBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

        // Firebase Init
        mFirebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.su_forgot_pass_btn:
                startActivity(new Intent(SignUpPage.this, ForgotPassPage.class));
                finish();
                break;
            case R.id.su_signup_btn:
                signupUser(inputEmail.getText().toString(),inputPass.getText().toString());
                break;
            case R.id.su_login_btn:
                startActivity(new Intent(SignUpPage.this, LoginPage.class));
                finish();
                break;
            default:
                throw new RuntimeException("Unknown button ID");


        }
    }

    private void signupUser(String email, String password) {

        mFirebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){

                            snackbar.make(activity_sign_up_page, "Error: "+task.getException(),Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();

                        }else{
                            snackbar.make(activity_sign_up_page, "User Created! ",Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                        }

                    }
                });

    }
}
