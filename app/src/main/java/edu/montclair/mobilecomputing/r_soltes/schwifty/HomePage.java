package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import edu.montclair.mobilecomputing.r_soltes.schwifty.fragments.HomeFragment;
import edu.montclair.mobilecomputing.r_soltes.schwifty.fragments.NotificationFragment;
import edu.montclair.mobilecomputing.r_soltes.schwifty.fragments.ScheduleFragment;
import edu.montclair.mobilecomputing.r_soltes.schwifty.fragments.TimeOffFragment;

public class HomePage extends AppCompatActivity {

    public Snackbar snackbar;
    private FirebaseAuth mFirebaseAuth;
    Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Binding
        ButterKnife.bind(this);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, fragment).addToBackStack(null).commit();


        // Listener to display the correct information according to tab that is selected.
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.tab_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.tab_schedule:
                        fragment = new ScheduleFragment();
                        break;
                    case R.id.tab_time_off:
                        fragment = new TimeOffFragment();
                        break;
                    case R.id.tab_notification:
                        fragment = new NotificationFragment();
                        break;
                    case R.id.tab_logout:
//                        fragment = new HomeFragment();

                        snackbar.make(findViewById(android.R.id.content), "Are you sure you want to logout?",
                                Snackbar.LENGTH_LONG).setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mFirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(HomePage.this, LoginPage.class));
                                finish();
                            }
                        }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();

                        break;
                    default:
                        fragment = new HomeFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, fragment).addToBackStack(null).commit();
                return true;
            }

        });

    }
    
}
