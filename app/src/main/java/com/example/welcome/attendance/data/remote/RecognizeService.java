package com.example.welcome.attendance.data.remote;

import com.example.welcome.attendance.data.model.Recognise;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by nitesh on 21/3/18.
 */

public interface RecognizeService {
    @Headers({
            "app_id:75da2413",
            "app_key:830315669cb5a7da377bd5199af651d8"

    })
    @POST("/recognize")
    Call<Recognise> saveRecognise(@Body Recognise recognise);

}
