package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.lp_login_btn) Button loginBtn;
    @BindView(R.id.lp_sign_up_btn) TextView signupBtn;
    @BindView(R.id.lp_forgot_pass_btn) TextView forgotpassBtn;
    @BindView(R.id.lp_login_email) EditText inputEmail;
    @BindView(R.id.lp_login_password) EditText inputPass;
    @BindView(R.id.lpProgressBar) ProgressBar mprogressBar;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public Snackbar snackbar;

    RelativeLayout activity_login_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        // Binding
        ButterKnife.bind(this);
        activity_login_page = (RelativeLayout)findViewById(R.id.activity_login_page);

        // Set click listeners for the login page buttons
        signupBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        forgotpassBtn.setOnClickListener(this);

        // Init Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();

        // Check if user is signed in
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    startActivity(new Intent(LoginPage.this, HomePage.class));
                    finish();
                } else {
                    // User is signed out
                }
            }
        };
    }

    /**
     * When activity starts, authListener is added to
     * check if there is a user currently signed in.
     * **/
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    /**
     * onClick method to handle which button in UI is clicked.
     * Starts intent to page corresponding to the id of the button
     * clicked.
     * **/
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.lp_forgot_pass_btn:
                startActivity(new Intent(LoginPage.this, ForgotPassPage.class));
                finish();
                break;
            case R.id.lp_sign_up_btn:
                startActivity(new Intent(LoginPage.this, SignUpPage.class));
                finish();
                break;
            case R.id.lp_login_btn:
                loginUser(inputEmail.getText().toString(),inputPass.getText().toString());
                break;
            default:
                throw new RuntimeException("Unknown button ID");

        }
    }

    /**
     * Method that checks if Login fields are empty, if so display error through snackbar.
     * If both fields are filled, checks credentials against users in database.
     * If correct, user is logged in and redirected to the home page.
     * **/
    private void loginUser(final String email, final String password) {

        // Shows a progress bar so user knows system is working.
        mprogressBar.setVisibility(View.VISIBLE);

        if(TextUtils.isEmpty(email)){
            snackbar.make(activity_login_page, "Please enter an email address",
                    Snackbar.LENGTH_SHORT).setAction("Action", null).show();

        }else if(TextUtils.isEmpty(password) || password.length() < 6){
            snackbar.make(activity_login_page, "Password length be atleast 6 characters",
                    Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        }else{
            mFirebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            snackbar.make(activity_login_page, "Error: "+task.getException(),Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                            if(TextUtils.isEmpty(password) || password.length() < 6){
                                snackbar.make(activity_login_page, "Password length be atleast 6 characters",
                                        Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            }
                        }else{
                            startActivity(new Intent(LoginPage.this, HomePage.class));
                        }
                        }
                    });
        }

        // Hides progress bar to show the process is done.
        mprogressBar.setVisibility(View.GONE);
    }

    /**
     * When activity is stopped, authListener is no longer checking
     * for a user already logged in.
     * **/
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
