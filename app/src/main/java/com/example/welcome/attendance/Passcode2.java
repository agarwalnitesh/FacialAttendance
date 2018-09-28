package com.example.welcome.attendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Passcode2 extends AppCompatActivity {


    private EditText editPassword;
    private Button btnPassword;
    private String pass;

    public String finalvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode2);
        editPassword = (EditText) findViewById(R.id.edit_password);
        editPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        Onpass();
    }

    public void Onpass() {
        btnPassword = (Button) findViewById(R.id.btn_logout);


        btnPassword.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pass = editPassword.getText().toString();
                        //finalvalue=Integer.parseInt(pass);
                        if (pass.equals("sbcet")) {
                            Intent intent = new Intent(Passcode2.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(Passcode2.this, "INVALID PASSCODE", Toast.LENGTH_SHORT).show();

                        }


                    }

                }
        );
    }
}
