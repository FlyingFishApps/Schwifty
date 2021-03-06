package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.montclair.mobilecomputing.r_soltes.schwifty.model.NotificationAdapter;
import edu.montclair.mobilecomputing.r_soltes.schwifty.model.Notifications;

public class NotificationPageManager extends AppCompatActivity implements View.OnClickListener {

    private TextView DateEtxt;
    @BindView(R.id.noti_title) EditText title;
    @BindView(R.id.noti_date) TextView date;
    @BindView(R.id.noti_message) EditText message;
    @BindView(R.id.noti_button) Button notiBtn;
    @BindView(R.id.noti_list) ListView mListView;

    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private static final int TAG_SIMPLE_NOTIFICATION = 1;
    private DatabaseReference mDatabaseReference, notifRef;
    private FirebaseAuth mFirebaseAuth;

    Snackbar snackbar;
    RelativeLayout activity_notification_page;
    NotificationAdapter mNotificationAdapter;
    ChildEventListener mChildEventListener;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);

        ButterKnife.bind(this);
        activity_notification_page = (RelativeLayout)findViewById(R.id.activity_notification_page);
        notiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSimpleNotification();
                createNotification(date.getText().toString().trim(),title.getText().toString().trim(),message.getText().toString().trim());
            }
        });

        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        findViewsById();
        setDateTimeField();

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
    private PendingIntent pendingIntentForNotification() {
        //Create the intent you want to show when the notification is clicked
        Intent intent = new Intent(NotificationPageManager.this, NotificationPageManager.class);

        //Add any extras (in this case, that you want to relaunch this fragment)


        //This will hold the intent you've created until the notification is tapped.
        PendingIntent pendingIntent = PendingIntent.getActivity(NotificationPageManager.this, 1, intent, 0);
        return pendingIntent;
    }
    private void showSimpleNotification() {
        //Use the NotificationCompat compatibility library in order to get gingerbread support.
        Notification notification = new NotificationCompat.Builder(NotificationPageManager.this)
                //Title of the notification
                .setContentTitle(title.getText().toString().trim())
                //Content of the notification once opened
                .setContentText(message.getText().toString().trim())
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
                NotificationPageManager.this.getSystemService(Context.NOTIFICATION_SERVICE);

        //Set a tag so that the same notification doesn't get reposted over and over again and
        //you can grab it again later if you need to.
        notificationManager.notify(TAG_SIMPLE_NOTIFICATION, notification);
    }

    private void createNotification(String nDate, String nTitle, String nBody) {

        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        String nId = String.valueOf(n);

        Notifications notification = new Notifications(nDate,nTitle,nBody,nId);

        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");
        notifRef = mDatabaseReference.child("notifications");
        mDatabaseReference.child("notifications").push().setValue(notification);
        snackbar.make(activity_notification_page, "Notification Sent!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        mNotificationAdapter.clear();
        title.getText().clear();
        message.getText().clear();

    }

    private void findViewsById() {
        DateEtxt = (TextView) findViewById(R.id.noti_date);
        DateEtxt.setInputType(InputType.TYPE_NULL);
        DateEtxt.requestFocus();


    }

    private void setDateTimeField() {
        DateEtxt.setOnClickListener(this);


        java.util.Calendar newCalendar = java.util.Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                java.util.Calendar newDate = java.util.Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                DateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(java.util.Calendar.YEAR), newCalendar.get(java.util.Calendar.MONTH), newCalendar.get(java.util.Calendar.DAY_OF_MONTH));


    }

    @Override
    public void onClick(View view) {
        if(view == DateEtxt) {
            fromDatePickerDialog.show();
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NotificationPageManager.this, HomePage.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }


}
