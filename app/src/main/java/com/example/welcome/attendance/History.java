package com.example.welcome.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class History extends AppCompatActivity {
    Button btnId,btnCallender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history);

        btnId=(Button)findViewById(R.id.btn_id);
        btnCallender=(Button)findViewById(R.id.btn_callender);
        //onClick();
        btnCallender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(History.this,Callender.class);
                startActivity(intent);
            }
        });
        btnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(History.this,Search.class);
                startActivity(intent);
            }
        });
    }


}
