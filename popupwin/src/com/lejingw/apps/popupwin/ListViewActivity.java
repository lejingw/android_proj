package com.lejingw.apps.popupwin;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListViewActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);


        ListView listView = (ListView) this.findViewById(R.id.listView);

        List<HashMap<String, Object>> data = createTempData();
        //创建SimpleAdapter适配器将数据绑定到item显示控件上
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.list_item,
                new String[]{"name", "phone", "amount"}, new int[]{R.id.name, R.id.phone, R.id.amount});
        //实现列表的显示
        listView.setAdapter(adapter);
        //条目点击事件
        listView.setOnItemClickListener(new ItemClickListener());
    }

    private List<HashMap<String, Object>> createTempData() {
        String[] ids = new String[]{"1", "2", "3"};
        String[] names = new String[]{"aaaaaaa", "bbbbbbb", "cccccc"};
        String[] phones = new String[]{"123", "234", "345"};
        String[] amounts = new String[]{"99", "100", "101"};
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

        for(int i=0;i<ids.length;i++) {
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put("id", ids[i]);
            item.put("name", names[i]);
            item.put("phone", phones[i]);
            item.put("amount", amounts[i]);
            data.add(item);
        }
        return data;
    }

    //获取点击事件
    private final class ItemClickListener implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ListView listView = (ListView) parent;
            HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
            String personid = data.get("id").toString();
            Toast.makeText(getApplicationContext(), personid, Toast.LENGTH_SHORT).show();
        }
    }

    class Person {
        private String id;
        private String name;
        private String phone;
        private String amount;

        public String getId() {
            return id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}