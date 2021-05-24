package com.example.a3rd.Navigation.Account.UserInfo.interfaces;

import com.example.a3rd.Navigation.Account.UserInfo.Models.UserInfoResponse;

public interface UserInfoActivityView {

    void UserInfoSuccess(UserInfoResponse userInfoResponse);

    void UserInfoFailure(String message);

}
