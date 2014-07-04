package com.lejingw.apps.scatchcard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.widget.ImageView.ScaleType;
import com.lejingw.apps.scatchcard.index.ListViewAdapter;
import com.lejingw.apps.scatchcard.index.ScratchCardView;
import com.lejingw.apps.scatchcard.index.ScratchData;
import com.lejingw.apps.scatchcard.util.DisplayUtil;

public class IndexActivity extends Activity {
    private ViewPager viewPager;

    //	private String[] titles = new String[]{"title1", "title2", "title3", "title4"}; // 图片标题
    //	private TextView currentTitleTextView;
    private int[] imageResIdArr = new int[]{R.drawable.img_roll_advs1_s, R.drawable.img_roll_advs2_s, R.drawable.img_roll_advs3_s, R.drawable.img_roll_advs4_s};
    private int[] dotResIdArr = new int[]{R.id.v_dot0, R.id.v_dot1, R.id.v_dot2, R.id.v_dot3};
    private List<ImageView> imageViewList;
    private List<View> dotViewList; // 图片标题正文的那些点

    private int[] lunpanIdArr = new int[]{R.drawable.lunpan_0, R.drawable.lunpan_1, R.drawable.lunpan_2, R.drawable.lunpan_3, R.drawable.lunpan_4, R.drawable.lunpan_5};
    private int selectItemIndex = -1;

    private int currentItem = 0; // 当前图片的索引号

    private ImageView lunpanImageView;

    // An ExecutorService that can schedule commands to run after a given delay, or to execute periodically.
    private ScheduledExecutorService scheduledExecutorService;

    // 切换当前显示的图片
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // 切换当前显示的图片
            viewPager.setCurrentItem(currentItem);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        initImageAndDot();


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter());// 设置填充ViewPager页面的适配器
        // 设置一个监听器，当ViewPager中的页面改变时调用
        viewPager.setOnPageChangeListener(new MyPageChangeListener());

        //返回按钮
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, StartActivity.class);
                startActivity(intent);
                IndexActivity.this.finish();
            }
        });

        lunpanImageView = (ImageView) findViewById(R.id.lunpanImageButton);
        lunpanImageView.setOnTouchListener(new LunpanOnTouchListener());
        lunpanImageView.setOnClickListener(new ImageViewOnClickListener());
    }

    private void initImageAndDot(){
        imageViewList = new ArrayList<ImageView>();
        // 初始化图片资源
        for (int i = 0; i < imageResIdArr.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imageResIdArr[i]);
            imageView.setScaleType(ScaleType.CENTER_CROP);
            imageViewList.add(imageView);
        }

        dotViewList = new ArrayList<View>();
        // 初始化图片对应点
        for(int i=0; i < dotResIdArr.length; i++){
            dotViewList.add(findViewById(dotResIdArr[i]));
        }

//		currentTitleTextView = (TextView) findViewById(R.id.currentTitle);
//		currentTitleTextView.setText(titles[0]);//
    }




    @Override
    protected void onStart() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒钟切换一次图片显示
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
        super.onStart();
    }

    @Override
    protected void onStop() {
        // 当Activity不可见的时候停止切换
        scheduledExecutorService.shutdown();
        super.onStop();
    }

    /**
     * 换行切换任务
     *
     * @author Administrator
     */
    private class ScrollTask implements Runnable {

        public void run() {
            synchronized (viewPager) {
                currentItem = (currentItem + 1) % imageViewList.size();
                handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
            }
        }

    }

    /**
     * 当ViewPager中页面的状态发生改变时调用
     *
     * @author Administrator
     */
    private class MyPageChangeListener implements OnPageChangeListener {
        private int oldPosition = 0;

        /**
         * This method will be invoked when a new page becomes selected.
         * position: Position index of the new selected page.
         */
        public void onPageSelected(int position) {
            currentItem = position;
//			currentTitleTextView.setText(titles[position]);
            dotViewList.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
            dotViewList.get(position).setBackgroundResource(R.drawable.dot_focused);
            oldPosition = position;
        }

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    /**
     * 填充ViewPager页面的适配器
     *
     * @author Administrator
     */
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageResIdArr.length;
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(imageViewList.get(arg1));
            return imageViewList.get(arg1);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {

        }
    }


    private final class LunpanOnTouchListener implements View.OnTouchListener {
        private int RADIUS_MIN_LENGTH = 60;
        private int RADIUS_MAX_LENGTH = 200;
        //中心点坐标-全局
        private float centerPointX = -1;
        private float centerPointY = -1;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (centerPointX < 0) {
                int[] location = new int[2];
                view.getLocationOnScreen(location);

                centerPointX = location[0] + view.getWidth() / 2;
                centerPointY = location[1] + view.getHeight() / 2;

                RADIUS_MIN_LENGTH = view.getWidth() / 2 / 4;
                RADIUS_MAX_LENGTH = RADIUS_MIN_LENGTH * 3;
            }
            int clickX = (int) motionEvent.getRawX();
            int clickY = (int) motionEvent.getRawY();

            //检查是否在有效圆环内点击
            if (!checkRadiusAvail(clickX, clickY)) {
                selectItemIndex = -1;
                return false;
            }

            switch (motionEvent.getAction()) {
                //触摸屏幕时刻
                case MotionEvent.ACTION_DOWN:
                    int area = getDegreeArea(centerPointX, centerPointY, clickX, clickY);
                    Log.d("area", "touch_area=" + area);
                    selectItemIndex = area;
                    lunpanImageView.setImageResource(lunpanIdArr[area + 1]);
                    break;
                //触摸并移动时刻
                case MotionEvent.ACTION_MOVE:
                    break;
                //终止触摸时刻
                case MotionEvent.ACTION_UP:
                    lunpanImageView.setImageResource(lunpanIdArr[0]);
                    break;
            }
            return false;
        }

        /**
         * 检查是否在有效圆环内点击
         */
        private boolean checkRadiusAvail(int x, int y) {
            //计算点击位置到轮盘中心点的距离
            double length = Math.sqrt(Math.pow(centerPointX - x, 2) + Math.pow(centerPointY - y, 2));
            if (length >= RADIUS_MIN_LENGTH && length <= RADIUS_MAX_LENGTH)
                return true;
            return false;
        }

        /**
         * 获取点击角度所在的区域
         */
        private int getDegreeArea(double centerPointX, double centerPointY, double x, double y) {
            int initDegree = new Double(Math.acos((x - centerPointX) / Math.sqrt(Math.pow(x - centerPointX, 2) + Math.pow(y - centerPointY, 2))) / Math.PI * 180).intValue();
            int finalDegree = initDegree;
            //在第四象限，或第一象限
            if (y < centerPointY) {
                finalDegree = 360 - finalDegree;
            }
            //调整，坐标系逆时针旋转90度
            finalDegree += 90;
            if (finalDegree > 360) {
                finalDegree -= 360;
            }
            Log.d("degree", "init_degree=" + initDegree + " and final_degree=" + finalDegree);
            return finalDegree / (360 / 5);
        }
    }


    private final class ImageViewOnClickListener implements View.OnClickListener {
        private PopupWindow popWindow;
        @Override
        public void onClick(View view) {
            if(selectItemIndex<0){
                return ;
            }
            Log.d("msg", "==go detail use selectItemIndex=" + selectItemIndex);

            LayoutInflater inflater = (LayoutInflater) IndexActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View popWinView = inflater.inflate(R.layout.index_popwin, null, false);

            popWindow = new PopupWindow(popWinView, AbsListView.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);

            popWinView.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popWindow.dismiss();//Close the Pop Window
                }
            });

            ListView listView = (ListView) popWinView.findViewById(R.id.listView);
            //自定义Adapter适配器将数据绑定到item显示控件上
            //实现列表的显示
            listView.setAdapter(new ListViewAdapter2());
            //列表点击事件
            listView.setOnItemClickListener(new ScratchCardClickListener(view));

            popWindow.setAnimationStyle(R.style.popWinInOutAnimation);
            popWindow.setFocusable(true);
            popWindow.update();
            popWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        }

        private final class ListViewAdapter2 extends BaseAdapter {
            private int[] startScoreArr = new int[]{R.id.star_score1, R.id.star_score2, R.id.star_score3, R.id.star_score4, R.id.star_score5};

            private Context context = IndexActivity.this;
            private List<ScratchData> scratchDataList = ScratchData.createTempData();

            @Override
            public int getCount() {
                return scratchDataList.size();
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
                final View view = LayoutInflater.from(context).inflate(R.layout.index_popwin_item, null, false);
//              LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//              final View view = inflater.inflate(R.layout.index_popwin_item, null, false);

                ScratchData scratch = scratchDataList.get(position);
                //业务图片
                final ImageView scratchPicView = (ImageView) view.findViewById(R.id.scratchPicView);
                scratchPicView.setImageResource(scratch.getResPicId());
                final TextView nameTextView = (TextView) view.findViewById(R.id.name);
                nameTextView.setText(scratch.getName());
                final TextView topPrizeTextView = (TextView) view.findViewById(R.id.topPrize);
                topPrizeTextView.setText("最高奖:" + scratch.getTopPrize() + "万");
                final TextView backRateTextView = (TextView) view.findViewById(R.id.backRate);
                backRateTextView.setText("返奖率:" + scratch.getBackRate() + "%");
                //人气指数
                int i = 0;
                for (int j = scratch.getPopularityIndex() / 2; i < j; i++) {
                    ImageView scoreImageView = (ImageView) view.findViewById(startScoreArr[i]);
                    scoreImageView.setImageResource(R.drawable.bg_star_full);
                }
                if (scratch.getPopularityIndex() % 2 == 1) {
                    ImageView scoreImageView = (ImageView) view.findViewById(startScoreArr[i]);
                    scoreImageView.setImageResource(R.drawable.bg_star_half);
                }
                final ImageView showDetailView = (ImageView) view.findViewById(R.id.showDetailView);
                final View popularityIndexLayout = view.findViewById(R.id.popularityIndexLayout);

                showDetailView.setOnClickListener(new View.OnClickListener() {
                    private boolean expendFlag = false;
                    private TextView name = nameTextView;
                    private ImageView scratchPic = scratchPicView;
                    private TextView backRate = backRateTextView;
                    private View popularityIndex = popularityIndexLayout;

                    @Override
                    public void onClick(View v) {
                        expendFlag = !expendFlag;
                        //更换展开，合起图片
                        ((ImageView) v).setImageResource(expendFlag ? R.drawable.collipse11 : R.drawable.expand);
                        Context ctx = v.getContext();
                        if (expendFlag) {
                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)DisplayUtil.dip2px(ctx, 150), (int)DisplayUtil.dip2px(ctx, 150*80/100));
                            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                            scratchPic.setLayoutParams(layoutParams);
                        } else {
                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)DisplayUtil.dip2px(ctx, 100), (int)DisplayUtil.dip2px(ctx, 80));
                            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                            scratchPic.setLayoutParams(layoutParams);
                        }
                        backRate.setVisibility(expendFlag ? View.VISIBLE : View.INVISIBLE);
                        popularityIndex.setVisibility(expendFlag ? View.VISIBLE : View.INVISIBLE);
                    }
                });

                //条目点击事件
                //view.setOnClickListener(new BusinessItemClickListener());

                return view;
            }
        }
        //获取点击事件
        private final class ScratchCardClickListener implements AdapterView.OnItemClickListener {
            private View parentView;

            public ScratchCardClickListener(View view) {
                this.parentView = view;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//              LayoutInflater inflater = (LayoutInflater) IndexActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//              final View popWinView = inflater.inflate(R.layout.index_scratchcard, null, false);

                final View popWinView = LayoutInflater.from(IndexActivity.this).inflate(R.layout.index_scratchcard, null, false);


                final PopupWindow popWindow = new PopupWindow(popWinView, AbsListView.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);

                popWinView.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popWindow.dismiss();
                    }
                });

                popWindow.setAnimationStyle(R.style.scratchcardInOutAnimation);
                popWindow.setFocusable(true);
                popWindow.update();
                //popWindow.showAtLocation(parent, Gravity.CENTER_VERTICAL, 0, 0);
//                popWindow.showAtLocation((View) view.getParent(), Gravity.CENTER, 0, 0);
                popWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);

                LinearLayout scratchcardLayout = (LinearLayout) popWinView.findViewById(R.id.scratchcardLayout);
                scratchcardLayout.addView(new ScratchCardView(scratchcardLayout.getContext()));
            }
        }
    }
}
