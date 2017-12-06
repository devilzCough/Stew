package org.androidtown.stew;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by limjeonghyun on 2017. 11. 30..
 */

public class MyListFragment extends Fragment {


    ArrayList<CustomBookCard> items;
    String userID;
    String userName;
    TextView txtID, txtName;
    JsoupProcess jsoupProcess;

    ArrayList<String> bookListString;
    RecyclerView recyclerView;
    ViewGroup rootView;

    public MyListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        AppManager.getInstance().setMyListFragment(this);
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mylist, container, false);

        txtID = (TextView) rootView.findViewById(R.id.txtID);
        txtName = (TextView) rootView.findViewById(R.id.txtName);

        jsoupProcess = AppManager.getInstance().getJsoupProcess();
        jsoupProcess.mylistInfo();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        return rootView;
    }

    public void createBookList(ArrayList<String> bookList) {


        /* 추후 정보는 받아오는걸로 처리 */
        int nInfo = bookList.size();
        items = new ArrayList<>();
        ArrayList<String> bookInfoRoom = new ArrayList<String>();
        ArrayList<String> bookInfoDate = new ArrayList<String>();
        ArrayList<String> bookInfoTime = new ArrayList<String>();
        ArrayList<String> bookInfoUser = new ArrayList<String>();

        for(String list : bookList){
            String[] infoArray = list.split("/");
            bookInfoRoom.add(infoArray[0]);

            String[] timeDivideArray = infoArray[1].split(" ");
            bookInfoDate.add(timeDivideArray[0]);
            bookInfoTime.add(timeDivideArray[1]+" "+timeDivideArray[3]);

            String[] userDivideArray = infoArray[2].split(" ");
            String userString="";
            for(int i=0;i<userDivideArray.length/8;i++){
                userString += userDivideArray[(8*i)+7] +"\t"+ userDivideArray[(8*i)+3]+"\n";
            }
            bookInfoUser.add(userString);
        }

        CustomBookCard[] item = new CustomBookCard[nInfo];
        for (int i = 0; i < nInfo; i++) {
            item[i] = new CustomBookCard(bookInfoRoom.get(i), bookInfoDate.get(i), bookInfoTime.get(i), bookInfoUser.get(i));
            items.add(item[i]);
        }

        recyclerView.setAdapter(new RecyclerAdapter(getActivity().getApplicationContext(), items, R.id.myListView));

        rootView.findViewById(R.id.progressBar).setVisibility(View.GONE);
        userID = AppManager.getInstance().getUserID();
        userName = AppManager.getInstance().getUserName();
        txtID.setText(userID);
        txtName.setText(userName);
    }

}
