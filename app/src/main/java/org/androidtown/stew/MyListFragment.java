package org.androidtown.stew;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limjeonghyun on 2017. 11. 30..
 */

public class MyListFragment extends Fragment{

    // test info
    String[] bookInfo = {"2017. 12. 01 \n4층 19번 스터디룸", "2017. 12. 5 \n7층 3번 스터디룸"};

    // ArrayList<CardView> bookList;

    public MyListFragment(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mylist, container, false);

        RecyclerView recyclerView=(RecyclerView) rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<CustomBookCard> items=new ArrayList<>();
        CustomBookCard[] item=new CustomBookCard[2];
        item[0]=new CustomBookCard(bookInfo[0]);
        item[1]=new CustomBookCard(bookInfo[1]);


        for (int i=0;i<2;i++) items.add(item[i]);

        recyclerView.setAdapter(new RecyclerAdapter(getActivity().getApplicationContext(),items,R.id.myListView));

        /*bookList = new ArrayList<CardView>();
        for (int i = 0; i < 2; i++) {
            CardView tmpCard = (CardView) rootView.findViewById(R.id.cardView);
            TextView text = (TextView) rootView.findViewById(R.id.bookInfo);
            text.setText(bookInfo[i]);
            bookList.add(tmpCard);
        }*/


        return rootView;
    }
}
