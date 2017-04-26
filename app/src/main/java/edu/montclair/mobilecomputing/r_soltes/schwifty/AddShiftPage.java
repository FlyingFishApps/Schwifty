package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.montclair.mobilecomputing.r_soltes.schwifty.model.ManagerNotifications;
import edu.montclair.mobilecomputing.r_soltes.schwifty.model.ManagerNotificationsAdapter;
import edu.montclair.mobilecomputing.r_soltes.schwifty.model.ScheduleNotification;
import edu.montclair.mobilecomputing.r_soltes.schwifty.model.ScheduleNotificationAdapter;

/**
 * Created by tjame_000 on 4/25/2017.
 */

public class AddShiftPage extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.noti_list_ACS) ListView mListView;
    @BindView(R.id.uid_CS) EditText uID;
    @BindView(R.id.bId_CS) EditText place;
    @BindView(R.id.end_time) EditText endTime;
    @BindView(R.id.in_time) EditText message;
    @BindView(R.id.in_date) EditText title;
    @BindView(R.id.nID_CS) EditText numID;
    @BindView(R.id.create_shift_btn) Button notiBtn;

    private String value;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Snackbar snackbar;
    private DatabaseReference mDatabaseReference, notifRef, userIdRef, businessRef;
    private FirebaseAuth mFirebaseAuth;
    RelativeLayout activity_create_shift;
    ScheduleNotificationAdapter mNotificationAdapter;
    ChildEventListener mChildEventListener;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_shift);
        ButterKnife.bind(this);
        activity_create_shift = (RelativeLayout)findViewById(R.id.activity_create_shift);

        notiBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
               createNotification("sID: "+numID.getText().toString().trim(),"nID: "+uID.getText().toString().trim(),"Date: "+title.getText().toString().trim(),"Time in: "+message.getText().toString().trim(),"Time out: "+endTime.getText().toString());
                checkBusiness();
            }
        });

        numID.setOnClickListener(this);
        uID.setOnClickListener(this);
        title.setOnClickListener(this);
        message.setOnClickListener(this);
        endTime.setOnClickListener(this);
        place.setOnClickListener(this);


        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();


        List<ScheduleNotification> listOfNotifis = new ArrayList<>();
        notifRef = FirebaseDatabase.getInstance().getReference("users");
        mFirebaseAuth = FirebaseAuth.getInstance();

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


    private void createNotification(String sID, String uID, String nDate, String nStartTime, String nEndTime) {

        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        String nId = String.valueOf(n);

        ScheduleNotification notification = new ScheduleNotification(nId, sID, uID, nDate, nStartTime, nEndTime);

        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");
        notifRef = mDatabaseReference.child("full_schedule");
        mDatabaseReference.child("full_schedule").push().setValue(notification);
        snackbar.make(activity_create_shift, "Notification Sent!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        mNotificationAdapter.clear();
        mNotificationAdapter.clear();

    }

    public void shift() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");
        userIdRef = mDatabaseReference.child("users");
        userIdRef.child(uID.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                userIdRef.child(uID.getText().toString()).child("Schedule").push().setValue("sID: "+ numID.getText().toString() + "\nPlace: "+place.getText().toString()+"\nDate: "+title.getText().toString()+"\nStart Shift:"+ message.getText().toString()+"  End Shift:" + endTime.getText().toString() );


                snackbar.make(activity_create_shift, "Shift Added!", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                title.getText().clear();
                message.getText().clear();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void checkBusiness(){

        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");

        businessRef = mDatabaseReference.child("businesses");
        businessRef.child(place.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists() || place.getText().toString().equals(null)|| numID.getText().toString().equals(null)){
                    place.setError("Business does not exist!");
                }else{
                    shift();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onClick(View v) {

        if (v == title) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            title.setText((monthOfYear + 1) + "-" + dayOfMonth + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == message) {

            // Get Current Time

            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            message.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();


        }
        if (v == endTime) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            endTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();


        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddShiftPage.this, HomePage.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}