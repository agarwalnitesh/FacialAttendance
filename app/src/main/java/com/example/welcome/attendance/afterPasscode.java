package com.example.welcome.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class afterPasscode extends AppCompatActivity {


    private RadioButton cl, ccl, dl,al;
    private RadioGroup radioGroup;
    String custom;
    boolean radioClicked=false;
    EditText txtId;
    Button  btnUpdate;
    DatabaseHelper myDb;
    CalendarView calender;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_passcode);
        myDb = new DatabaseHelper(this);
        al=(RadioButton)findViewById(R.id.al);
        cl =(RadioButton)findViewById(R.id.cl);
        ccl =(RadioButton)findViewById(R.id.ccl);
        dl =(RadioButton)findViewById(R.id.dl);
        txtId=(EditText)findViewById(R.id.txt_id4);
        calender=(CalendarView) findViewById(R.id.calendarView2);
        //calender=(CalendarView)findViewById(R.id.calendarView);


        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = dayOfMonth+"/"+(month+1)+"/"+year;
                //Toast.makeText(Callender.this,""+date,Toast.LENGTH_SHORT).show();
                calender.setVisibility(View.GONE);
            }
        });
        btnUpdate=(Button)findViewById(R.id.btn_id4);

        radioGroup=(RadioGroup)findViewById(R.id.radio_group4);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.al)
                {
                    custom = al.getText().toString();
                    radioClicked=true;
                }
                else if(checkedId==R.id.cl)
                {
                    custom= cl.getText().toString();
                    radioClicked=true;
                }
                else if(checkedId==R.id.ccl)
                {
                    custom= ccl.getText().toString();
                    radioClicked=true;
                }
                else if(checkedId== R.id.dl)
                {
                    custom= dl.getText().toString();
                    radioClicked=true;
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtId.getText().toString().equals("")||custom.equals("")||date.equals(""))
                {
                    Toast.makeText(afterPasscode.this,"Select All Field Properly!!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    boolean userRegistered=myDb.check_id(txtId.getText().toString());
                    if(userRegistered==false)
                    {
                        boolean alreadyPresent=myDb.alreadyPresent(txtId.getText().toString(),date);
                        if(alreadyPresent==true)
                        {
                            // myDb.updateCustom(txtId.getText().toString(),custom);
                            boolean isUpdated= myDb.insertAttendance(txtId.getText().toString(),date,"--","--",custom,"A");
                            if(isUpdated==true)
                            {
                                Toast.makeText(afterPasscode.this,"Data Updated Successfully!!!",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(afterPasscode.this,home.class);
                                startActivity(intent);
                                calender.setVisibility(View.VISIBLE);

                            }
                            else
                            {
                                Toast.makeText(afterPasscode.this,"Make One more Try!!",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(afterPasscode.this,"You are already present on this Date",Toast.LENGTH_SHORT).show();
                            calender.setVisibility(View.VISIBLE);


                        }
                    }
                    else
                        Toast.makeText(afterPasscode.this,"User Not Registered",Toast.LENGTH_SHORT).show();



                }
            }
        });
    }


}
