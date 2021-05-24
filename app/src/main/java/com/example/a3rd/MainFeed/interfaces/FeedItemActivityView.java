package com.example.a3rd.MainFeed.interfaces;


import com.example.a3rd.MainFeed.Models.FeedLikeResponse;
import com.example.a3rd.MainFeed.Models.FeedResponse;

public interface FeedItemActivityView {
    void FeedItemSuccess(FeedResponse feedResponse);

    void FeedItemFailure(String message);

    void FeedUserItemSuccess(FeedResponse feedResponse);

    void FeedUserItemFailure(String message);


    void FeedLikeSuccess(FeedLikeResponse feedLikeResponse);
    void FeedLikeFailure(String message);

}
