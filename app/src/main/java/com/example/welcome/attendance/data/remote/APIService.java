package com.example.welcome.attendance.data.remote;

import com.example.welcome.attendance.data.model.Post;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by nitesh on 20/3/18.
 */

public interface APIService {
    @Headers({
            "app_id:75da2413",
            "app_key:830315669cb5a7da377bd5199af651d8"

    })
    @POST("/enroll")
    Call<Post> savePost(@Body Post post);
   /* @POST("/enroll")
    @FormUrlEncoded
    Call<Post> savePost(@Field("confidence") String confidence,
                        @Field("face_id") String face_id,
                        @Field("gallery_name") long gallery_name);*/

}
