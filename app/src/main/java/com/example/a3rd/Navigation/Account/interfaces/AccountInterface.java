package com.example.a3rd.Navigation.Account.interfaces;

import com.example.a3rd.Navigation.Account.Models.AccountResponse;
import com.example.a3rd.Navigation.Account.followModels.FollowRequsetResponse;
import com.example.a3rd.Navigation.Account.followModels.FollowingDeleteResponse;
import com.example.a3rd.Navigation.Account.followModels.followBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AccountInterface {

    @GET("/user/{userIdx}/feeds")
    Call<AccountResponse> getAccount(@Path("userIdx") int userIdx);

    @DELETE("/user/{userIdx}/following")
    Call<FollowingDeleteResponse> followingDelete(@Path("userIdx") int userIdx);

    @POST("/followrequest")
    Call<FollowRequsetResponse> followRequest(@Body followBody params);


}
