package com.example.a3rd.Navigation.Account.UserInfo;

import android.util.Log;

import com.example.a3rd.Navigation.Account.UserInfo.Models.EditInfoBody;
import com.example.a3rd.Navigation.Account.UserInfo.Models.EditInfoResponse;
import com.example.a3rd.Navigation.Account.UserInfo.interfaces.EditUserInfoActivityView;
import com.example.a3rd.Navigation.Account.UserInfo.interfaces.UserInfoInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.a3rd.ApplicationClass.getRetrofit;

public class EditInfoService {
    private final EditUserInfoActivityView mEditUserInfoActivityView;

    EditInfoService(final EditUserInfoActivityView editUserInfoActivityView) {
        this.mEditUserInfoActivityView = editUserInfoActivityView;
    }

    void editUserInfo(String uri,String userName, String userId, String profileIntro, String profileWebsite) {
        final UserInfoInterface userInfoInterface = getRetrofit().create(UserInfoInterface.class);
        userInfoInterface.editUserInfo(new EditInfoBody(uri,userName, userId, profileIntro, profileWebsite)).enqueue(new Callback<EditInfoResponse>() {
            @Override
            public void onResponse(Call<EditInfoResponse> call, Response<EditInfoResponse> response) {
                final EditInfoResponse editInfoResponse = response.body();
                if (editInfoResponse == null) {
                    mEditUserInfoActivityView.EditUserInfoFailure(null);
                    Log.d("test", "실패");
                    return;
                }

                Log.d("test", "edit성공");
                mEditUserInfoActivityView.EditUserInfoSuccess(editInfoResponse);

            }

            @Override
            public void onFailure(Call<EditInfoResponse> call, Throwable t) {
                mEditUserInfoActivityView.EditUserInfoFailure(null);
                Log.e("test", t.toString());
            }
        });
    }
}
