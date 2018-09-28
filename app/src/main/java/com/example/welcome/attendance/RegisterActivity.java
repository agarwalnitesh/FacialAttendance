package com.example.welcome.attendance;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.welcome.attendance.data.model.Post;
import com.example.welcome.attendance.data.remote.APIService;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
private Button ncamera;
//private TextView register;
private RelativeLayout register;
private RadioButton btech,mtech,mca;
private RadioGroup radioGroup;
private String course;
private EditText collegeid,editName,editBranch,editContact,editEmail;
    private final int requestCode = 20;
    private APIService mAPIService;
    private String Base64Image;
    private boolean isCaptured=false;
    private boolean radioClicked=false;
DatabaseHelper myDb;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        myDb=new DatabaseHelper(this);
        register=(RelativeLayout) findViewById(R.id.btn_register);
        btech=(RadioButton)findViewById(R.id.radio_btech);
        mtech=(RadioButton)findViewById(R.id.radio_mtech);
        mca=(RadioButton)findViewById(R.id.radio_mca);

        collegeid =(EditText)findViewById(R.id.college_id);
        editName=(EditText)findViewById(R.id.editName);
        radioGroup=(RadioGroup)findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radio_btech)
                {
                    course=btech.getText().toString();
                    radioClicked=true;
                }
                else if(checkedId==R.id.radio_mtech)
                {
                    course=mtech.getText().toString();
                    radioClicked=true;
                }
                else if(checkedId== R.id.radio_mca)
                {
                    course=mca.getText().toString();
                    radioClicked=true;
                }
            }
        });

        editBranch=(EditText)findViewById(R.id.editBranch);

        editEmail=(EditText)findViewById(R.id.editEmail);
        editContact=(EditText)findViewById(R.id.editContact);
                cameraclick();

        register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                if (editName.getText().toString().equals("")) {
                    editName.setError("Enter a valid Name");
                    return;

                }
                if (TextUtils.isEmpty(editBranch.getText().toString())) {
                    editBranch.setError("Enter a valid Branch");
                    return;

                }
                if (TextUtils.isEmpty(collegeid.getText().toString())) {
                    collegeid.setError("Enter a valid College-Id");
                    return;

                }
                if (TextUtils.isEmpty(editEmail.getText().toString())) {
                    editEmail.setError("Enter a valid Email");
                    return;

                }
                if (editEmail.getText().toString().contains("@")) {


                    if (TextUtils.isEmpty(editContact.getText().toString())) {
                        editContact.setError("Enter a valid Contact");
                        return;
                    }
                    if(editContact.getText().toString().length()<10)
                    {
                        editContact.setError("Enter Proper contact with 10 digit");
                    }
                    if (radioClicked == false) {
                        Toast.makeText(RegisterActivity.this, "select your Course", Toast.LENGTH_SHORT).show();

                    }
                    if (isCaptured == true)
                        addData();
                    else
                        Toast.makeText(RegisterActivity.this, "Please Click Your Image First", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    editEmail.setError("Enter proper Email with @");
                    Toast.makeText(RegisterActivity.this, "Select proper email with @", Toast.LENGTH_SHORT).show();

                }

            }
        });



 }
public void addData()
{
    boolean alreadyRegistered= myDb.check_id(collegeid.getText().toString());
    if(alreadyRegistered==true)
    {
        boolean isInserted = myDb.insertData(collegeid.getText().toString(),editName.getText().toString(),course,editBranch.getText().toString(),editEmail.getText().toString(),editContact.getText().toString());
        if(isInserted==true)
        {
            Toast.makeText(RegisterActivity.this,"Successfully Registered!",Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(RegisterActivity.this,home.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(RegisterActivity.this,"Data are not Registered!",Toast.LENGTH_SHORT).show();
        }
    }
    else
        Toast.makeText(RegisterActivity.this,"Already Registered!!",Toast.LENGTH_SHORT).show();

        Intent intent= new Intent(RegisterActivity.this,home.class);
        startActivity(intent);


}

public void cameraclick(){

         boolean alreadyRegistered1= myDb.check_id(collegeid.getText().toString());
         if(alreadyRegistered1==true) {


             ncamera = (Button) findViewById(R.id.btn_camera);
             ncamera.setOnClickListener(
                     new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {

                             Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                             startActivityForResult(photoCaptureIntent, requestCode);
                         }
                     }
             );
         }
         else
              Toast.makeText(RegisterActivity.this,"Already Registered!!",Toast.LENGTH_SHORT).show(); 



}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("before image is saved", "in internal");
        if (this.requestCode == requestCode && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            //String partFilename = currentDateFormat();
            Base64Image = base64FromBitmap(bitmap);
            String subject_id=collegeid.getText().toString();
            String  gallery_name="faculty_attendance";

            Post post =new Post(Base64Image,subject_id,gallery_name);
            sendNetworkRequest(post);



        }
    }

    private void sendNetworkRequest(Post post)
    {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.kairos.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        APIService apiService = retrofit.create(APIService.class);
        Call<Post> call = apiService.savePost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Toast.makeText(RegisterActivity.this,"Image is successfully captured"+response.body().getSubject_id().toString(),Toast.LENGTH_SHORT).show();

                isCaptured=true;
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                Toast.makeText(RegisterActivity.this,"Something went wrong !",Toast.LENGTH_SHORT).show();
            }
        });

    }

    protected static String base64FromBitmap(Bitmap image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, 0);
        return encoded;
    }

    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

  /*  @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }*/
}