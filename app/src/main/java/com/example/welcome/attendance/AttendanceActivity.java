package com.example.welcome.attendance;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.welcome.attendance.data.model.Recognise;
import com.example.welcome.attendance.data.remote.RecognizeService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AttendanceActivity extends AppCompatActivity {
private CardView morning,evening;
private String Base64image;
private String college_id,session;
private String timeString,dateString;
    DatabaseHelper myDb1;
    private final int requestCode = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timeString=getIntent().getStringExtra("timeString");
        dateString=getIntent().getStringExtra("dateString");
        setContentView(R.layout.attendance);
        myDb1= new DatabaseHelper(this);

        //Toast.makeText(AttendanceActivity.this,"date="+dateString+"time="+timeString,Toast.LENGTH_SHORT).show();

        morning=(CardView) findViewById(R.id.morning_attendance);
        morning.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
                        session="arrival";
                        startActivityForResult(intent,requestCode);

                        //startActivity(intent);

                    }
                }
        );

        evening=(CardView) findViewById(R.id.evening_attendance);
        evening.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
                        session="departure";
                        startActivityForResult(intent,requestCode);


                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(this.requestCode == requestCode && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");

            String partFilename = currentDateFormat();
            String gallery_name="faculty_attendance";
            Log.i("Store Method","Is Called");
            //storeCameraPhotoInSDCard(bitmap, partFilename);
            Base64image=base64FromBitmap(bitmap);
            Recognise recognise=new Recognise(Base64image,gallery_name);
            sendNetworkRequest(recognise);
            Log.i("In SDCard","Succeed");
            // display the image from SD Card to ImageView Control
            String storeFilename = "/photo_" + partFilename + ".jpg";
           // Bitmap mBitmap = getImageFileFromSDCard(storeFilename);
            //imageHolder.setImageBitmap(mBitmap);
        }
    }
    private void sendNetworkRequest(Recognise recognise)
    {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.kairos.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        RecognizeService recognizeService = retrofit.create(RecognizeService.class);
        Call<Recognise> call = recognizeService.saveRecognise(recognise);
        call.enqueue(new Callback<Recognise>() {
            @Override
            public void onResponse(Call<Recognise> call, Response<Recognise> response) {
                college_id=response.body().getSubject_id().toString();
                Toast.makeText(AttendanceActivity.this,"Face Recognized:-"+response.body().getSubject_id().toString(),Toast.LENGTH_SHORT).show();
                onAdd();
                Intent intent = new Intent(AttendanceActivity.this,home.class);
                intent.putExtra("clg_id",college_id);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<Recognise> call, Throwable t) {

                Toast.makeText(AttendanceActivity.this,"Something went wrong !",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    private void storeCameraPhotoInSDCard(Bitmap bitmap, String currentDate){
        File outputFile = new File(Environment.getExternalStorageDirectory(), "photo_" + currentDate + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected static String base64FromBitmap(Bitmap image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, 0);
        return encoded;
    }
    public void onSession() {
        //Toast.makeText(AttendanceActivity.this, "session" + session, Toast.LENGTH_SHORT).show();
    }

    public void onAdd()
    {

            if(session=="arrival")
            {
                boolean alreadyPresent = myDb1.alreadyPresent(college_id,dateString);
                if(alreadyPresent==true)
                {

                    boolean isInserted=myDb1.insertAttendance(college_id,dateString,timeString,"--","-","P");
                    if(isInserted==true)
                    {
                        //Toast.makeText(AttendanceActivity.this,""+college_id+""+session.toString(),Toast.LENGTH_SHORT).show();
                        Toast.makeText(AttendanceActivity.this,"Attendance is Registed",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(AttendanceActivity.this,"Sorry!!make One more try",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(AttendanceActivity.this,"You already mark your Attendance",Toast.LENGTH_SHORT).show();


            }
            else
            {

                //boolean alreadyDeparture = myDb1.alreadyDeparture(college_id,dateString);
                //if(alreadyDeparture==false)
                //{
                    boolean isInserted =myDb1.updateAttendance(college_id,dateString,timeString);
                    if(isInserted==true)
                    {
                        Toast.makeText(AttendanceActivity.this,"Attendance is marked",Toast.LENGTH_SHORT).show();

                    }
                    else
                        Toast.makeText(AttendanceActivity.this,"Sorry! try One More time..",Toast.LENGTH_SHORT).show();
               // }
               // else
                 //   Toast.makeText(AttendanceActivity.this,"You are already Departed",Toast.LENGTH_SHORT).show();



            }
        }

    }

