package com.example.a3rd;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;
// 마이페이지
public class Frag5 extends Fragment {

    private View fragment;
    ImageView img_profile;
    TextView tv_user_id, tv_board_settings, tv_collection, tv_user_settings;
    ImageButton imgbtn_board, imgbtn_collection, imgbtn_user;
    static final String TAG = "파이어베이스";
    MainActivity activity;
    // Firebase에서 데이터 불러오기위한 전역번수 선언
    DatabaseReference mDatabase;


    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        activity = (MainActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment=inflater.inflate(R.layout.frag5, container, false);

        img_profile = fragment.findViewById(R.id.img_profile);
        tv_user_id = fragment.findViewById(R.id.tv_user_id);
        tv_board_settings = fragment.findViewById(R.id.tv_board_settings);
        tv_collection = fragment.findViewById(R.id.tv_collection);
        tv_user_settings = fragment.findViewById(R.id.tv_user_settings);

        // 현재 로그인한 계정 정보 가져오기
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Firebase RealtimeDB에서 정보 가져오기
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // 로그인한 ID 출력하기
        tv_user_id.setText((CharSequence) user.getEmail());

        // Click Event
        // img_board 클릭하면 내 게시글 관리로 이동
        tv_board_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 게시물 관리 Fragment 넣기
                //activity.setFrag(new FragUserEdit());
            }
        });


        // img_collection 클릭하면 내 식물 컬렉션으로 이동
        tv_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 식물 컬렉션 Fragment 넣기
                //activity.setFrag(new FragUserEdit());
            }
        });


        // img_settings 클릭하면 내 정보 변경으로 이동
        tv_user_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 내 정보 변경 Fragment로 이동
                activity.setFrag(new FragUserEdit());
            }
        });

        return fragment;
    }


}
