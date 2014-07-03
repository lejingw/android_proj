package com.lejingw.apps.popupwin;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandableList1 extends Activity {
    public final static String tag = "ExpandableList1";
    public final static String NAME = "姓名:";
    public final static String PHONE = "电话:";
    public final static String SEX = "性别:";
    private ExpandableListView listView;
    private TextView mTitle;
    public List<String> group;
    public List<List<String>> child;
    public ExpandInfoAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expand_list);
        findView();
        initListView();
        setListViewOnChildClickListener();
        // 注册长按选项弹出莱单
        registerForContextMenu(listView);
    }

    public void findView() {
        listView = (ExpandableListView) findViewById(R.id.expandable_list_view);
        mTitle = (TextView) this.findViewById(R.id.list_title_text);
    }

    public void initialOther() {
        group = new ArrayList<String>();
        child = new ArrayList<List<String>>();
    }

    public void initListView() {
        initialOther();
        addItemByValue("张三", "051782214", "男");
        addItemByValue("李四", "110", "男");
        addItemByValue("王二", "132", "女");
        addItemByValue("麻子", "13321234562", "女");
        adapter = new ExpandInfoAdapter(this);
        listView.setAdapter(adapter);
    }

    public void setListViewOnChildClickListener() {
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        Log.i(tag, "groupPosition:" + groupPosition);
                        Log.i(tag, "childPosition:" + childPosition);
                        Log.i(tag, "id:" + id);
                        return true;
                    }
                });
    }


    public void addItemByValue(String n, String c1, String c2) {
        group.add(n);
        List<String> item = new ArrayList<String>();
        item.add(NAME + n);
        item.add(PHONE + c1);
        item.add(SEX + c2);
        child.add(item);
    }

    public class ExpandInfoAdapter extends BaseExpandableListAdapter {
        LayoutInflater mInflater;
        Bitmap mIcon1;
        Activity activity;

        public ExpandInfoAdapter(Activity a) {
            activity = a;
            mInflater = LayoutInflater.from(activity);
            mIcon1 = BitmapFactory.decodeResource(activity.getResources(), R.drawable.icon48x48_1);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return child.get(groupPosition).get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return child.get(groupPosition).size();
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            // return
            // getChildViewStub(child.get(groupPosition).get(childPosition)
            // .toString());
            return getView(groupPosition, childPosition, convertView, parent);
        }

//        public TextView getChildViewStub(String s) {
//            // Layout parameters for the ExpandableListView
//            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
//                    ViewGroup.LayoutParams.FILL_PARENT, 64);
//            TextView text = new TextView(activity);
//            text.setLayoutParams(lp);
//            text.setTextSize(20);
//            text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//            text.setPadding(36, 0, 0, 0);
//            text.setText(s);
//            return text;
//        }

        public View getView(int groupPosition, int childPosition, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.expand_list_item, null);
                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.text);
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String name = child.get(groupPosition).get(childPosition);
            holder.text.setText(name);
            holder.icon.setImageBitmap(mIcon1);
            return convertView;
        }

        // ++++++++++++++++++++++++++++++++++++++++++++
        // group's stub
        @Override
        public Object getGroup(int groupPosition) {
            return group.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return group.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            return getGroupViewStub(getGroup(groupPosition).toString());
        }

        public TextView getGroupViewStub(String s) {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 64);
            TextView text = new TextView(activity);
            text.setLayoutParams(lp);
            text.setTextSize(20);
            text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            text.setPadding(36, 0, 0, 0);
            text.setText(s);
            return text;
        }

        // Indicate whether Group is Expanded or Collapsed
        public void onGroupExpanded(int groupPosition) {
        }

        public void onGroupCollapsed(int groupPosition) {
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }

    static class ViewHolder {
        TextView text;
        ImageView icon;
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
//        menu.setHeaderTitle("Sample menu");
//        menu.add(0, 0, 0, "menu1 order0");
//        menu.add(0, 0, 1, "menu2 order1");
//        menu.add(0, 1, 1, "menu3 item1");
//        menu.add(1, 1, 1, "menu4 group1");
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        Log.i(tag, "GroupID" + item.getGroupId() + ", itemId :" + item.getItemId() + " order :" + item.getOrder());
//
//        ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();
//        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
//        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
//            String title = ((ViewHolder) info.targetView.getTag()).text.getText().toString();
//            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
//            int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition);
//            Toast.makeText(
//                    this,
//                    title + ": Child " + childPos + " clicked in group" + groupPos, Toast.LENGTH_SHORT).show();
//            return true;
//        } else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
//            String titles = ((TextView) info.targetView).getText().toString();
//            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
//            Toast.makeText(
//                    this,
//                    titles + ": Group " + groupPos + " clicked", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        return false;
//    }
}