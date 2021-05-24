package com.example.a3rd;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class bookFrag extends DialogFragment {
    static final String TAG = "bookFrag";

    int rscore = 0;
    private int [] imgs1 = {R.drawable.book01, R.drawable.book02, R.drawable.book03, R.drawable.book04, R.drawable.book05,
            R.drawable.book06, R.drawable.book07, R.drawable.book08, R.drawable.book09, R.drawable.book10, R.drawable.book11, R.drawable.book12,
            R.drawable.book13, R.drawable.book14, R.drawable.book15, R.drawable.book16};

    ImageView img_bplant;
    TextView tv_bhum, tv_btemper, tv_binfo,tv_bname;
    Button btn_bclose;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_dialog,container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그 배경 투명화

        Bundle scores = getArguments();
        rscore = scores.getInt("position");
        Log.d("점수받아오기",rscore+"");

        //초기화
        img_bplant = view.findViewById(R.id.img_bplant);
        tv_bhum = view.findViewById(R.id.tv_bhum);
        tv_btemper = view.findViewById(R.id.tv_btemper);
        tv_binfo = view.findViewById(R.id.tv_binfo);
        tv_bname =view.findViewById(R.id.tv_bname);
        btn_bclose = view.findViewById(R.id.btn_bclose);

        //이미지 설정
        img_bplant.setImageResource(imgs1[rscore]);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("bookinfo").document("bookdata");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) { Log.w(TAG, "Listen failed.", e);
                    return; }
                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());

                    bookVO bvo = snapshot.get(("flower" + (rscore+1)), bookVO.class);
                    tv_bname.setText(bvo.getPlantName());
                    tv_bhum.setText(bvo.getPlantSoilhum());
                    tv_btemper.setText(bvo.getPlantTemper());
                    tv_binfo.setText(bvo.getPlantInfo());

                }else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
        btn_bclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }

}
