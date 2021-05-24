package com.example.a3rd;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JoinActivity extends AppCompatActivity {

    EditText et_join_id, et_join_pw, et_join_nick, et_join_phpne;
    Button btn_join_submit;
    String email, pw, nickname, phone;
    FirebaseAuth mAuth;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        myRef = FirebaseDatabase.getInstance().getReference("UserList");
        mAuth = FirebaseAuth.getInstance();

        et_join_id = findViewById(R.id.et_join_id);
        et_join_pw = findViewById(R.id.et_join_pw);
        et_join_nick = findViewById(R.id.et_join_nick);
        et_join_phpne = findViewById(R.id.et_join_phpne);
        btn_join_submit = findViewById(R.id.btn_join_submit);

        // btn_join_submit 버튼 클릭 Event
        btn_join_submit.setOnClickListener(v -> {

            // 가입정보 가져오기
            email = et_join_id.getText().toString();
            pw = et_join_pw.getText().toString();
            nickname = et_join_nick.getText().toString();
            phone = et_join_phpne.getText().toString();

            if (!email.equals("") && !pw.equals("")) {
                // 이메일과 비밀번호가 공백이 아닌 경우
                // Realtime DB에 데이터 추가 Method 실행
                writeUser(email, pw, nickname, phone);
                // Auth에 데이터 추가 Method 실행 - Email, PW만 저장됨(only 로그인용)
                createUser(email, pw, nickname, phone);
                Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
            } else {
                // 이메일과 비밀번호가 공백인 경우
                Toast.makeText(JoinActivity.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
            }
        });
    } // onCreate() 끄읏


    // Firebase Authentication - 계정 생성 Method
    private void createUser(String email, String password, String nickname, String phone) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공시
                            Toast.makeText(JoinActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            // LoginActivity로 이동하기
                            Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                            intent.putExtra("nickname", nickname);
                            startActivity(intent);
                            finish();
                        } else {
                            // 계정이 중복된 경우
                            Toast.makeText(JoinActivity.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // RealtimeDB - 회원가입 user 추가 Method
    public void writeUser(String email, String password, String nickname, String phone){
        UserVO user = new UserVO(email, password,nickname,phone);

        myRef.push().setValue(user);

    }


}
