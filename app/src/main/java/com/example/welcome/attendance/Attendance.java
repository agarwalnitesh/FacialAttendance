package com.example.welcome.attendance;

import android.util.Log;

/**
 * Created by nitesh on 24/3/18.
 */

public class Attendance {

    private String name,roll_no,arrival_time,departure_time,custom;
    public Attendance(String roll_no, String arrival_time,String departure_time) {
        Log.i(" "+roll_no," "+arrival_time);
        this.roll_no = roll_no;
        this.arrival_time = arrival_time;
        this.departure_time=departure_time;
    }
    public Attendance(String roll_no, String arrival_time,String departure_time,String custom) {
        Log.i(" "+roll_no," "+arrival_time);
        this.roll_no = roll_no;
        this.arrival_time = arrival_time;
        this.departure_time=departure_time;
        this.custom=custom;
    }

    public String getName() {
        return name;
    }

    public String getCustom()
    {
        return custom;
    }
    public String getRoll_no() {
        return roll_no;
    }


    public String getDeparture_time() {
        return departure_time;
    }

    public String getArrival_time() {

        return arrival_time;
    }


}
