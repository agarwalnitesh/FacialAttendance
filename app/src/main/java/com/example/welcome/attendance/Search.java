package com.example.welcome.attendance;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    EditText txtId;
    Button btnSearch;
    ListView lv;
    DatabaseHelper myDb;
    SearchAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        myDb=new DatabaseHelper(this);
        txtId = (EditText) findViewById(R.id.txt_id1);
        btnSearch=(Button) findViewById(R.id.btn_id);
        lv=(ListView) findViewById(R.id.list2);
        //arrayList=new ArrayList<>();
        //adapter =new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtId.getText().toString().equals(""))
                {
                    Toast.makeText(Search.this,"Enter College Id",Toast.LENGTH_SHORT).show();
                }
                else
                {
//                    arrayList.clear();
                    show();
                }
            }
        });
    }

    public void show()
    {

        lv = (ListView)findViewById(R.id.list2);
        //arrayList=new ArrayList<>();

        ArrayList<Attendance> attendance = new ArrayList<>();
        Cursor cursor= myDb.searchAttendance(txtId.getText().toString());
        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {

                //name.setText(cursor.getString(0).toString());
                //Toast.makeText(Search.this,"Search is called"+cursor.getString(1).toString(),Toast.LENGTH_SHORT).show();
                attendance.add(new Attendance(cursor.getString(1).toString(),cursor.getString(2).toString(),cursor.getString(3).toString(),cursor.getString(4).toString()));

                //adapter.notifyDataSetChanged();

            }
            adapter =new SearchAdapter(this,attendance);
            lv.setAdapter(adapter);
        }
        else
        {
            Toast.makeText(Search.this,"No data are available",Toast.LENGTH_SHORT).show();
        }

    }
}
