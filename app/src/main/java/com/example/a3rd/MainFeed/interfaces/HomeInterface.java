package com.example.a3rd.MainFeed.interfaces;


import com.example.a3rd.MainFeed.Models.CommentBody;
import com.example.a3rd.MainFeed.Models.CommentResponse;
import com.example.a3rd.MainFeed.Models.FeedLikeResponse;
import com.example.a3rd.MainFeed.Models.FeedResponse;
import com.example.a3rd.MainFeed.Models.HomeResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HomeInterface {

    @GET("/signIn/auto")
    Call<HomeResponse> SignInAuto();

    @GET("/feeds")
    Call<FeedResponse> GetFeedList();

    @GET("/feed/{feedId}")
    Call<FeedResponse> GetUserFeedList(
            @Path("feedId") int feedId
    );


    @POST("/feed/{feedId}/like")
    Call<FeedLikeResponse> FeedLike(
            @Path("feedId") int feedId
    );


    @POST("/comment")
    Call<CommentResponse> WriteComment(@Body CommentBody params);



}
