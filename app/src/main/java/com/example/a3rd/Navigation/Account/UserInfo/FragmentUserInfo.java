package com.example.a3rd.Navigation.Account.UserInfo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.a3rd.Navigation.Account.UserInfo.Models.EditInfoResponse;
import com.example.a3rd.Navigation.Account.UserInfo.Models.UserInfoResponse;
import com.example.a3rd.Navigation.Account.UserInfo.interfaces.EditUserInfoActivityView;
import com.example.a3rd.Navigation.Account.UserInfo.interfaces.UserInfoActivityView;
import com.example.a3rd.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.example.a3rd.ApplicationClass.smallProfile;

public class FragmentUserInfo extends Fragment implements UserInfoActivityView, EditUserInfoActivityView {

    TextView profile_name, profile_img_change;
    TextView profile_id;
    TextView profile_intro;
    TextView profile_website;
    ImageView profile_edit, profile_img;
    private static final int REQUEST_ALBUM = 600;
    private Uri uri;
    private String imgurl;
    private StorageReference mStorageRef;
    private FirebaseStorage storage = FirebaseStorage.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tryGetUserInfo();
        View view = inflater.inflate(R.layout.activity_login_account, container, false);
        profile_img = view.findViewById(R.id.tv_profile_Img);
        profile_img_change = view.findViewById(R.id.tv_change_profile_Img);
        profile_name = view.findViewById(R.id.tv_change_profile_name);
        profile_id = view.findViewById(R.id.tv_change_profile_id);
        profile_intro = view.findViewById(R.id.tv_change_profile_intro);
        profile_website = view.findViewById(R.id.tv_change_profile_website);
        profile_edit = view.findViewById(R.id.btn_finish_edit);

        profile_img_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGallery();
            }
        });

        profile_edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HHmmss", Locale.getDefault());
                Date curDate = new Date(System.currentTimeMillis());
                String filename = formatter.format(curDate);
                mStorageRef = storage.getReferenceFromUrl("gs://instagramclone-39a0f.appspot.com/").child("/instagram/"+filename);
                if(uri!=null){
                    mStorageRef.putFile(uri)
                            //성공시
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(getActivity().getApplicationContext(), "업로드 완료!",Toast.LENGTH_SHORT).show();

                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Log.d("★★★★★★★★11111", uri.toString());
                                            imgurl=uri.toString();
                                            System.out.println("이미지 uri="+imgurl);
                                            smallProfile = imgurl;
                                            tryEditUserInfo(imgurl);
                                        }
                                    }).toString();
                                }

                            })//실패시
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity().getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            //진행중
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                }
                            });
                }else{
                    tryEditUserInfo("");
                }



            }
        });
        return view;
    }

    private void startGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_ALBUM);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("여기");


        if (resultCode != RESULT_OK) {
            return;
        }
        try {
            // 선택한 이미지에서 비트맵 생성
            InputStream in = getActivity().getContentResolver().openInputStream(data.getData());
            Bitmap img = BitmapFactory.decodeStream(in);
            in.close();
            // 이미지 표시
            profile_img.setImageBitmap(img);
            uri = data.getData();
            System.out.println(uri+"임");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static FragmentUserInfo newInstance() {
        return new FragmentUserInfo();
    }

    private void tryGetUserInfo() {
        final UserInfoService userInfoService = new UserInfoService(this);
        userInfoService.getUserInfo();
    }


    private void tryEditUserInfo(String imgurl) {
        String changeName = profile_name.getText().toString();
        String changeId = profile_id.getText().toString();
        String changeIntro = profile_intro.getText().toString();
        String changeWebsite = profile_website.getText().toString();
        final EditInfoService editInfoService = new EditInfoService(this);
        editInfoService.editUserInfo(imgurl,changeName,changeId,changeIntro,changeWebsite);
    }


    @Override
    public void EditUserInfoSuccess(EditInfoResponse editInfoResponse) {
        Glide.with(this)
                .load(editInfoResponse.getEditInfoResult().getProfileImgUrl())
                .error(R.drawable.ic_launcher_foreground)
                .into(profile_img);

        profile_name.setText(editInfoResponse.getEditInfoResult().getUserName());
        profile_id.setText(editInfoResponse.getEditInfoResult().getUserId());
        profile_intro.setText(editInfoResponse.getEditInfoResult().getProfileIntro());
        profile_website.setText(editInfoResponse.getEditInfoResult().getProfileWebsite());
//
//        Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
//        startActivity(intent);


    }

    @Override
    public void EditUserInfoFailure(String message) {

    }

    @Override
    public void UserInfoSuccess(UserInfoResponse userInfoResponse) {
        if(userInfoResponse.isSuccess()){

            Glide.with(this)
                    .load(userInfoResponse.getAccountResult().getProfileImgUrl())
                    .error(R.drawable.ic_launcher_foreground)
                    .into(profile_img);


            profile_name.setText(userInfoResponse.getAccountResult().getUserName());
            profile_id.setText(userInfoResponse.getAccountResult().getUserId());
            profile_intro.setText(userInfoResponse.getAccountResult().getProfileIntro());
            profile_website.setText(userInfoResponse.getAccountResult().getProfileWebSite());
        }


    }

    @Override
    public void UserInfoFailure(String message) {

    }
}
