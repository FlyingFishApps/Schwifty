package edu.montclair.mobilecomputing.r_soltes.schwifty.fragments;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.ShareCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;

import edu.montclair.mobilecomputing.r_soltes.schwifty.R;

import static android.content.Context.NOTIFICATION_SERVICE;


public class NotificationFragment extends Fragment {

    EditText title;
    EditText message;


    public NotificationFragment(){
        // Empty Required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);


        title = (EditText)getView().findViewById(R.id.noti_title);
        message = (EditText)getView().findViewById(R.id.noti_message);
        Button submit = (Button)getView().findViewById(R.id.noti_button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title1=title.getText().toString().trim();
                String body=message.getText().toString().trim();

                NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify=new Notification.Builder
                        (getApplicationContext()).setContentTitle(title).setContentText(body).
                        setContentTitle(subject).setSmallIcon(R.drawable.abc).build();

                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                notif.notify(0, notify);

            }
        });







        return view;
    }


    public void performHeadsUp(View view){
        Intent intent = new Intent(NotificationFragment.this, NotificationFragment.class);
        PendingIntent pi = PendingIntent.getActivity(NotificationFragment.this, 0,intent,0);
        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentTitle("Title")
                .setContentText("the text")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .setVibrate(new long[]{Notification.DEFAULT_VIBRATE})
                .setPriority(Notification.PRIORITY_MAX);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    }


}
