package edu.montclair.mobilecomputing.r_soltes.schwifty;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.montclair.mobilecomputing.r_soltes.schwifty.utils.Notifications;

public class NotificationPage extends AppCompatActivity {

    @BindView(R.id.noti_title) EditText title;
    @BindView(R.id.noti_message) EditText message;
    @BindView(R.id.noti_button) Button notiBtn;
    private static final int TAG_SIMPLE_NOTIFICATION = 1;
    Snackbar snackbar;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    RelativeLayout activity_notification_page;

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
                createNotification(title.getText().toString().trim(),message.getText().toString().trim());
            }
        });


    }
    private PendingIntent pendingIntentForNotification() {
        //Create the intent you want to show when the notification is clicked
        Intent intent = new Intent(NotificationPage.this, NotificationPage.class);

        //Add any extras (in this case, that you want to relaunch this fragment)


        //This will hold the intent you've created until the notification is tapped.
        PendingIntent pendingIntent = PendingIntent.getActivity(NotificationPage.this, 1, intent, 0);
        return pendingIntent;
    }
    private void showSimpleNotification() {
        //Use the NotificationCompat compatibility library in order to get gingerbread support.
        Notification notification = new NotificationCompat.Builder(NotificationPage.this)
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
                NotificationPage.this.getSystemService(Context.NOTIFICATION_SERVICE);

        //Set a tag so that the same notification doesn't get reposted over and over again and
        //you can grab it again later if you need to.
        notificationManager.notify(TAG_SIMPLE_NOTIFICATION, notification);
    }

    private void createNotification(String nTitle, String nBody) {

        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        String nId = String.valueOf(n);

//        mprogressBar.setVisibility(View.VISIBLE);


        Notifications notification = new Notifications(nTitle,nBody,nId);

        mDatabaseReference.child("notifications").child(nId).setValue(notification);
        snackbar.make(activity_notification_page, "Employee Added!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        title.getText().clear();
        message.getText().clear();
//        mprogressBar.setVisibility(View.GONE);

    }
}
