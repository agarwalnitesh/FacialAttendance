package com.example.welcome.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
CardView nlogin;
EditText nusername,npassword;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    npassword=(EditText)findViewById(R.id.txt_password);
    npassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());

                Onclick();
    }

    public void Onclick() {
        nlogin=(CardView)findViewById(R.id.login);
nlogin.setOnClickListener(
        new View.OnClickListener() {
            @Override
            public void onClick(View view) {
check();

            }
        }
);
    }

public void check(){
    nusername=(EditText)findViewById(R.id.txt_username);


    String     val1=nusername.getText().toString();
    String     val2=npassword.getText().toString();

   if (val1.equals(""))
   {
       Toast.makeText(MainActivity.this,"enter username",Toast.LENGTH_SHORT).show();}
else if (val2.equals(""))
   {
       Toast.makeText(MainActivity.this,"enter password",Toast.LENGTH_SHORT).show();}

   else if((val1.equals("admin"))&&(val2.equals("sbcet"))) {

        Intent intent = new Intent(MainActivity.this, home.class);
        startActivity(intent);
       nusername.setText("");
       npassword.setText("");
    }
    else
    {
        Toast.makeText(MainActivity.this,"Invalid Login",Toast.LENGTH_SHORT).show();
    }

}

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }

}

