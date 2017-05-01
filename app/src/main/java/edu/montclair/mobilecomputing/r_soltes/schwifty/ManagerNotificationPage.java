package edu.montclair.mobilecomputing.r_soltes.schwifty;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.montclair.mobilecomputing.r_soltes.schwifty.model.ManagerNotifications;
import edu.montclair.mobilecomputing.r_soltes.schwifty.model.ManagerNotificationsAdapter;
import edu.montclair.mobilecomputing.r_soltes.schwifty.model.NotificationAdapter;
import edu.montclair.mobilecomputing.r_soltes.schwifty.model.Notifications;
import edu.montclair.mobilecomputing.r_soltes.schwifty.model.ScheduleNotification;
import edu.montclair.mobilecomputing.r_soltes.schwifty.model.ScheduleNotificationAdapter;

public class ManagerNotificationPage extends AppCompatActivity {
    @BindView(R.id.noti_list_MNP) ListView mListView;

    Snackbar snackbar;
    private DatabaseReference mDatabaseReference, notifRef;
    private FirebaseAuth mFirebaseAuth;
    RelativeLayout activity_manager_notification_page;
    ScheduleNotificationAdapter mNotificationAdapter;
    ChildEventListener mChildEventListener;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_notification_page);
        ButterKnife.bind(this);
        activity_manager_notification_page = (RelativeLayout)findViewById(R.id.activity_manager_notification_page);
        List<ScheduleNotification> listOfNotifis = new ArrayList<>();


        FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid().toString();
        notifRef = FirebaseDatabase.getInstance().getReference("usersIDs").child(uid).child("jobs");
        mNotificationAdapter = new ScheduleNotificationAdapter(this, R.layout.schedule_notification_item, listOfNotifis);
        mListView.setAdapter(mNotificationAdapter);

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ScheduleNotification notification = dataSnapshot.getValue(ScheduleNotification
                        .class);
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
        Intent intent = new Intent(ManagerNotificationPage.this, ManagerPanelPage.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}

