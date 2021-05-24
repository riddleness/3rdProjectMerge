package com.example.a3rd.Navigation.Account.interfaces;

import com.example.a3rd.Navigation.Account.Models.AccountResponse;

public interface AccountActivityView {

    void AccountSuccess(AccountResponse accountResponse);

    void AccountFailure(String message);

}
