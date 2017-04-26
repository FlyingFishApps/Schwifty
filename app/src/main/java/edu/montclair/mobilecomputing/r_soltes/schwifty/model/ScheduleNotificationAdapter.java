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

import java.util.List;

import edu.montclair.mobilecomputing.r_soltes.schwifty.R;

/**
 * Created by ryansoltes on 4/23/17.
 */

public class ScheduleNotificationAdapter extends ArrayAdapter<ScheduleNotification> {

    public ScheduleNotificationAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ScheduleNotification> objects){
        super(context, resource, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null)

            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.schedule_notification_item,parent,false);

        TextView notifDate = (TextView)convertView.findViewById(R.id.notification_date_SCI);
        TextView notifsID = (TextView)convertView.findViewById(R.id.notification_sID_SCI);
        TextView notifuID = (TextView)convertView.findViewById(R.id.notification_uID_SCI);
        TextView notifSTime = (TextView)convertView.findViewById(R.id.notification_start_time_SCI);
        TextView notifETime = (TextView)convertView.findViewById(R.id.notification_end_time_SCI);

        ScheduleNotification notification = getItem(position);

        notifsID.setText(notification.getsID());
        notifuID.setText(notification.getuID());
        notifDate.setText(notification.getnDate());
        notifSTime.setText(notification.getnStartTime());
        notifETime.setText(notification.getnEndTime());

        return convertView;

    }

}
