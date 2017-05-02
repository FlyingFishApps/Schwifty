package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.montclair.mobilecomputing.r_soltes.schwifty.model.NotificationAdapter;
import edu.montclair.mobilecomputing.r_soltes.schwifty.model.Notifications;

/**
 * Created by tjame_000 on 4/24/2017.
 */

public class NotificationPageEmployee extends AppCompatActivity {

    @BindView(R.id.noti_list_ENP)

    private DatabaseReference mDatabaseReference, notifRef;
    private FirebaseAuth mFirebaseAuth;

    ListView mListView;
    Snackbar snackbar;
    LinearLayout activity_employee_notification_page;
    NotificationAdapter mNotificationAdapter;
    ChildEventListener mChildEventListener;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_notification_page);

        ButterKnife.bind(this);
        activity_employee_notification_page = (LinearLayout) findViewById(R.id.activity_employee_notification_page);

        List<Notifications> listOfNotifis = new ArrayList<>();
        notifRef = FirebaseDatabase.getInstance().getReference("notifications");
        mFirebaseAuth = FirebaseAuth.getInstance();

        mNotificationAdapter = new NotificationAdapter(this, R.layout.notification_item, listOfNotifis);
        mListView.setAdapter(mNotificationAdapter);

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Notifications notification = dataSnapshot.getValue(Notifications.class);
                mNotificationAdapter.add(notification);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        notifRef.addChildEventListener(mChildEventListener);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NotificationPageEmployee.this, HomePage.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }


}