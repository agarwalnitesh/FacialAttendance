package com.example.welcome.attendance;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nitesh on 26/3/18.
 */

public class AttendanceAdapter extends ArrayAdapter<Attendance> {


    public AttendanceAdapter(Activity context , ArrayList<Attendance> attendance) {
        super(context,0, attendance);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item1,parent,false);
        }
        Attendance attendance=getItem(position);
        TextView listName =(TextView) listItemView.findViewById(R.id.list_name);
        Log.i("Attendance Activity",""+attendance.getRoll_no());
        //Toast.makeText(this,attendance.getRoll_no(),Toast.LENGTH_SHORT).show();
        listName.setText(attendance.getRoll_no());

        TextView listArrival =(TextView) listItemView.findViewById(R.id.list_arrival);
        listArrival.setText(attendance.getArrival_time());

        TextView listDeparture =(TextView) listItemView.findViewById(R.id.list_departure);
        listDeparture.setText(attendance.getDeparture_time());

        return listItemView;
    }
}
