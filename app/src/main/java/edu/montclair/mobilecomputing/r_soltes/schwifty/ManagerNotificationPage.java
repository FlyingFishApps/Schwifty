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
import android.widget.ArrayAdapter;
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
import com.google.firebase.database.ValueEventListener;

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
    private DatabaseReference mDatabaseReference, notifRef, notifRefJ;
    private FirebaseAuth mFirebaseAuth;
    private String value;
    private List<String> listOfJobs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_notification_page);
        ButterKnife.bind(this);


        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfJobs);

        notifRef = mDatabaseReference.child("businesses");
        notifRefJ = mDatabaseReference.child("usersIDs");
        mFirebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user3 = mFirebaseAuth.getInstance().getCurrentUser();
        final String uid = user3.getUid().toString();

        notifRefJ.child(uid).child("jobs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                // Add each job in a user's jobs child to the array
                // Set the array adapter to the list view on UI to display elements in array
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String data = snapshot.getValue().toString();
                    String data2 = data.substring(data.lastIndexOf("=")+1);
                    final String data3 = data2.split("\\}")[0];

                    value = data3;
                    notifRef.child(value).child("manager_notifications").child("Jamest8").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot){
                            // Add each job in a user's jobs child to the array
                            // Set the array adapter to the list view on UI to display elements in array
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String data = snapshot.getValue().toString();
                                String data2 = data.substring(data.lastIndexOf("=")+1);
                                final String data3 = data2.split("\\}")[0];


                                System.out.println(data3);
                                listOfJobs.add(data3);
                                mListView.setAdapter(arrayAdapter);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }




    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ManagerNotificationPage.this, ManagerPanelPage.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}

