package com.example.welcome.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Passcode extends AppCompatActivity {
    private EditText medit_pass;
    private Button mbtn_pass;
    private String pass;
    public int finalvalue=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);
        medit_pass = (EditText) findViewById(R.id.edit_pass);
        medit_pass.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        Onpass(); }

    public int Onpass() {
        mbtn_pass = (Button) findViewById(R.id.btn_pass);


        mbtn_pass.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pass = medit_pass.getText().toString();
                        finalvalue=Integer.parseInt(pass);
                        if (finalvalue==8899) {
                            Intent intent = new Intent(Passcode.this, afterPasscode.class);
                            startActivity(intent);
                            medit_pass.setText("");
                        } else {
                            Toast.makeText(Passcode.this, "INVALID PASSCODE", Toast.LENGTH_SHORT).show();

                        }


                    }

                }

        );
        return 0;}

}

