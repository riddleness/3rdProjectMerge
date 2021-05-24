package com.example.a3rd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Frag4 extends Fragment {
    //식물 MBTI 페이지
    private View view;
    Button btn_test, btn_book;
    TextView tv_title;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.frag4, container, false);

        tv_title = view.findViewById(R.id.tv_title);
        btn_test = view.findViewById(R.id.btn_test);
        btn_book = view.findViewById(R.id.btn_book);


        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                tv_title.setVisibility(View.GONE);
//                tv_test.setVisibility(View.GONE);

                FragmentManager fm = getFragmentManager();
                mbtiFrag dialFrag = new mbtiFrag();
                dialFrag.show(fm,"mbtiFrag");
            }
        });
        //테스트용 식물도감 이동버튼입니다.
        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              FragmentManager fm = getFragmentManager();
              FragmentTransaction ft = fm.beginTransaction();
              Frag6 frag6 = new Frag6();
              ft.replace(R.id.main_frame, frag6);
              ft.commit();
            }
        });


        return view;
    }
}
