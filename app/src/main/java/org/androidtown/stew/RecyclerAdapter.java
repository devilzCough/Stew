package org.androidtown.stew;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by iseungjin on 2017. 12. 1..
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    List<CustomBookCard> items;
    int item_layout;

    public RecyclerAdapter(Context context, List<CustomBookCard> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.book_card,null);
        return new ViewHolder(v);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CustomBookCard item = items.get(position);
        /*Drawable drawable=context.getResources().getDrawable(item.getImage());
        holder.image.setBackground(drawable);*/
        holder.bookInfo.setText(item.getBookInfo());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,item.getBookInfo(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        // ImageView image;
        TextView bookInfo;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            // image = (ImageView)itemView.findViewById(R.id.image);
            bookInfo = (TextView)itemView.findViewById(R.id.bookInfo);
            cardView = (CardView)itemView.findViewById(R.id.cardView);
        }
    }
}
