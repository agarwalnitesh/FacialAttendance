package com.example.welcome.attendance;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Callender extends AppCompatActivity {
    CalendarView calendarView;
    DatabaseHelper myDb;
    String date;

    SearchAdapter adapter;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callender);
        myDb = new DatabaseHelper(this);
        calendarView=(CalendarView)findViewById(R.id.calendarView);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = dayOfMonth+"/"+(month+1)+"/"+year;
                //Toast.makeText(Callender.this,""+date,Toast.LENGTH_SHORT).show();
                show();



            }
        });
    }

    public void show()
    {
        lv = (ListView)findViewById(R.id.list3);
        //arrayList=new ArrayList<>();

        ArrayList<Attendance> attendance = new ArrayList<>();

        //Toast.makeText(Callender.this,""+date,Toast.LENGTH_SHORT).show();
        Cursor cursor= myDb.searchByDate(date);
        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {

                //name.setText(cursor.getString(0).toString());
                attendance.add(new Attendance(cursor.getString(0).toString(),cursor.getString(2).toString(),cursor.getString(3).toString(),cursor.getString(4).toString()));

            }
            adapter =new SearchAdapter(this,attendance);
            lv.setAdapter(adapter);
        }
        else
        {
            Toast.makeText(Callender.this,"No data are available",Toast.LENGTH_SHORT).show();
        }

    }
}
