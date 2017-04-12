package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;
import edu.montclair.mobilecomputing.r_soltes.schwifty.fragments.HomeFragment;
import edu.montclair.mobilecomputing.r_soltes.schwifty.fragments.NotificationFragment;
import edu.montclair.mobilecomputing.r_soltes.schwifty.fragments.ScheduleFragment;
import edu.montclair.mobilecomputing.r_soltes.schwifty.fragments.TimeOffFragment;

public class HomePage extends AppCompatActivity {

    Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);

        // Bind the navigation
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

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
                    default:
                        fragment = new HomeFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, fragment).addToBackStack(null).commit();
                return true;
            }
        });

    }

}
