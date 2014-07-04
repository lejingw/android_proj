package com.lejingw.apps.popupwin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends Activity {
    private List<BusinessData> data;
    private int[] startScoreArr = new int[]{R.id.star_score1, R.id.star_score2, R.id.star_score3, R.id.star_score4, R.id.star_score5};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);


        ListView listView = (ListView) this.findViewById(R.id.listView);

        data = createTempData();
        //创建SimpleAdapter适配器将数据绑定到item显示控件上
//      SimpleAdapter adapter = new ListViewAdapter(this, data, R.layout.list_item,
//                new String[]{"name", "phone", "amount"}, new int[]{R.id.name, R.id.phone, R.id.amount});
        BaseAdapter adapter = new ListViewInnerAdapter(this);
        //实现列表的显示
        listView.setAdapter(adapter);
        //条目点击事件
        listView.setOnItemClickListener(new ItemClickListener());
    }
/*
    public void onMyButtonClicksssssssssssssss(View view){
//        view.getParent().getParent
        ImageView imageView = (ImageView) view;
//        imageView.setImageResource(R.drawable.collipse);
//        imageView.setImageDrawable(getResources().getDrawable(R.drawable.collipse));
//        Toast.makeText(this, "Button clicked!", Toast.LENGTH_SHORT).show();
       // Bitmap bitmapOrg = imageView.getDrawingCache();
        Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), R.drawable.expand);
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 缩放图片动作
        matrix.postScale(bitmapOrg.getWidth(), bitmapOrg.getHeight());
        //旋转图片 动作
        matrix.postRotate(180);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true);
        //将上面创建的Bitmap转换成Drawable对象，使得其可以使用在 ImageView, ImageButton中
        BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
//        imageView.setImageDrawable(bmd);
        imageView.setImageBitmap(resizedBitmap);
    }
*/
    private List<BusinessData> createTempData() {
        String[] ids = new String[]{"1", "2", "3"};
        String[] names = new String[]{"宝石奇缘", "马到成功", "龙腾盛世"};
        String[] phones = new String[]{"20万", "20万", "50万"};
        String[] amounts = new String[]{"99", "100", "101"};
        int[] scores = new int[]{0, 9, 10};

        List<BusinessData> data = new ArrayList<BusinessData>();

        for(int i=0;i<ids.length;i++) {
            data.add(new BusinessData(ids[i], names[i], phones[i], amounts[i], scores[i]));
        }
        return data;
    }

    //获取点击事件
    private final class ItemClickListener implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
            ListView listView = (ListView) parent;
            HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
            String personid = data.get("id").toString();
            Toast.makeText(getApplicationContext(), personid, Toast.LENGTH_SHORT).show();
            */
        }
    }

    class ListViewInnerAdapter extends BaseAdapter {
        private Context context;

        public ListViewInnerAdapter(Context context) {
            super();
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 64);
            final View view = LayoutInflater.from(context).inflate(R.layout.list_item, null, false);
            //view.setLayoutParams(lp);
            //view.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//          LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//          final View vPopWindow = inflater.inflate(R.layout.mypop_window, null, false);

            BusinessData person = data.get(position);

            TextView nameTextView = (TextView)view.findViewById(R.id.name);
            nameTextView.setText(person.getName());
            TextView phoneTextView = (TextView) view.findViewById(R.id.phone);
            phoneTextView.setText("最高奖:" + person.getPhone());
            final TextView amountTextView = (TextView) view.findViewById(R.id.amount);
            amountTextView.setText("返奖率:" + person.getAmount());
//            final TextView scoreTextView = (TextView) view.findViewById(R.id.score);
//            scoreTextView.setText("人气指数:" + person.getScore());
            int i=0;
            for(int j=person.getScore()/2;i<j;i++){
                ImageView scoreImageView = (ImageView) view.findViewById(startScoreArr[i]);
                scoreImageView.setImageResource(R.drawable.bg_star_full);
            }
            if(person.getScore()%2 == 1){
                ImageView scoreImageView = (ImageView) view.findViewById(startScoreArr[i]);
                scoreImageView.setImageResource(R.drawable.bg_star_half);
            }

            final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
//            final ImageView imageView2 = (ImageView) view.findViewById(R.id.imageView2);
            final ImageView expandImageView = (ImageView) view.findViewById(R.id.expandImageView);

            final View linearLayoutView = view.findViewById(R.id.linearLayout);

            expandImageView.setOnClickListener(new View.OnClickListener() {
                private boolean expendFlag = false;
                private ImageView iv = imageView;
                private TextView amountView = amountTextView;
//                private TextView scoreView = scoreTextView;
                private View linearView = linearLayoutView;
                @Override
                public void onClick(View v) {
                    expendFlag = !expendFlag;
                    //更换展开，合起图片
                    ((ImageView)v).setImageResource(expendFlag ? R.drawable.collipse11 : R.drawable.expand);

                    if(expendFlag){
                        RelativeLayout.LayoutParams mParams = new RelativeLayout.LayoutParams(150, 150);
                        imageView .setLayoutParams(mParams);
                    }else{
                        RelativeLayout.LayoutParams mParams = new RelativeLayout.LayoutParams(100, 100);
//                        android:layout_alignParentLeft="true"
//                        android:layout_centerVertical="true"
//                        mParams.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                        imageView .setLayoutParams(mParams);
                    }
                    amountView.setVisibility(expendFlag?View.VISIBLE:View.INVISIBLE);
//                    scoreView.setVisibility(expendFlag?View.VISIBLE:View.INVISIBLE);
                    linearView.setVisibility(expendFlag?View.VISIBLE:View.INVISIBLE);

//                  view.setMinimumHeight(200);
//                    imageView2.setVisibility(View.VISIBLE);
//                    imageView.setVisibility(View.INVISIBLE);

//                  iv.setAdjustViewBounds(true);
//                  iv.setMaxHeight(150);
//                  iv.setMaxWidth(150);
//                  iv.invalidate();
//                  System.out.println(iv);
                }
            });
            return view;
        }
    }



    class BusinessData {
        private String id;
        private String name;
        private String phone;
        private String amount;
        private int score;

        public BusinessData(String id, String name, String phone, String amount, int score) {
            this.id = id;
            this.name = name;
            this.phone = phone;
            this.amount = amount;
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

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