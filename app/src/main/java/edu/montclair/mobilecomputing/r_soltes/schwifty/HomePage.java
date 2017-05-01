package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.montclair.mobilecomputing.r_soltes.schwifty.utils.sessionUser;

public class HomePage extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.nav_profile) Button profileBtn;
    @BindView(R.id.nav_manager_panel) Button managerPanelBtn;
    @BindView(R.id.nav_job_list) Button jobListBtn;
    @BindView(R.id.nav_schedule) Button scheduleBtn;
    @BindView(R.id.nav_time_off) Button timeOffBtn;

    @BindView(R.id.nav_logout) Button logoutBtn;
    @BindView(R.id.hp_username_tv) TextView usernameTv;
    @BindView(R.id.how_to) Button howToBtn;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference, userRef, nameRef;

    public Snackbar snackbar;
    public sessionUser session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Binding
        ButterKnife.bind(this);

        // Set OnClick Listeners for all buttons
        profileBtn.setOnClickListener(this);
        managerPanelBtn.setOnClickListener(this);
        jobListBtn.setOnClickListener(this);
        scheduleBtn.setOnClickListener(this);
        timeOffBtn.setOnClickListener(this);

        logoutBtn.setOnClickListener(this);
        howToBtn.setOnClickListener(this);
        session = new sessionUser(getApplicationContext());

        // Get Firebase current user
        FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();

        // Make string from the current user's ID

        final String uid = user.getUid().toString();
        // Get instance of database
        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");
        // Create reference to users table in database
        userRef = mDatabaseReference.child("usersIDs");
        // Add value listener to users reference to find a user ID equal to the current user's user ID
        userRef.orderByChild("uid").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Display current user's username in TextVIew at the top of the UI
                String username = dataSnapshot.child(uid).child("username").getValue().toString();
                usernameTv.setText(username);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /**
     * onClick to handle which button is pressed on the home page navigation.
     * Send user to page corresponding to the button pressed.
     * **/
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.nav_profile:
                startActivity(new Intent(HomePage.this, ProfilePage.class));
                finish();
                break;
            case R.id.nav_manager_panel:
                // Get current user
                FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();

                // Create string from current user's user ID
                final String uid = user.getUid().toString();
                // Get reference to database
                mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");
                // Get Reference to users
                userRef = mDatabaseReference.child("usersIDs");
                // Add listener to find user ID equal to current user's, user ID
                userRef.orderByChild("uid").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // Creates strings of all the information about the current user in database
                        String userRole = dataSnapshot.child(uid).child("userRole").getValue().toString();
                        String username = dataSnapshot.child(uid).child("username").getValue().toString();
                        String userEmail = dataSnapshot.child(uid).child("email").getValue().toString();

                        // Creates session User with info from database
                        session.createSessionUser(userEmail,uid,username,userRole);
                        // Retreives info about session User from hash map
                        HashMap<String, String> currentUser = session.getUserDetails();

                        // If current user is a manager redirect to the manager panel
                        if(currentUser.get(sessionUser.KEY_SESSION_USERROLE).equals("Manager")){
                            startActivity(new Intent(HomePage.this, ManagerPanelPage.class));
                            finish();
                        // If current user is an employee redirect to error page
                        }else{
                            startActivity(new Intent(HomePage.this, ErrorPage.class));
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
//                startActivity(new Intent(HomePage.this, ManagerPanelPage.class));
//                finish();
                break;
            case R.id.nav_job_list:
                startActivity(new Intent(HomePage.this, JobListPage.class));
                finish();
                break;
            case R.id.nav_schedule:
                startActivity(new Intent(HomePage.this, SchedulePage.class));
                finish();
                break;
            case R.id.nav_time_off:
                startActivity(new Intent(HomePage.this, TimeOffPage.class));
                finish();
                break;
            case R.id.how_to:
                startActivity(new Intent(HomePage.this, HowToUsePage.class));
                finish();
                break;
            case R.id.nav_logout:
                // Notify and ask the user if they want to logout
                // If they click yes then signOut() method is called and the user is signed out
                // Redirected to the login page
                snackbar.make(findViewById(android.R.id.content), "Are you sure you want to logout?",
                        Snackbar.LENGTH_LONG).setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFirebaseAuth.getInstance().signOut();
                        session.logoutUser();
                        startActivity(new Intent(HomePage.this, LoginPage.class));
                        finish();
                    }
                }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                break;
            default:
                throw new RuntimeException("Unknown button ID");
        }
    }

    /**
     * When back button is pressed user stays on the home page
     * **/
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HomePage.this, HomePage.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
