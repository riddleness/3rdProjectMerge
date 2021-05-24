package com.example.a3rd;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Frag6 extends Fragment {
    private View view;
    static final String TAG = "Frag6";

    GridView gv;
    //    ImageView img_num1;
    int num = 1;

    ImageView img_btnplant;

    ArrayList<bookVO> data;
    bookAdapter adapter;
    //식물도감 프레그먼트
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.frag6, container, false);

        gv = view.findViewById(R.id.gv);
        data = new ArrayList<>();

//        img_num1 = view.findViewById(R.id.img_num1);

        // 파이어스토어 테스트 이미지 불러오기 코드
//        FirebaseStorage storage = FirebaseStorage.getInstance("gs://rdproject-9dfed.appspot.com");
//        StorageReference storageRef = storage.getReference();
//
//        Log.d("저장소",storageRef+"");
//
//        storageRef.child("book/book01.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Glide.with(getActivity().getApplicationContext())  //프래그먼트는 이렇게 Context를 구해야한다.
//                        .load(uri)
//                        .into(img_num1);
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getActivity().getApplicationContext(),"실패",Toast.LENGTH_SHORT).show();
//            }
//        });


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //데이터 받아오기 테스트
        final DocumentReference docRef = db.collection("bookinfo").document("bookdata");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) { Log.w(TAG, "Listen failed.", e);
                    return; }
                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());

                    for(int z = 0 ; z<16 ;z++){

                        bookVO bvo = snapshot.get("flower"+ num,bookVO.class);
                        num+=1;
                        data.add(bvo);
                    }
                    adapter = new bookAdapter(getActivity().getApplicationContext(), R.layout.book_list,data);
                    gv.setAdapter(adapter);
                }else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });


        // 그리드뷰 클릭 이벤트 작성중...
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("클릭확인",position+"");


                FragmentManager fm = getFragmentManager();
                bookFrag bookFrag = new bookFrag();

                Bundle args = new Bundle();
                args.putInt("position",position);
                bookFrag.setArguments(args);
                bookFrag.show(fm,"bookFrag");



            }
        });





        //리스트뷰 테스트
//        lv = view.findViewById(R.id.lv);
//        data = new ArrayList<>();


//        bookVO vo1 = new bookVO("a","b","c","d","e");
//        bookVO vo2 = new bookVO("1","2","3","4","5");
//        bookVO vo3 = new bookVO("1","2","3","4","5");
//        bookVO vo4 = new bookVO("1","2","3","4","5");
//        bookVO vo5 = new bookVO("1","2","3","4","5");
//        bookVO vo6 = new bookVO("1","2","3","4","5");
//        bookVO vo7 = new bookVO("1","2","3","4","5");
//
//        data.add(vo1);
//        data.add(vo2);
//        data.add(vo3);
//        data.add(vo4);
//        data.add(vo5);
//        data.add(vo6);
//        data.add(vo7);

//        adapter = new bookAdapter(getActivity().getApplicationContext(), R.layout.book_list,data);
//        lv.setAdapter(adapter);

//        adapter.notifyDataSetChanged();


        //테스트데이터 입력

//        Map<String, Object> bookcoll = new HashMap<>();
//        bookcoll.put("flower1",new bookVO("자스민","book01.png",
//                "보통","15도","1개월에 한번 물주세요"));
//        bookcoll.put("flower2",new bookVO("로즈마리","book02.png",
//                "건조","20도","겉흙이 마르면 물을 주세요"));
//        bookcoll.put("flower3",new bookVO("장미허브","book03.png",
//                "건조","20도","흙이 마르면 2~3일 후에 충분히 / 과습에 주의"));
//        bookcoll.put("flower4",new bookVO("다육이","book04.png",
//                "건조","15도","1개월에한번 물을 주세요"));
//        bookcoll.put("flower5",new bookVO("산세베리아","book05.png",
//                "보통","15도","1개월에한번 물을 주세요"));
//        bookcoll.put("flower6",new bookVO("틸란드시아","book06.png",
//                "건조","10도","2~3일에 한번 분무기로 물을 주세요"));
//        bookcoll.put("flower7",new bookVO("스투키","book07.png",
//                "건조","15도","1개월에한번 물을 주세요"));
//        bookcoll.put("flower8",new bookVO("알라만다","book08.png",
//                "다습","8도이상","6월부터 늦가을까지 꽃이 피고 지는게 반복, 줄기와 잎의 수액에 독성이 있음"));
//        bookcoll.put("flower9",new bookVO("제라늄","book09.png",
//                "건조","21도","여름철에 관리 잘 해야함(습도체크, 비료주면안됨)"));
//        bookcoll.put("flower10",new bookVO("동양난","book10.png",
//                "보통","19도","베란다에서 키우는 경우 햇빛 차광필요(50~70%정도)"));
//        bookcoll.put("flower11",new bookVO("안스리움","book11.png",
//                "다습","20도","겨울에는 미지근한물로 자주 분무"));
//        bookcoll.put("flower12",new bookVO("레몬밤","book12.png",
//                "다습","18도","2~3일에1회 씩 물을 주세요!"));
//        bookcoll.put("flower13",new bookVO("페퍼민트","book13.png",
//                "보통","15도","번식력이 강해서 금방 분갈이 필요해요"));
//        bookcoll.put("flower14",new bookVO("치자","book14.png",
//                "다습","16도","겉흙이 마르면 듬뿍, 꽃에는 물이 닿지 않게"));
//        bookcoll.put("flower15",new bookVO("히야신스","book15.png",
//                "보통","10도","겉흙이 마르면 물을 주세요"));
//        bookcoll.put("flower16",new bookVO("라벤더","book16.png",
//                "건조","15도","겉흙이 마르면 하루 이틀 후 충분히 물을 주세요"));
//
//
//        db.collection("bookinfo").document("bookdata")
//                .update(bookcoll)
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


        return view;

    }
}