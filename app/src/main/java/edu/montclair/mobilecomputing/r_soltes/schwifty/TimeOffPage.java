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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.montclair.mobilecomputing.r_soltes.schwifty.model.ManagerNotifications;
import edu.montclair.mobilecomputing.r_soltes.schwifty.model.ManagerNotificationsAdapter;

public class TimeOffPage extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.showDate) TextView sDate;
    @BindView(R.id.showDate2) TextView eDate;
    @BindView(R.id.emp_Name) EditText nameEMP;
    @BindView(R.id.emp_Job) EditText wPlace;
    @BindView(R.id.btn_Submit) Button notiBtn;
    @BindView(R.id.timeoff_spinner) Spinner s;

    private TextView fromDateEtxt;
    private TextView toDateEtxt;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private static final int TAG_SIMPLE_NOTIFICATION = 1;
    private DatabaseReference mDatabaseReference, notifRef;
    private FirebaseAuth mFirebaseAuth;

    Snackbar snackbar;
    RelativeLayout activity_time_off_page;

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
                createNotification(sDate.getText().toString().trim(),s.getSelectedItem().toString().trim(),
                        eDate.getText().toString().trim(), nameEMP.getText().toString().trim());
            }
        });

        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        findViewsById();
        setDateTimeField();


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
                .setContentTitle(sDate.getText().toString().trim())
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

        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");
        notifRef = mDatabaseReference.child("businesses").child(wPlace.getText().toString());
        notifRef.child("manager_notifications").child(name).setValue("\nTime Off Request \nStart Date: " + sDate.getText().toString() + "\nEnd Date :"
        + eDate.getText().toString() + "\nReason: " + s.getSelectedItem().toString() + "\n Name: " + nameEMP.getText().toString());
        snackbar.make(activity_time_off_page, "Time Off Request Sent!", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
        nameEMP.getText().clear();
        wPlace.getText().clear();
        sDate.setText("Enter Start Date");
        eDate.setText("Enter End Date");
        s.setSelection(0);

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
