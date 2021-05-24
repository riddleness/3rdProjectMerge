package com.example.a3rd.MainFeed;

import android.util.Log;

import com.example.a3rd.MainFeed.Models.FeedLikeResponse;
import com.example.a3rd.MainFeed.Models.FeedResponse;
import com.example.a3rd.MainFeed.interfaces.FeedItemActivityView;
import com.example.a3rd.MainFeed.interfaces.HomeInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.a3rd.ApplicationClass.getRetrofit;

public class FeedItemService {
    private final FeedItemActivityView mFeedItemActivityView;

    FeedItemService(final FeedItemActivityView feedItemActivityView) {
        this.mFeedItemActivityView = feedItemActivityView;
    }

    void GetFeedList() {
        final HomeInterface homeInterface = getRetrofit().create(HomeInterface.class);
        homeInterface.GetFeedList().enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                final FeedResponse feedResponse = response.body();
                if (feedResponse == null) {
                    System.out.println("feed 실패");

                    mFeedItemActivityView.FeedItemFailure(null);
                    return;
                }
                System.out.println("feed 성공");
                mFeedItemActivityView.FeedItemSuccess(feedResponse);
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                mFeedItemActivityView.FeedItemFailure(null);
                System.out.println("feed 통신실패");

                Log.e("test",t.toString());
            }
        });
    }

    void GetUserFeedList(int feedId) {
        final HomeInterface homeInterface = getRetrofit().create(HomeInterface.class);
        homeInterface.GetUserFeedList(feedId).enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                final FeedResponse feedResponse = response.body();
                if (feedResponse == null) {
                    System.out.println("feed 실패");

                    mFeedItemActivityView.FeedUserItemFailure(null);
                    return;
                }
                System.out.println("feed 성공");
                mFeedItemActivityView.FeedUserItemSuccess(feedResponse);
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                mFeedItemActivityView.FeedUserItemFailure(null);
                System.out.println("feed 통신실패");

                Log.e("test",t.toString());
            }
        });
    }


    void PostFeedLike(int FeedId) {
        final HomeInterface homeInterface = getRetrofit().create(HomeInterface.class);
        homeInterface.FeedLike(FeedId).enqueue(new Callback<FeedLikeResponse>() {
            @Override
            public void onResponse(Call<FeedLikeResponse> call, Response<FeedLikeResponse> response) {
                final FeedLikeResponse feedLikeResponse = response.body();
                if (feedLikeResponse == null) {
                    Log.d("test","실패");
                    mFeedItemActivityView.FeedLikeFailure(null);
                    return;
                }

                Log.d("test","유저정보 넘겨줌");
                mFeedItemActivityView.FeedLikeSuccess(feedLikeResponse);
            }

            @Override
            public void onFailure(Call<FeedLikeResponse> call, Throwable t) {
                mFeedItemActivityView.FeedLikeFailure(null);
                Log.e("test",t.toString());
            }

        });
    }

}
