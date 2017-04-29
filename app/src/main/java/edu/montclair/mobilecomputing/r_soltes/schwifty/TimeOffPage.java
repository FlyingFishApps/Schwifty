package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.montclair.mobilecomputing.r_soltes.schwifty.model.ManagerNotifications;
import edu.montclair.mobilecomputing.r_soltes.schwifty.model.ManagerNotificationsAdapter;
import edu.montclair.mobilecomputing.r_soltes.schwifty.model.NotificationAdapter;
import edu.montclair.mobilecomputing.r_soltes.schwifty.model.Notifications;

public class TimeOffPage extends AppCompatActivity implements View.OnClickListener {
    private TextView fromDateEtxt;
    private TextView toDateEtxt;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    @BindView(R.id.showDate) TextView title;
    @BindView(R.id.showDate2) TextView title1;
    @BindView(R.id.emp_Name) EditText title2;
    @BindView(R.id.emp_Job) EditText wPlace;
    @BindView(R.id.btn_Submit) Button notiBtn;
    @BindView(R.id.noti_list1)
    ListView mListView;
    private static final int TAG_SIMPLE_NOTIFICATION = 1;
    Snackbar snackbar;
    private DatabaseReference mDatabaseReference, notifRef;
    private FirebaseAuth mFirebaseAuth;
    RelativeLayout activity_time_off_page;
    ManagerNotificationsAdapter mNotificationAdapter;
    ChildEventListener mChildEventListener;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    @BindView(R.id.timeoff_spinner) Spinner s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_off_page);
        ButterKnife.bind(this);

        activity_time_off_page = (RelativeLayout)findViewById(R.id.activity_time_off_page);
        notiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSimpleNotification();
                createNotification(title.getText().toString().trim(),s.getSelectedItem().toString().trim(),title1.getText().toString().trim(),title2.getText().toString().trim());
            }
        });

        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        findViewsById();
        setDateTimeField();

        //lines of code below creates the dropdown to select your major
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TimeOffPage.this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        List<ManagerNotifications> listOfNotifis = new ArrayList<>();
        notifRef = FirebaseDatabase.getInstance().getReference("manager_notifications");
        mFirebaseAuth = FirebaseAuth.getInstance();

        mNotificationAdapter = new ManagerNotificationsAdapter(this, R.layout.manager_notification_item, listOfNotifis);
        mListView.setAdapter(mNotificationAdapter);

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ManagerNotifications notification = dataSnapshot.getValue(ManagerNotifications
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
    private PendingIntent pendingIntentForNotification() {
        //Create the intent you want to show when the notification is clicked
        Intent intent = new Intent(TimeOffPage.this, TimeOffPage.class);

        //Add any extras (in this case, that you want to relaunch this fragment)


        //This will hold the intent you've created until the notification is tapped.
        PendingIntent pendingIntent = PendingIntent.getActivity(TimeOffPage.this, 1, intent, 0);
        return pendingIntent;
    }
    private void showSimpleNotification() {
        //Use the NotificationCompat compatibility library in order to get gingerbread support.
        Notification notification = new NotificationCompat.Builder(TimeOffPage.this)
                //Title of the notification
                .setContentTitle(title.getText().toString().trim())
                //Content of the notification once opened
                .setContentText(s.getSelectedItem().toString().trim())
                //This bit will show up in the notification area in devices that support that
                //Icon that shows up in the notification area
                .setSmallIcon(R.mipmap.ic_launcher)
                //Icon that shows up in the drawer
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                //Set the intent
                .setContentIntent(pendingIntentForNotification())
                //Build the notification with all the stuff you've just set.
                .build();

        //Add the auto-cancel flag to make it dismiss when clicked on
        //This is a bitmask value so you have to pipe-equals it.
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        //Grab the NotificationManager and post the notification
        NotificationManager notificationManager = (NotificationManager)
                TimeOffPage.this.getSystemService(Context.NOTIFICATION_SERVICE);

        //Set a tag so that the same notification doesn't get reposted over and over again and
        //you can grab it again later if you need to.
        notificationManager.notify(TAG_SIMPLE_NOTIFICATION, notification);
    }

    private void createNotification(String dStart, String nReason, String dEnd, String name) {

        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        String nId = String.valueOf(n);

        ManagerNotifications notification = new ManagerNotifications(dStart,nReason,nId,dEnd,name);

        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");
        notifRef = mDatabaseReference.child("businesses").child(wPlace.getText().toString());
        notifRef.child("manager_notifications").setValue(notification);
        snackbar.make(activity_time_off_page, "Notification Sent!", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
        mNotificationAdapter.clear();
        mNotificationAdapter.clear();

}



    private void findViewsById() {
        fromDateEtxt = (TextView) findViewById(R.id.showDate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

        toDateEtxt = (TextView) findViewById(R.id.showDate2);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
    }

    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);

        java.util.Calendar newCalendar = java.util.Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                java.util.Calendar newDate = java.util.Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(java.util.Calendar.YEAR), newCalendar.get(java.util.Calendar.MONTH), newCalendar.get(java.util.Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                java.util.Calendar newDate = java.util.Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(java.util.Calendar.YEAR), newCalendar.get(java.util.Calendar.MONTH), newCalendar.get(java.util.Calendar.DAY_OF_MONTH));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bottom_navigation_main, menu);
        return true;
    }



    @Override
    public void onClick(View view) {
        if(view == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if(view == toDateEtxt) {
            toDatePickerDialog.show();
        }
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TimeOffPage.this, HomePage.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

}
