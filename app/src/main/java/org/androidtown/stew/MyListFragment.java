package org.androidtown.stew;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by limjeonghyun on 2017. 11. 30..
 */

public class MyListFragment extends Fragment{


    ArrayList<CustomBookCard> items;
    String userID;
    String userName;

    public MyListFragment(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mylist, container, false);

        RecyclerView recyclerView=(RecyclerView) rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        items = new ArrayList<>();
        createBookList();
        recyclerView.setAdapter(new RecyclerAdapter(getActivity().getApplicationContext(),items,R.id.myListView));

        userID = AppManager.getInstance().getUserID();
        userName = AppManager.getInstance().getUserName();
        return rootView;
    }

    public void createBookList(){

        /* 추후 정보는 받아오는걸로 처리 */
        int nInfo =2;
        String[] bookInfoRoom = {"4층 Room 19","7층 Room 7"};
        String[] bookInfoDate = {"2017-12-10","2017-12-12"};
        String[] bookInfoTime = {"11:00-13:00","14:00-16:00"};
        String[] bookInfoUser = {"14010960 이승진\n14010970 임정현","14010968 정해서\n14011002 이주연"};

        CustomBookCard[] item = new CustomBookCard[nInfo];
        for (int i = 0; i < nInfo; i++) {
            item[i] = new CustomBookCard(bookInfoRoom[i],bookInfoDate[i],bookInfoTime[i],bookInfoUser[i]);
            items.add(item[i]);
        }
    }
}
