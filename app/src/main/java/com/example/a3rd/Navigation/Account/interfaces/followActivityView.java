package com.example.a3rd.Navigation.Account.interfaces;

import com.example.a3rd.Navigation.Account.followModels.FollowRequsetResponse;
import com.example.a3rd.Navigation.Account.followModels.FollowingDeleteResponse;

public interface followActivityView {

    void FollowRequestSuccess(FollowRequsetResponse followRequsetResponse);

    void FollowRequestFailed(String message);

    void FollowingDeleteSuccess(FollowingDeleteResponse followingDeleteResponse);
    void FollowingDeleteFailed(String message);

}
