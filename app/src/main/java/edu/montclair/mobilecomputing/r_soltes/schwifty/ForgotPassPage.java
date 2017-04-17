package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPassPage extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.fp_login_email) EditText minputEmail;
    @BindView(R.id.fp_reset_btn) Button mresetBtn;
    @BindView(R.id.fp_sign_up_btn) TextView msignupBtn;
    @BindView(R.id.fp_signin_btn) TextView msigninBtn;
    @BindView(R.id.progressBar) ProgressBar mprogressBar;

    private FirebaseAuth mFirebaseAuth;
    Snackbar snackbar;
    RelativeLayout activity_forgot_pass_page;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass_page);
        ButterKnife.bind(this);

        activity_forgot_pass_page = (RelativeLayout)findViewById(R.id.activity_forgot_pass_page);
        mresetBtn.setOnClickListener(this);
        msigninBtn.setOnClickListener(this);
        msignupBtn.setOnClickListener(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fp_reset_btn:

                String email = minputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    minputEmail.setError("Enter your registered email address");
                    return;
                }else {

                    mprogressBar.setVisibility(View.VISIBLE);
                    mFirebaseAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        snackbar.make(findViewById(android.R.id.content), "We have sent you instructions to reset your password",
                                                Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                    } else {
                                        snackbar.make(findViewById(android.R.id.content), "Failed to reset your password",
                                                Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                    }

                                    mprogressBar.setVisibility(View.GONE);
                                }
                            });
                }

                break;
            case R.id.fp_sign_up_btn:
                startActivity(new Intent(ForgotPassPage.this, SignUpPage.class));
                finish();
                break;
            case R.id.fp_signin_btn:
                startActivity(new Intent(ForgotPassPage.this, LoginPage.class));
                finish();
                break;
            default:
                throw new RuntimeException("Unknown button ID");

        }
    }
}
