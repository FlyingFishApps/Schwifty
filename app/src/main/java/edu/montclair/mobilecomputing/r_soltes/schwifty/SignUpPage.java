package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.montclair.mobilecomputing.r_soltes.schwifty.utils.User;

public class SignUpPage extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.su_signup_btn) Button signupBtn;
    @BindView(R.id.su_forgot_pass_btn) TextView forgotpassBtn;
    @BindView(R.id.su_login_btn) TextView loginBtn;
    @BindView(R.id.su_signup_email) EditText inputEmail;
    @BindView(R.id.su_signup_password) EditText inputPass;
    @BindView(R.id.su_signup_username) EditText inputUsername;
    @BindView(R.id.user_role_spinner) Spinner userRoleSpinner;
    @BindView(R.id.suProgressBar) ProgressBar mprogressBar;

    RelativeLayout activity_sign_up_page;
    public static final String TAG = SignUpPage.class.getSimpleName();
    Snackbar snackbar;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        ButterKnife.bind(this);

        activity_sign_up_page = (RelativeLayout)findViewById(R.id.activity_sign_up_page);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_role_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        userRoleSpinner.setAdapter(adapter);

        signupBtn.setOnClickListener(this);
        forgotpassBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

        // Firebase Init
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.su_forgot_pass_btn:
                startActivity(new Intent(SignUpPage.this, ForgotPassPage.class));
                finish();
                break;
            case R.id.su_signup_btn:
                if(!TextUtils.isEmpty(inputUsername.getText().toString())){
//                    signupUser(inputEmail.getText().toString().trim(),inputPass.getText().toString().trim());
                    checkUsername();
                }else{
                    inputUsername.setError("Please enter a username");
                }
                break;
            case R.id.su_login_btn:
                startActivity(new Intent(SignUpPage.this, LoginPage.class));
                finish();
                break;
            default:
                throw new RuntimeException("Unknown button ID");


        }
    }

    private void checkUsername(){

        mDatabaseReference.child("users").child(inputUsername.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    inputUsername.setError("Username already exists.");
                }else{
                    signupUser(inputEmail.getText().toString().trim(),inputPass.getText().toString().trim());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void signupUser(final String email, String password) {

        mprogressBar.setVisibility(View.VISIBLE);


        mFirebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            snackbar.make(activity_sign_up_page, "Error: "+task.getException(),Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                            Log.d(TAG, "CREATE_USER_ERROR: " + task.getException());

                        }else{
                            FirebaseUser user = task.getResult().getUser();

                            createNewUser(inputUsername.getText().toString().trim(),
                                    email,user.getUid(),userRoleSpinner.getSelectedItem().toString().trim());
                            inputUsername.getText().clear();
                            inputEmail.getText().clear();
                            inputPass.getText().clear();
                            snackbar.make(activity_sign_up_page, "User Created! ",Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                        }
                        mprogressBar.setVisibility(View.GONE);
                    }
                });

    }

    private void createNewUser(String name, String email, String userId, String userRole) {
        User user = new User(name, email, userId, userRole);

        mDatabaseReference.child("users").child(name).setValue(user);
    }

}
