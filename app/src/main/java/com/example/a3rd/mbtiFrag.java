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
import androidx.fragment.app.FragmentManager;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class mbtiFrag extends DialogFragment {

    static final String TAG = "mbtiFrag";
    TextView tv_question;
    Button btn_num1, btn_num2, btn_num3, btn_num4;
    ImageView img_mbti;

    int[] imgs = {R.drawable.mbti1, R.drawable.mbti2, R.drawable.mbti3, R.drawable.mbti4, R.drawable.mbti5,
                R.drawable.mbti6, R.drawable.mbti7, R.drawable.mbti8, R.drawable.mbti9, R.drawable.mbti10};

    int score = 0;
    String question1 = "";
    String answer1 = "";
    String answer2 = "";
    String answer3 = "";
    String answer4 = "";
    int num = 2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mbti_dialog,container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        img_mbti = view.findViewById(R.id.img_mbti);
        tv_question = view.findViewById(R.id.text8);
        btn_num1 = view.findViewById(R.id.btn_num1);
        btn_num2 = view.findViewById(R.id.btn_num2);
        btn_num3 = view.findViewById(R.id.btn_num3);
        btn_num4 = view.findViewById(R.id.btn_num4);


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //테스트데이터 입력

        Map<String, Object> mbti = new HashMap<>();
//        mbti.put("question1",new MbtiVO("식물을 두려는 장소에 빛이 얼마나 오래 드나요?","거의 온 종일 빛이 들어요.",
//                "하루 6시간 정도 빛이 들어요","하루 2~3시간 정도 빛이 들어요","빛이 잘 들지 않아요"));
//
//        mbti.put("question2",new MbtiVO("평소, 창문을 얼마나 자주 열고 계시나요?","항상 열어두고 있어요.",
//                "하루 2-3시간 주기적으로 환기를 해요.","일주일에 2~3번, 주기적으로 환기를 해요.","생각날 때마다 한 번씩 환기해요."));
//
//        mbti.put("question3",new MbtiVO("식물을 두려는 장소는 얼마나 건조한가요?","가끔, 목이 칼칼할 정도로 건조해요.",
//                "습하지도, 건조하지도 않은 것 같아요.","약간 습한데, 불쾌하지는 않아요.","공기의 무게가 느껴질 정도로 습해요."));
//
//        mbti.put("question4",new MbtiVO("식물에 얼마나 자주 관심을 줄 수 있나요?","매일 들여다 볼 수 있어요.",
//                "일주일에 2~3번정도 들여다볼 수 있어요.","일주일에 한 번 정도 들여다볼 수 있어요.","생각날 때 마다 한번씩 들여다볼 수 있어요."));
//
//        mbti.put("question5",new MbtiVO("식물에 물주는 일, 좋아하시나요?","정말 좋아해요! 자주 주고 싶어요.",
//                "좋아하지만, 자주 주기는 번거로울 것 같아요.","귀찮지는 않지만, 종종 깜빡할 것 같아요.","물주기를 자주 잊을 것 같아요."));
//
//        mbti.put("question6",new MbtiVO("식물을 두려는 장소에 빛이 얼마나 강하게 드나요? (정오 기준)","조명을 켜지 않고도 책을 읽을 수 있을 정도에요.",
//                "조명을 켜지 않고도 일상생활을 할 수 있어요.","빛은 들지만, 조명을 켜지 않으면 일상생활은 무리가 있어요.","낮에도 새벽이나 저녁처럼 어두워요."));
//
//        mbti.put("question7",new MbtiVO("어떤 종류의 식물을 키우고 싶으신가요 ?","초본류의 관엽식물 (풀, 키가 작아요)",
//                "목본류의 관엽식물 (나무, 키가 커요)","꽃과 열매를 보는 식물","선인장, 다육식물"));
//
//        mbti.put("question8",new MbtiVO("식물을 둘 곳의 창문은 어떻게 생겼나요??","키를 훌쩍 넘는 통유리",
//                "키와 비슷한 베란다 슬라이딩 도어","키보다 살짝 작은 창문","환기가 겨우 가능한, 아주 작은 창문"));
//
//        mbti.put("question9",new MbtiVO("식식물을 키워본 경험이 있으신가요?","네, 지금도 잘 키우고 있어요.",
//                "키우고 있지만, 잘 키울 자신은 없어요.","한 번도 키워본 적이 없어요.","경험은 있지만, 지금은 식물을 키우고 있지 않아요."));
//
//        mbti.put("question10",new MbtiVO("아끼던 식물이 아프면, 어떻게 할 것 같으세요?","모르겠어요.",
//                "인터넷으로 정보를 검색해 볼 거예요.","'시간이 약이겠지...' 조금 더 지켜볼 거예요.","주변에 직접 물어보거나, 도움을 청할 거예요."));

//        db.collection("mbtitest").document("mbtidata")
//                .update(mbti)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG,"데이터입력성공");
//                    }
//                })
//
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "데이터입력실패", e);
//                    }
//                });


        final DocumentReference docRef = db.collection("mbtitest").document("mbtidata");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) { Log.w(TAG, "Listen failed.", e);
                return; }
                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());

                    MbtiVO mvo = snapshot.get("question1", MbtiVO.class);
                    Log.d("값 받아오기", snapshot.get("question1") + "");
                    tv_question.setText(mvo.getQuestion());
                    btn_num1.setText(mvo.getAnswer1());
                    btn_num2.setText(mvo.getAnswer2());
                    btn_num3.setText(mvo.getAnswer3());
                    btn_num4.setText(mvo.getAnswer4());

                }else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });


        //mbti 1~4번까지 클릭 점수 누적.
        btn_num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DocumentReference docRef = db.collection("mbtitest").document("mbtidata");
                docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) { Log.w(TAG, "Listen failed.", e);
                            return; }
                        if (snapshot != null && snapshot.exists()) {
                            Log.d(TAG, "Current data: " + snapshot.getData());

                            if(num<11) {
                                MbtiVO mvo = snapshot.get(("question" + num), MbtiVO.class);
                                tv_question.setText(mvo.getQuestion());
                                btn_num1.setText(mvo.getAnswer1());
                                btn_num2.setText(mvo.getAnswer2());
                                btn_num3.setText(mvo.getAnswer3());
                                btn_num4.setText(mvo.getAnswer4());

                                num += 1;
                                img_mbti.setImageResource(imgs[num-2]);
                            }
                        }else {
                            Log.d(TAG, "Current data: null");
                        }
                    }
                });

                score+=4;
                if (num==11) {
                    getDialog().dismiss();
                    FragmentManager fm = getFragmentManager();
                    mbtiFrag2 mbtiFrag2 = new mbtiFrag2();

                    Bundle args = new Bundle();
                    args.putInt("score",score);
                    mbtiFrag2.setArguments(args);
                    mbtiFrag2.show(fm,"mbtiFrag2");

                }

            }
        });

        btn_num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DocumentReference docRef = db.collection("mbtitest").document("mbtidata");
                docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) { Log.w(TAG, "Listen failed.", e);
                            return; }
                        if (snapshot != null && snapshot.exists()) {
                            Log.d(TAG, "Current data: " + snapshot.getData());

                            if(num<11) {
                                MbtiVO mvo = snapshot.get(("question" + num), MbtiVO.class);
                                tv_question.setText(mvo.getQuestion());
                                btn_num1.setText(mvo.getAnswer1());
                                btn_num2.setText(mvo.getAnswer2());
                                btn_num3.setText(mvo.getAnswer3());
                                btn_num4.setText(mvo.getAnswer4());

                                num += 1;
                                img_mbti.setImageResource(imgs[num-2]);
                            }
                        }else {
                            Log.d(TAG, "Current data: null");
                        }
                    }
                });
                score+=3;
                if (num==11) {
                    getDialog().dismiss();
                    FragmentManager fm = getFragmentManager();
                    mbtiFrag2 mbtiFrag2 = new mbtiFrag2();

                    Bundle args = new Bundle();
                    args.putInt("score",score);
                    mbtiFrag2.setArguments(args);
                    mbtiFrag2.show(fm,"mbtiFrag2");

                }

            }
        });
        btn_num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DocumentReference docRef = db.collection("mbtitest").document("mbtidata");
                docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) { Log.w(TAG, "Listen failed.", e);
                            return; }
                        if (snapshot != null && snapshot.exists()) {
                            Log.d(TAG, "Current data: " + snapshot.getData());

                            if(num<11) {
                                MbtiVO mvo = snapshot.get(("question" + num), MbtiVO.class);
                                tv_question.setText(mvo.getQuestion());
                                btn_num1.setText(mvo.getAnswer1());
                                btn_num2.setText(mvo.getAnswer2());
                                btn_num3.setText(mvo.getAnswer3());
                                btn_num4.setText(mvo.getAnswer4());

                                num += 1;
                                img_mbti.setImageResource(imgs[num-2]);
                            }

                        }else {
                            Log.d(TAG, "Current data: null");
                        }
                    }
                });
//                score+=2;
//                if (num==10){
//                    getDialog().dismiss();
//                }

                score+=2;
                if (num==11) {
                    getDialog().dismiss();
                    FragmentManager fm = getFragmentManager();
                    mbtiFrag2 mbtiFrag2 = new mbtiFrag2();

                    Bundle args = new Bundle();
                    args.putInt("score",score);
                    mbtiFrag2.setArguments(args);
                    mbtiFrag2.show(fm,"mbtiFrag2");

                }

            }
        });
        btn_num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DocumentReference docRef = db.collection("mbtitest").document("mbtidata");
                docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) { Log.w(TAG, "Listen failed.", e);
                            return; }
                        if (snapshot != null && snapshot.exists()) {
                            Log.d(TAG, "Current data: " + snapshot.getData());

                            if(num<11) {
                                MbtiVO mvo = snapshot.get(("question" + num), MbtiVO.class);
                                tv_question.setText(mvo.getQuestion());
                                btn_num1.setText(mvo.getAnswer1());
                                btn_num2.setText(mvo.getAnswer2());
                                btn_num3.setText(mvo.getAnswer3());
                                btn_num4.setText(mvo.getAnswer4());

                                num += 1;
                                img_mbti.setImageResource(imgs[num-2]);
                            }

                        }else {
                            Log.d(TAG, "Current data: null");
                        }
                    }
                });
                score+=1;
                if (num==11){
                    getDialog().dismiss();
                    FragmentManager fm = getFragmentManager();
                    mbtiFrag2 mbtiFrag2 = new mbtiFrag2();

                    Bundle args = new Bundle();
                    args.putInt("score",score);
                    mbtiFrag2.setArguments(args);
                    mbtiFrag2.show(fm,"mbtiFrag2");

                }

            }
        });

        return view;
    }
}
