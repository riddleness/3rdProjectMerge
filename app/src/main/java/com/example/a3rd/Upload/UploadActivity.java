package com.example.a3rd.Upload;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.a3rd.BaseActivity;
import com.example.a3rd.Upload.interfaces.UploadActivityView;
import com.example.a3rd.Upload.models.UploadBody;
import com.example.a3rd.Upload.models.UploadResponse;
import com.example.a3rd.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UploadActivity extends BaseActivity implements UploadActivityView {
    final int PICTURE_REQUEST_CODE = 100;
    private static final int pick_from_Multi_album = 1;
    private String imageFilePath;
    private StorageReference mStorageRef;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private Uri uri;
    public UploadBody.imgUrls[] imgUrl;

    Button btn_camera, gallery;
    ImageView btn_upload, image_content;
    EditText upload_comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        gallery = findViewById(R.id.gallery);
        btn_camera = findViewById(R.id.btn_camera);
        btn_upload = findViewById(R.id.btn_upload);
        upload_comment = findViewById(R.id.upload_comment);
        image_content= findViewById(R.id.iv_upload);

        FirebaseApp.initializeApp(this);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                //사진을 여러개 선택할수 있도록 한다
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),  PICTURE_REQUEST_CODE);
            }
        });



        // 권한 체크
        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("카메라 권한이 필요합니다.")
                .setDeniedMessage("거부하셨습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HHmmss", Locale.getDefault());
                Date curDate = new Date(System.currentTimeMillis());
                String filename = formatter.format(curDate);
                System.out.println(filename+"파일이름");
                mStorageRef = storage.getReferenceFromUrl("gs://instagramclone-39a0f.appspot.com/").child("/instagram/"+filename);
                mStorageRef.putFile(uri)
                        //성공시
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getApplicationContext(), "업로드 완료!",Toast.LENGTH_SHORT).show();

                                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Log.d("★★★★★★★★11111", uri.toString());
                                        String url= uri.toString();
                                        imgUrl= new UploadBody.imgUrls[0];
                                        tryUpload( imgUrl ,upload_comment.getText().toString());
                                    }
                                }).toString();

                            }

                        })//실패시
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        //진행중
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            }
                        });


            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICTURE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                ClipData clipData = data.getClipData();
                Log.i("clipdata",String.valueOf(clipData.getItemCount()));
                uri =clipData.getItemAt(0).getUri();
                File f1 = new File(uri.getPath());
                image_content.setAdjustViewBounds(true);
                image_content.setImageURI(Uri.fromFile(f1));
            }
        }


    }



    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(), "권한이 허용됨", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "권한이 거부됨", Toast.LENGTH_SHORT).show();

        }

    };




    private void tryUpload(UploadBody.imgUrls[] imgUrl, String caption) {
        System.out.println("WriteComment 함수");
        final UploadService uploadService = new UploadService(this);
        uploadService.upLoadFeed(imgUrl,caption);
    }






    @Override
    public void UploadSuccess(UploadResponse uploadResponse) {
        System.out.println(uploadResponse.getMessage());
        if(uploadResponse.isSuccess()) {
            Intent intent = new Intent(getApplicationContext(), com.example.a3rd.MainActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void UploadFailure(String message) {

    }

    @Override
    public void onAttach(Context context) {

    }




    @Override
    public void onDetach() {

    }
}