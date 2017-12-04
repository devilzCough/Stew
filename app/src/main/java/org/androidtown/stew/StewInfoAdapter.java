package org.androidtown.stew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by iseungjin on 2017. 12. 4..
 */

public class StewInfoAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<String> titleList = null;
    private HashMap<String, ArrayList<String>> contentsList;

    // private ViewHolder viewHolder = null;

    public StewInfoAdapter(Context c, ArrayList<String> titleList,
                           HashMap<String, ArrayList<String>> contentsList) {
        super();
        context = c;
        this.titleList = titleList;
        this.contentsList = contentsList;
    }

    // return list size
    @Override
    public int getGroupCount() { return titleList.size(); }

    @Override
    public int getChildrenCount(int groupPosition) {
        return contentsList.get(titleList.get(groupPosition)).size();
    }

    // return position
    @Override
    public String getGroup(int groupPosition) { return titleList.get(groupPosition); }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return contentsList.get(titleList.get(groupPosition)).get(childPosition);
    }

    // return ID
    @Override
    public long getGroupId(int groupPosition) { return groupPosition; }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        String groupName = titleList.get(groupPosition);
        View v = convertView;

        if (v == null) {
            //
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = (GridLayout) inflater.inflate(R.layout.info_title, null);
            //
            /*viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.info_title, parent, false);
            viewHolder.title = (TextView) v.findViewById(R.id.txtTitle);
            // viewHolder.iv_image = (ImageView) v.findViewById(R.id.iv_image);
            v.setTag(viewHolder);*/
        }/*else{
            viewHolder = (ViewHolder) v.getTag();
        }*/

        TextView textGroup = (TextView) v.findViewById(R.id.txtTitle);
        textGroup.setText(groupName);

        // 그룹을 펼칠때와 닫을때 아이콘을 변경해 준다.
        /*if (isExpanded) {
            viewHolder.iv_image.setBackgroundColor(Color.GREEN);
        } else {
            viewHolder.iv_image.setBackgroundColor(Color.WHITE);
        }

        viewHolder.title.setText(getGroup(groupPosition));*/

        return v;
    }


    // 차일드뷰 각각의 ROW
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        String childName = contentsList.get(titleList.get(groupPosition)).get(childPosition);
        View v = convertView;

        if (v == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = (LinearLayout) inflater.inflate(R.layout.info_contents, null);
            /*viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.info_contents, null);
            viewHolder.contents = (TextView) v.findViewById(R.id.txtContents);
            v.setTag(viewHolder);*/
        }/*else{
            viewHolder = (ViewHolder)v.getTag();
        }*/

        TextView textChild = (TextView) v.findViewById(R.id.txtContents);
        textChild.setText(childName);

        // viewHolder.contents.setText(getChild(groupPosition, childPosition));

        return v;
    }

    @Override
    public boolean hasStableIds() { return true; }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }

    class ViewHolder {

        public ImageView iv_image;
        public TextView title;
        public TextView contents;
    }

}
