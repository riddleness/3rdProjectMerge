package com.example.a3rd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText et_login_id, et_login_pw, et_join_nick;
    Button btn_login, btn_join;
    CheckBox check_login;
    SignInButton Google_login;
    String email, pw, nickname;
    FirebaseAuth.AuthStateListener firebaseAuthListener;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // id 불러오기
        et_login_id = findViewById(R.id.et_login_id);
        et_login_pw = findViewById(R.id.et_login_pw);
        btn_login = findViewById(R.id.btn_login);
        btn_join = findViewById(R.id.btn_join);
        check_login = findViewById(R.id.check_login);
        Google_login = findViewById(R.id.Google_login);
        et_join_nick = findViewById(R.id.et_join_nick);

        // btn_join 클릭 -> JoinActivity로 이동하기
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });

        // btn_login 클릭 Event
        btn_login.setOnClickListener(v -> {

            email = et_login_id.getText().toString();
            pw = et_login_pw.getText().toString();
            nickname = getIntent().getStringExtra("nickname");

            mAuth.signInWithEmailAndPassword(email, pw)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "로그인 실패...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        });

        // 자동로그인 체크 Event
        check_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 자동로그인 Method
                // moveMainPage(firebaseAuth.getCurrentUser());
            }
        });

        // Google_login 클릭 Event
        Google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    } // onCreate 끝

}





