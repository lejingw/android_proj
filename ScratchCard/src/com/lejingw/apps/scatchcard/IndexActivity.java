package com.lejingw.apps.scatchcard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import android.view.*;
import android.widget.*;
import android.widget.ImageView.ScaleType;
import com.lejingw.apps.scatchcard.index.ImageViewOnClickListener;
import com.lejingw.apps.scatchcard.index.LunpanOnTouchListener;
import com.lejingw.apps.scatchcard.util.ImageUtil;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IndexActivity extends Activity {
    private ViewPager viewPager;

    //private String[] titles = new String[]{"title1", "title2", "title3", "title4"}; // 图片标题
    //private TextView currentTitleTextView;
    private int[] imageResIdArr = new int[]{R.drawable.img_roll_advs1_s, R.drawable.img_roll_advs2_s, R.drawable.img_roll_advs3_s, R.drawable.img_roll_advs4_s};
    private int[] dotResIdArr = new int[]{R.id.v_dot0, R.id.v_dot1, R.id.v_dot2, R.id.v_dot3};
    private List<ImageView> imageViewList;
    private List<View> dotViewList; // 图片标题正文的那些点

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
        viewPager.setOnPageChangeListener(new ViewPageChangeListener());

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
        lunpanImageView.setOnTouchListener(new LunpanOnTouchListener(this));
        lunpanImageView.setOnClickListener(new ImageViewOnClickListener(this));
    }

    private void initImageAndDot(){
        imageViewList = new ArrayList<ImageView>();
        // 初始化图片资源
        for (int i = 0; i < imageResIdArr.length; i++) {
            ImageView imageView = new ImageView(this);
//          imageView.setImageResource(imageResIdArr[i]);
            imageView.setImageBitmap(ImageUtil.readBitMap(this, imageResIdArr[i]));
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
    private class ViewPageChangeListener implements OnPageChangeListener {
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


    public void setSelectItemIndex(int selectItemIndex) {
        this.selectItemIndex = selectItemIndex;
    }

    public int getSelectItemIndex(){
        return this.selectItemIndex;
    }

    public void setLunpanImageView(int lunpanImageResId) {
//        this.lunpanImageView.setImageResource(lunpanImageResId);
        lunpanImageView.setImageBitmap(ImageUtil.readBitMap(this, lunpanImageResId));
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }
}
