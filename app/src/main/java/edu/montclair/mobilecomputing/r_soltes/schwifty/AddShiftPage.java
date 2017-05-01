package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    private List<String> listOfJobs = new ArrayList<>();
    private String value;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Snackbar snackbar;
    private DatabaseReference mDatabaseReference, notifRef, notifRefJ, userIdRefn, userIdRefID, userIdRef,userBusRef, businessRef;
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
               createNotification("sID: "+numID.getText().toString().trim(),
                       "nID: "+uID.getText().toString().trim(),"Date: "+title.getText().toString().trim(),
                       "Time in: "+message.getText().toString().trim(),"Time out: "+endTime.getText().toString());
                checkBusiness();
            }
        });

        numID.setOnClickListener(this);
        uID.setOnClickListener(this);
        title.setOnClickListener(this);
        message.setOnClickListener(this);
        endTime.setOnClickListener(this);
        place.setOnClickListener(this);



        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfJobs);

        notifRef = mDatabaseReference.child("businesses");
        notifRefJ = mDatabaseReference.child("usersIDs");
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user3 = mFirebaseAuth.getInstance().getCurrentUser();
        final String uid = user3.getUid().toString();


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

    }


    /**
     * Adds an instance of a user's shift, which is made by a manager, than stores them
     * in users > Schedule > Shift + "sID"
     * */
    public void addShift() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");
        userIdRef = mDatabaseReference.child("users");
        userIdRef.child(uID.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    // Adds an instance of the users' sID in the child of Shift branch.
                userIdRef.child(uID.getText().toString()).child("Schedule").child("Shift: "+numID.getText().toString()).child("sID").setValue(numID.getText().toString());
                    // Adds an instance of the users' Place in the child of Shift branch.
                userIdRef.child(uID.getText().toString()).child("Schedule").child("Shift: "+numID.getText().toString()).child("Place").setValue(place.getText().toString());
                    // Adds an instance of the users' Date in the child of Shift branch.
                userIdRef.child(uID.getText().toString()).child("Schedule").child("Shift: "+numID.getText().toString()).child("Date").setValue(title.getText().toString());
                    // Adds an instance of the users' Start Shift in the child of Shift branch.
                userIdRef.child(uID.getText().toString()).child("Schedule").child("Shift: "+numID.getText().toString()).child("Start Shift").setValue(message.getText().toString());
                    // Adds an instance of the users' End Shift in the child of Shift branch.
                userIdRef.child(uID.getText().toString()).child("Schedule").child("Shift: "+numID.getText().toString()).child("End Shift").setValue(endTime.getText().toString());


                userIdRef.child(uID.getText().toString()).child("sch").child(numID.getText().toString()).setValue
                        ("\nPlace: "+ place.getText().toString()+ "\nShift: " +numID.getText().toString()
                                + "\nEmployee: "+ uID.getText().toString()+"\nDate: "+ title.getText().toString()
                                + "\nStart Time:" + message.getText().toString()+ "\nEnd Time: " +
                                endTime.getText().toString());

                        addShiftID();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    /**
     * Adds an instance of a user's shift, which is made by a manager, than stores them
     * in users > Schedule > Shift + "sID"
     * */
    public void addShiftToBus() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");
        userBusRef = mDatabaseReference.child("businesses");
        userBusRef.child(place.getText().toString()).child("full_schedule").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Adds an instance of the users' sID in the child of Shift branch.
                userBusRef.child(place.getText().toString()).child("full_schedule").child("Shift: "+numID.getText().toString()).child("sID").setValue(numID.getText().toString());
                // Adds an instance of the users' Place in the child of Shift branch.
                userBusRef.child(place.getText().toString()).child("full_schedule").child("Shift: "+numID.getText().toString()).child("Place").setValue(place.getText().toString());
                // Adds an instance of the users' Date in the child of Shift branch.
                userBusRef.child(place.getText().toString()).child("full_schedule").child("Shift: "+numID.getText().toString()).child("Date").setValue(title.getText().toString());
                // Adds an instance of the users' Start Shift in the child of Shift branch.
                userBusRef.child(place.getText().toString()).child("full_schedule").child("Shift: "+numID.getText().toString()).child("Start Shift").setValue(message.getText().toString());
                // Adds an instance of the users' End Shift in the child of Shift branch.
                userBusRef.child(place.getText().toString()).child("full_schedule").child("Shift: "+numID.getText().toString()).child("End Shift").setValue(endTime.getText().toString());

                userBusRef.child(place.getText().toString()).child("full_schedule_noti").child(numID.getText().toString()).setValue
                        ("\nPlace: "+ place.getText().toString()+ "\nShift: " +numID.getText().toString()
                        + "\nEmployee: "+ uID.getText().toString()+"\nDate: "+ title.getText().toString()
                                + "\nStart Time:" + message.getText().toString()+ "\nEnd Time: " +
                                endTime.getText().toString());




                snackbar.make(activity_create_shift, "Shift Added!", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    /**
     * Adds an instance of a user's shift, which is made by a manager, than stores them
     * in usersIDs > Schedule > Shift + "sID"
     * */
    public void addShiftID(){

        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");
        userIdRefn = mDatabaseReference.child("users").child(uID.getText().toString()).child("uid");

        userIdRefn.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String uid = dataSnapshot.getValue().toString();
                userIdRefID = mDatabaseReference.child("usersIDs").child(uid);

                    // Adds an instance of the usersIDs' sId in the child of Shift branch.
                userIdRefID.child("Schedule").child("Shift: "+numID.getText().toString()).child("sID").setValue(numID.getText().toString());
                    // Adds an instance of the usersIDs' Place in the child of Shift branch.
                userIdRefID.child("Schedule").child("Shift: "+numID.getText().toString()).child("Place").setValue(place.getText().toString());
                    // Adds an instance of the usersIDs' Date in the child of Shift branch.
                userIdRefID.child("Schedule").child("Shift: "+numID.getText().toString()).child("Date").setValue(title.getText().toString());
                    // Adds an instance of the usersIDs' Start Shift in the child of Shift branch.
                userIdRefID.child("Schedule").child("Shift: "+numID.getText().toString()).child("Start Shift").setValue(message.getText().toString());
                    // Adds an instance of the usersIDs' End Shift in the child of Shift branch.
                userIdRefID.child("Schedule").child("Shift: "+numID.getText().toString()).child("End Shift").setValue(endTime.getText().toString());



                userIdRefID.child("sch").child(numID.getText().toString()).setValue
                        ("\nPlace: "+ place.getText().toString()+ "\nShift: " +numID.getText().toString()
                                + "\nEmployee: "+ uID.getText().toString()+"\nDate: "+ title.getText().toString()
                                + "\nStart Time:" + message.getText().toString()+ "\nEnd Time: " +
                                endTime.getText().toString());

                addShiftToBus();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Method to check if a business exists in the database or not.
     * If the business entered by the user does not exist, alert user.
     * Otherwise check the employee;
     * */
    public void checkBusiness(){

        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");

        businessRef = mDatabaseReference.child("businesses");
        businessRef.child(place.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists() || place.getText().toString().equals(null)|| numID.getText().toString().equals(null)||
                        title.getText().toString().equals(null)|| message.getText().toString().equals(null)|| endTime.getText().toString().equals(null)){
                    place.setError("Business does not exist!");
                }else{
                        // Method that adds a shift to users branch under username given by the manager
                    addShift();
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