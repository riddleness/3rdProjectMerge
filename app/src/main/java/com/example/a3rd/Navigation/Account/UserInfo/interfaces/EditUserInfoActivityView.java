package com.example.a3rd.Navigation.Account.UserInfo.interfaces;

import com.example.a3rd.Navigation.Account.UserInfo.Models.EditInfoResponse;

public interface EditUserInfoActivityView {
    void EditUserInfoSuccess(EditInfoResponse editInfoResponse);

    void EditUserInfoFailure(String message);
}
