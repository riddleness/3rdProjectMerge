package com.example.a3rd.Upload.interfaces;

import com.example.a3rd.Upload.models.UploadBody;
import com.example.a3rd.Upload.models.UploadResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UploadInterface {
    @POST("/feed")
    Call<UploadResponse> upLoadFeed(@Body UploadBody params);

}
