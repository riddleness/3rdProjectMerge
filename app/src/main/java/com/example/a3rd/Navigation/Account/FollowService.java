package com.example.a3rd.Navigation.Account;

import android.util.Log;

import com.example.a3rd.Navigation.Account.followModels.FollowRequsetResponse;
import com.example.a3rd.Navigation.Account.followModels.FollowingDeleteResponse;
import com.example.a3rd.Navigation.Account.followModels.followBody;
import com.example.a3rd.Navigation.Account.interfaces.AccountInterface;
import com.example.a3rd.Navigation.Account.interfaces.followActivityView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.a3rd.ApplicationClass.getRetrofit;

public class FollowService {
    private final followActivityView mfollowActivityView;

    public FollowService(followActivityView mfollowActivityView) {
        this.mfollowActivityView = mfollowActivityView;
    }

    //팔로잉 취소

    void followingDelete(int userIdx){
        final AccountInterface followCancelInterface = getRetrofit().create(AccountInterface.class);
        followCancelInterface.followingDelete(userIdx).enqueue(new Callback<FollowingDeleteResponse>() {
            @Override
            public void onResponse(Call<FollowingDeleteResponse> call, Response<FollowingDeleteResponse> response) {
                final FollowingDeleteResponse followingDeleteResponse = response.body();
                if (followingDeleteResponse == null) {
                    Log.d("test","실패");
                    mfollowActivityView.FollowingDeleteFailed(null);
                    return;
                }


                Log.d("test","유저정보 넘겨줌");
                mfollowActivityView.FollowingDeleteSuccess(followingDeleteResponse);
            }

            @Override
            public void onFailure(Call<FollowingDeleteResponse> call, Throwable t) {
                mfollowActivityView.FollowingDeleteFailed(null);
                Log.e("test",t.toString());
            }

        });

    }


    //팔로잉 요청
    void followRequest(int followUserIdx) {
        final AccountInterface followInterface = getRetrofit().create(AccountInterface.class);
        followInterface.followRequest(new followBody(followUserIdx)).enqueue(new Callback<FollowRequsetResponse>() {
            @Override
            public void onResponse(Call<FollowRequsetResponse> call, Response<FollowRequsetResponse> response) {
                final FollowRequsetResponse followRequsetResponse = response.body();
                if (followRequsetResponse == null) {
                    Log.d("test","실패");
                    mfollowActivityView.FollowRequestFailed(null);
                    return;
                }


                Log.d("test","유저정보 넘겨줌");
                mfollowActivityView.FollowRequestSuccess(followRequsetResponse);
            }

            @Override
            public void onFailure(Call<FollowRequsetResponse> call, Throwable t) {
                mfollowActivityView.FollowRequestFailed(null);
                Log.e("test",t.toString());
            }

        });
    }
}
