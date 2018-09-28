package com.example.welcome.attendance;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class home extends AppCompatActivity {
public TextView ntime,n1time,developer;
public TextView ndate,n1date,name,time,n2date;
public EditText nss;
public static String mdate;
    String dateString;
    Dialog myDialog;
public TextView attendance,register,history,custom_attendance,logout;
    private String clg_id="null",session;
    ListView lv;
    //ArrayList<String> arrayList;
    AttendanceAdapter adapter;
    //AttendanceAdapter madapter;
    DatabaseHelper myDb;


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bundle bundle1=getIntent().getExtras();
       clg_id= getIntent().getStringExtra("clgid");
        //session = getIntent().getStringExtra("session");
    //Bundle bundle= getIntent().getExtras();
    //clg_id=bundle.getString("clgid");
        setContentView(R.layout.activity_home);

    click();

  /*  developer = (TextView) findViewById(R.id.developer);
    developer.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDialog();

        }
    });*/

   //show();

    //Ndate();
    //Ntime();



    Thread t = new Thread() {
        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ndate = (TextView) findViewById(R.id.txt_date);
                            ntime=(TextView)findViewById(R.id.txt_time);

                            long date = System.currentTimeMillis();
                            SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
                            SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm:ss a");

                            dateString = sdf.format(date);
                            String timeString=sdf1.format(date);
                            ndate.setText(dateString);
                            ntime.setText(timeString);

                        }
                    });
                }
            } catch (InterruptedException e) {
            }
        }
    };

    t.start();
    myDialog =new Dialog(this);
    custom();
    n2date=(TextView)findViewById(R.id.txt_date);

    name=(TextView)findViewById(R.id.list_name);
    time=(TextView)findViewById(R.id.txt_time);
    logout=(TextView)findViewById(R.id.logout);
    myDb = new DatabaseHelper(this);

    show();

logout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent= new Intent(home.this,Passcode2.class);
        startActivity(intent);

    }
});


    }

    public void custom()
    {
        custom_attendance=(TextView) findViewById(R.id.custom_attendance);
        custom_attendance.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(home.this,Passcode.class);
                startActivity(intent);
            }
        });
    }

   /* public void Ndate() {
        String Date;
        ndate=(TextView)findViewById(R.id.txt_date);
        Date= DateFormat.getDateInstance().format(new Date());
       ndate.setText(Date);


    }
    public void Ntime(){

    String Time;
    ntime=(TextView)findViewById(R.id.txt_time);
    //Time= TimeZoneFormat.getInstanc().format(new Time());
        Date dt=new Date();
        int hours = dt.getHours();
        int minutes = dt.getMinutes();
        //int seconds = dt.getSeconds();
        String curTime = hours + ":" + minutes  ;
    ntime.setText(curTime);


    }*/
 public void click(){
 attendance=(TextView)findViewById(R.id.btn_attendance);
 attendance.setOnClickListener(
        new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n1date=(TextView)findViewById(R.id.txt_date);
                n1time=(TextView)findViewById(R.id.txt_time);
                mdate=n1date.getText().toString();
                Intent intent=new Intent(home.this,AttendanceActivity.class);
                intent.putExtra("dateString",n1date.getText().toString());
                intent.putExtra("timeString",n1time.getText().toString());
                startActivity(intent);
                //onAdd();
            }
        }
);



register=(TextView)findViewById(R.id.btn_register);
register.setOnClickListener(
        new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(home.this,RegisterActivity.class);
                startActivity(intent);

            }
        }
);

    history=(TextView)findViewById(R.id.btn_history);
    history.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(home.this,History.class);
                    startActivity(intent);
                }
            }
    );


}
public void show()
{

    lv = (ListView)findViewById(R.id.listmain);
    //arrayList=new ArrayList<>();

    ArrayList<Attendance> attendance = new ArrayList<>();

    long date1 = System.currentTimeMillis();
    SimpleDateFormat sdf1 = new SimpleDateFormat("d/M/yyyy");
    String dateString1 = sdf1.format(date1);

    Cursor cursor= myDb.showAttendance(dateString1,"P");
    //Toast.makeText(home.this,dateString,Toast.LENGTH_SHORT).show();
    if(cursor.getCount()>0)
    {
        while(cursor.moveToNext())
        {
            //name.setText(cursor.getString(0).toString());
            //Toast.makeText(home.this,"cursor called"+cursor.getString(0).toString()+" "+cursor.getString(2).toString(),Toast.LENGTH_SHORT).show();
            attendance.add(new Attendance(cursor.getString(0).toString(),cursor.getString(2).toString(),cursor.getString(3).toString()));
            //adapter.notifyDataSetChanged();
            //adapter.notifyDataSetChanged();
        }
        adapter =new AttendanceAdapter(this,attendance);
        lv.setAdapter(adapter);



    }
    else
    {
        Toast.makeText(home.this,"No data are available",Toast.LENGTH_SHORT).show();
    }

}


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }


    public void showDialog(View v)
    {
        TextView txtView;
        myDialog.setContentView(R.layout.custompopup);
        txtView = (TextView) myDialog.findViewById(R.id.txt_close);
        txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}