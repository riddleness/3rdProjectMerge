package com.example.a3rd;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class UserVO {

    private String email;
    private String pw;
    private String name;
    private String nickname;
    private String birth;
    private String phone;
    private int img;
    private String mbti;


    public UserVO(String email, String pw, String name, String nickname, String birth, String phone, int img, String mbti) {
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.phone = phone;
        this.img = img;
        this.mbti = mbti;
    }

    // 회원가입용 생성자
    public UserVO(String email, String pw, String nickname, String phone) {
        this.email = email;
        this.pw = pw;
        this.nickname = nickname;
        this.phone = phone;
    }

    // 기본 생성자
    public UserVO() {

    }

    // Getter & Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getMbti() {
        return mbti;
    }

    public void setMbti(String mbti) {
        this.mbti = mbti;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("pw", pw);
        result.put("nickname", nickname);
        result.put("phone", phone);

        return result;
    }

}
