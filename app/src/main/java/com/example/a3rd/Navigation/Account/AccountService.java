package com.example.a3rd.Navigation.Account;

import android.util.Log;

import com.example.a3rd.Navigation.Account.Models.AccountResponse;
import com.example.a3rd.Navigation.Account.interfaces.AccountActivityView;
import com.example.a3rd.Navigation.Account.interfaces.AccountInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.a3rd.ApplicationClass.getRetrofit;

public class AccountService {

    private final AccountActivityView mAccountActivityView;

    public AccountService(FragmentAccount mAccountActivityView) {
        this.mAccountActivityView = (AccountActivityView) mAccountActivityView;
    }


    void AccountGetTest(int userIdx) {
        final AccountInterface accountInterface = getRetrofit().create(AccountInterface.class);
        accountInterface.getAccount(userIdx).enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                final AccountResponse accountResponse = response.body();
                if (accountResponse == null) {
                    Log.d("test","실패");
                    mAccountActivityView.AccountFailure(null);
                    return;
                }


                Log.d("test","유저정보 넘겨줌");
                mAccountActivityView.AccountSuccess(accountResponse);
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                mAccountActivityView.AccountFailure(null);
                Log.e("test",t.toString());
            }

        });
    }
}
