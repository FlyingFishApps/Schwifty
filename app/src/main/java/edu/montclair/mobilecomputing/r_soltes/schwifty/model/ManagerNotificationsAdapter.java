package edu.montclair.mobilecomputing.r_soltes.schwifty.model;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.montclair.mobilecomputing.r_soltes.schwifty.R;

/**
 * Created by tjame_000 on 4/23/2017.
 */

public class ManagerNotificationsAdapter extends ArrayAdapter<ManagerNotifications> {
    public ManagerNotificationsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ManagerNotifications> objects){
        super(context, resource, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null)

            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.manager_notification_item,parent,false);

        TextView notifTitle = (TextView)convertView.findViewById(R.id.notification_title1);
        TextView notifTitle1 = (TextView)convertView.findViewById(R.id.notification_title2);
        TextView notifBody = (TextView)convertView.findViewById(R.id.notification_body1);
        TextView notifBody1 = (TextView)convertView.findViewById(R.id.notification_body2);

        ManagerNotifications notification = getItem(position);

        notifTitle.setText(notification.getdStart());
        notifTitle1.setText(notification.getdEnd());
        notifBody1.setText(notification.getnReason());
        notifBody.setText(notification.getName());

        return convertView;

    }

}

