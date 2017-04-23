package edu.montclair.mobilecomputing.r_soltes.schwifty.fragments;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.ShareCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Random;

import edu.montclair.mobilecomputing.r_soltes.schwifty.R;
import edu.montclair.mobilecomputing.r_soltes.schwifty.utils.Notifications;

import static android.content.Context.NOTIFICATION_SERVICE;
import static edu.montclair.mobilecomputing.r_soltes.schwifty.R.layout.fragment_notification;


public class NotificationFragment extends Fragment {

    EditText title;
    EditText message;
    private static final int TAG_SIMPLE_NOTIFICATION = 1;
    Snackbar snackbar;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;


    public NotificationFragment(){
        // Empty Required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(fragment_notification, container, false);
        title = (EditText)view.findViewById(R.id.noti_title);
        message = (EditText)view.findViewById(R.id.noti_message);
        Button simple = (Button)view.findViewById(R.id.noti_button);
        simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSimpleNotification();
                createNotification(title.getText().toString().trim(),message.getText().toString().trim());
            }
        });


        return view;
    }
    private PendingIntent pendingIntentForNotification() {
        //Create the intent you want to show when the notification is clicked
        Intent intent = new Intent(getActivity(), NotificationFragment.class);

        //Add any extras (in this case, that you want to relaunch this fragment)


        //This will hold the intent you've created until the notification is tapped.
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 1, intent, 0);
        return pendingIntent;
    }
    private void showSimpleNotification() {
        //Use the NotificationCompat compatibility library in order to get gingerbread support.
        Notification notification = new NotificationCompat.Builder(getActivity())
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
                getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

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
        snackbar.make(getView(), "Employee Added!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        title.getText().clear();
        message.getText().clear();
//        mprogressBar.setVisibility(View.GONE);

    }

}
