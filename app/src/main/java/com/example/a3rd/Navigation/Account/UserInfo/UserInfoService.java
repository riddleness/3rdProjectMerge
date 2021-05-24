package com.example.a3rd.Navigation.Account.UserInfo;

import android.util.Log;

import com.example.a3rd.Navigation.Account.UserInfo.Models.UserInfoResponse;
import com.example.a3rd.Navigation.Account.UserInfo.interfaces.UserInfoActivityView;
import com.example.a3rd.Navigation.Account.UserInfo.interfaces.UserInfoInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.a3rd.ApplicationClass.getRetrofit;

public class UserInfoService {
    private final UserInfoActivityView mUserInfoActivityView;
    UserInfoService(final UserInfoActivityView userInfoActivityView) {
        this.mUserInfoActivityView = userInfoActivityView;
    }

    void getUserInfo() {
        final UserInfoInterface userInfoInterface = getRetrofit().create(UserInfoInterface.class);
        userInfoInterface.getUserInfo().enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                final UserInfoResponse userInfoResponse = response.body();
                if (userInfoResponse == null) {
                    mUserInfoActivityView.UserInfoFailure(null);
                    return;
                }

                mUserInfoActivityView.UserInfoSuccess(userInfoResponse);
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                mUserInfoActivityView.UserInfoFailure(null);
                Log.e("test", t.toString());
            }
        });
    }

}

