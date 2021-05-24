package com.example.a3rd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class FragUserEdit extends Fragment {

    private View fragment;
    EditText et_editPw, et_editPw_submit, et_edit_phone;
    TextView tv_userEdit_back, tv_input_username, tv_logout;
    Button btn_imgchange, btn_edit_commit;
    MainActivity activity;
    String editPw, editPhone;
    private DatabaseReference myRef;
    private Context context;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        activity = (MainActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        fragment = inflater.inflate(R.layout.fragment_frag_user_edit, container, false);

        myRef = FirebaseDatabase.getInstance().getReference();

        et_editPw = fragment.findViewById(R.id.et_editPw);
        et_editPw_submit = fragment.findViewById(R.id.et_editPw_submit);
        et_edit_phone = fragment.findViewById(R.id.et_edit_phone);
        tv_userEdit_back = fragment.findViewById(R.id.tv_userEdit_back);
        tv_input_username = fragment.findViewById(R.id.tv_input_username);
        tv_logout = fragment.findViewById(R.id.tv_logout);
        btn_imgchange = fragment.findViewById(R.id.btn_imgchange);
        btn_edit_commit = fragment.findViewById(R.id.btn_edit_commit);

        // tv_userEdit_back 클릭 시 마이페이지 fragment로 화면 전환
        tv_userEdit_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setFrag(new Frag5());
            }
        });


        // tv_logout 누르면 LOUOUT
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        // btn_edit_commit 누르면 PW, PHONE DB 데이터 Update
        btn_edit_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPw = et_editPw.getText().toString();
                editPhone = et_edit_phone.getText().toString();

                if(!editPw.equals("")){
                    // 비밀번호 변경 Method 
                    updatePassword(editPw);
                }else if(!editPhone.equals("")){
                    myRef.child("phone");
                    // update 완료 후 Frag5로 이동
                    //activity.setFrag(new Frag5());
                }else if(!editPhone.equals("") && !editPw.equals("")){
                    myRef.child("pw");
                    myRef.child("phone");
                    // update 완료 후 Frag5로 이동
                    //activity.setFrag(new Frag5());
                }
            }
        });

        return fragment;
    }

    // PW 번경 Method
    public void updatePassword(String password) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = editPw;

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(activity, "변경완료!", Toast.LENGTH_SHORT).show();
                            // update 완료 후 Frag5로 이동
                            activity.setFrag(new Frag5());
                        }
                    }
                });
    }



    // 로그아웃 Method
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }
}