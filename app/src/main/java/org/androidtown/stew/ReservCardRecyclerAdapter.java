package org.androidtown.stew;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iseungjin on 2017. 12. 1..
 */

public class ReservCardRecyclerAdapter extends RecyclerView.Adapter<ReservCardRecyclerAdapter.ViewHolder> {

    Context context;
    List<CustomReservCard> items;
    View v;
    int item_layout;

    public ReservCardRecyclerAdapter(Context context, ArrayList<CustomReservCard> items, int item_layout) {

        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.reserv_card,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final CustomReservCard item = items.get(position);

        holder.tvUserNum.setText("사용자" + item.getUserNum());
        if (!item.getUserFlag()) {
            holder.etStudentId.setText(item.getStrStudentId());
            holder.etStudentName.setText(item.getStrStudentName());
            holder.checkBtn.setText("삭제");
        }
        else {
            holder.etStudentId.setText("");
            holder.etStudentName.setText("");
            holder.checkBtn.setText("조회");
        }

        holder.checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BackgroundWebview webView;

                // now button = 'search'
                if (item.getUserFlag()) {

                    String id = holder.etStudentId.getText().toString();
                    String name = holder.etStudentName.getText().toString();
                    if (id.equals("") || name.equals("")) {
                        CustomDialog alert = new CustomDialog(v.getContext(),0,"학번과 이름을 입력해주세요.");
                    } else {
                        CustomDialog alert = new CustomDialog(v.getContext(),0,"사용자를 추가하였습니다.");

                        item.setUserFlag(false);
                        item.setStrStudentId(id);
                        item.setStrStudentName(name);
                        // webView.userCheck(item);
                        holder.checkBtn.setText("삭제");
                    }

                    // webView .....
                    // webView = new BackgroundWebview(context);
                    // AppManager.getInstance().setWebView(webView);
                        /* alert when wrong input */
                    //



                    // now button = 'delete'
                } else {
                    holder.etStudentId.setText("");
                    holder.etStudentName.setText("");
                    item.setUserFlag(true);
                    item.setStrStudentId("");
                    item.setStrStudentName("");
                    holder.checkBtn.setText("조회");
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvUserNum;
        EditText etStudentId;
        EditText etStudentName;
        Button checkBtn;

        CardView cardView;

        public ViewHolder(View itemView) {

            super(itemView);
            tvUserNum = (TextView) itemView.findViewById(R.id.userCount);
            etStudentId = (EditText) itemView.findViewById(R.id.studentID);
            etStudentName = (EditText) itemView.findViewById(R.id.studentName);
            checkBtn = (Button) itemView.findViewById(R.id.btnCheck);
        }
    }
}
