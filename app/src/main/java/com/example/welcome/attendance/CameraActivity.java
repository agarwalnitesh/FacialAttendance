package com.example.welcome.attendance;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.welcome.attendance.data.model.Post;
import com.example.welcome.attendance.data.remote.APIService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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


public class CameraActivity extends Activity {

      private ImageView imageHolder;
      private final int requestCode = 20;
      private APIService mAPIService;
      private String Base64Image;

      private String clgid;




      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          Bundle bundle= getIntent().getExtras();
          clgid=bundle.getString("college_id");
          setContentView(R.layout.activity_camera);

          imageHolder = (ImageView)findViewById(R.id.Imageprev);
          Button capturedImageButton = (Button)findViewById(R.id.cpic);
          capturedImageButton.setOnClickListener( new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                  startActivityForResult(photoCaptureIntent, requestCode);
              }
          });

           Button uploadImage =(Button)findViewById(R.id.up);
          uploadImage.setOnClickListener( new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  /*mAPIService = ApiUtils.getAPIService();
                  String gallery_name = "faculty_attendance";
                  if( !TextUtils.isEmpty(gallery_name)) {
                      sendPost(gallery_name);
                  }*/
                  String subject_id=clgid;
                  String  gallery_name="faculty_attendance";

                  Post post =new Post(Base64Image,subject_id,gallery_name);
                  sendNetworkRequest(post);

              }
          });



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
                 Toast.makeText(CameraActivity.this,"Yeah!! Success:-"+response.body().getSubject_id().toString(),Toast.LENGTH_SHORT).show();

             }

             @Override
             public void onFailure(Call<Post> call, Throwable t) {

                 Toast.makeText(CameraActivity.this,"Something went wrong !",Toast.LENGTH_SHORT).show();
             }
         });

      }

      @Override
      protected void onActivityResult(int requestCode, int resultCode, Intent data) {
          super.onActivityResult(requestCode, resultCode, data);
          Log.i("before image is saved","in internal");
          if(this.requestCode == requestCode && resultCode == RESULT_OK){
              Bitmap bitmap = (Bitmap)data.getExtras().get("data");


              String partFilename = currentDateFormat();
              storeCameraPhotoInSDCard(bitmap, partFilename);
              Base64Image=base64FromBitmap(bitmap);


              // display the image from SD Card to ImageView Control
              final String storeFilename = "/photo_" + partFilename + ".jpg";
              Bitmap mBitmap = getImageFileFromSDCard(storeFilename);
            /* // FaceTest faceTest =new FaceTest();
              try {
               //   faceTest.detection(Environment.getExternalStorageDirectory()+storeFilename);
                  Log.i("image is saved","for detection");
              } catch (Exception e) {
                  e.printStackTrace();
              }*/
              imageHolder.setImageBitmap(mBitmap);


          }
      }

     /* public void sendPost(String gallery_name) {
          mAPIService.savePost(gallery_name).enqueue(new Callback<Post>() {
              @Override
              public void onResponse(Call<Post> call, Response<Post> response) {

                  if(response.isSuccessful()) {
                      showResponse(response.body().toString());
                      Log.i(TAG, "post submitted to API." + response.body().toString());
                  }
              }

              @Override
              public void onFailure(Call<Post> call, Throwable t) {
                  Log.e(TAG, "Unable to submit post to API.");
              }
          });
      }

      public void showResponse(String response) {
          Log.i("Http call successfully",response);
          Toast.makeText(this,response,Toast.LENGTH_SHORT).show();
      }*/

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
      private Bitmap getImageFileFromSDCard(String filename){
          Bitmap bitmap = null;
          File imageFile = new File(Environment.getExternalStorageDirectory() + filename);
          try {
              FileInputStream fis = new FileInputStream(imageFile);
              bitmap = BitmapFactory.decodeStream(fis);
          } catch (FileNotFoundException e) {
              e.printStackTrace();
          }
          return bitmap;
      }
    protected static String base64FromBitmap(Bitmap image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, 0);
        return encoded;
    }

  }
