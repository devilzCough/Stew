package org.androidtown.stew;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iseungjin on 2017. 12. 1..
 */

public class BookCardRecyclerAdapter extends RecyclerView.Adapter<BookCardRecyclerAdapter.ViewHolder> {
    Context context;
    List<CustomBookCard> items;
    int item_layout;

    public BookCardRecyclerAdapter(Context context, ArrayList<CustomBookCard> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.book_card,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final CustomBookCard item = items.get(position);

        holder.bookInfoRoom.setText(item.getBookInfoRoom());
        holder.bookInfoDate.setText(item.getBookInfoDate());
        holder.bookInfoTime.setText(item.getBookInfoTime());
        holder.bookInfoUser.setText(item.getBookInfoUser());


        holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "remove!!!", Toast.LENGTH_SHORT).show();
            }
        });
        holder.moreBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int flag = item.getBtnMoreFlag();
                if(flag == 0) {
                    holder.bookInfoMore.setVisibility(View.VISIBLE);
                    holder.moreBtn.setImageResource(R.drawable.close);
                    item.setBtnMoreFlag(1);
                }
                else if(flag == 1) {
                    holder.bookInfoMore.setVisibility(View.GONE);
                    holder.moreBtn.setImageResource(R.drawable.more);
                    item.setBtnMoreFlag(0);
                }
            }


        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView bookInfoRoom;
        TextView bookInfoDate;
        TextView bookInfoTime;
        TextView bookInfoUser;
        TableRow bookInfoMore;

        ImageButton cancelBtn;
        ImageButton moreBtn;

        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            bookInfoRoom = (TextView)itemView.findViewById(R.id.bookInfo_room);
            bookInfoDate = (TextView)itemView.findViewById(R.id.bookInfo_date);
            bookInfoTime = (TextView)itemView.findViewById(R.id.bookInfo_time);
            bookInfoUser = (TextView)itemView.findViewById(R.id.bookInfo_user);

            cancelBtn = (ImageButton)itemView.findViewById(R.id.btnCancel);
            moreBtn = (ImageButton)itemView.findViewById(R.id.btnMore);
            bookInfoMore = (TableRow)itemView.findViewById(R.id.bookInfo_more);
        }
    }
}
