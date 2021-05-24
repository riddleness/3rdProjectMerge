package com.example.a3rd.MainFeed.interfaces;

import com.example.a3rd.MainFeed.Models.HomeResponse;

public interface HomeActivityView {

    void HomeSuccess(HomeResponse homeResponse);

    void HomeFailure(String message);

}
