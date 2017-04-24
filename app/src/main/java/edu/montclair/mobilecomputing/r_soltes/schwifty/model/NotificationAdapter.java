package edu.montclair.mobilecomputing.r_soltes.schwifty.model;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.montclair.mobilecomputing.r_soltes.schwifty.R;

/**
 * Created by ryansoltes on 4/23/17.
 */

public class NotificationAdapter extends ArrayAdapter<Notifications> {

    public NotificationAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Notifications> objects){
        super(context, resource, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null)

            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.notification_item,parent,false);

        TextView notifTitle = (TextView)convertView.findViewById(R.id.notification_title);
        TextView notifBody = (TextView)convertView.findViewById(R.id.notification_body);

        Notifications notification = getItem(position);

        notifTitle.setText(notification.getnTitle());
        notifBody.setText(notification.getnBody());

        return convertView;

    }

}
