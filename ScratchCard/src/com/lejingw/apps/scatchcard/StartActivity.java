package com.lejingw.apps.scatchcard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.lejingw.apps.scatchcard.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends Activity {
    private ViewPager viewPager; // android-support-v4中的滑动组件

    private List<ImageView> imageViews = new ArrayList<ImageView>(); // 滑动的图片集合
    private int[] imageResId = new int[]{R.drawable.img_introduce1, R.drawable.img_introduce2, R.drawable.img_introduce3}; // 图片ID
    private GestureDetector gestureDetector;

    private int currentItem;
    /**
     * 记录当前分页ID
     */
    private int flaggingWidth;// 互动翻页所需滚动的长度是当前屏幕宽度的1/3

    // An ExecutorService that can schedule commands to run after a given delay, or to execute periodically.
//	private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        gestureDetector = new GestureDetector(new GuideViewTouch());
        // 获取分辨率
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        flaggingWidth = dm.widthPixels / 4;

        // 初始化图片资源
        for (int i = 0; i < imageResId.length; i++) {
            ImageView imageView = new ImageView(this);
//            imageView.setImageResource(imageResId[i]);
            imageView.setImageBitmap(ImageUtil.readBitMap(this, imageResId[i]));
            imageView.setScaleType(ScaleType.FIT_XY);

            if (i + 1 >= imageResId.length) {
                imageView.setOnClickListener(lastImageClickListener);
                imageView.setOnTouchListener(lastImageTouchListener);
            }

            imageViews.add(imageView);
        }
        viewPager = (ViewPager) findViewById(R.id.startViewPager);
        // 设置填充ViewPager页面的适配器
        viewPager.setAdapter(new MyAdapter());
        // 设置一个监听器，当ViewPager中的页面改变时调用
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }

    // 切换当前显示的图片
//    private Handler handler = new Handler() {
//        public void handleMessage(android.os.Message msg) {
//            viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
//        }
//    };


    private View.OnClickListener lastImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("msg", "x:" + v.getX() + " y:" + v.getY());
        }
    };

    View.OnTouchListener lastImageTouchListener = new View.OnTouchListener() {
        private float touchStartX = 0;
        private float touchStartY = 0;
        private final float CLICK_STEP_LENGTH = 20;

        private float CLICK_X_MIN = 160;
        private float CLICK_X_LENGTH = 160;

        private float CLICK_Y_MIN = -1;
        private float CLICK_Y_LENGTH = -1;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (CLICK_Y_MIN < 0) {
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                CLICK_X_LENGTH = dm.widthPixels / 3;
                CLICK_X_MIN = dm.widthPixels / 3;

                CLICK_Y_LENGTH = CLICK_X_LENGTH / 2;
                CLICK_Y_MIN = dm.heightPixels - CLICK_Y_LENGTH;

                Log.d("msg", "x:" + dm.widthPixels + " y:" + CLICK_Y_MIN);
            }
            switch (event.getAction()) {
                //触摸屏幕时刻
                case MotionEvent.ACTION_DOWN:
                    touchStartX = event.getRawX();
                    touchStartY = event.getRawY();
                    break;
                //触摸并移动时刻
                case MotionEvent.ACTION_MOVE:
                    break;
                //终止触摸时刻
                case MotionEvent.ACTION_UP:
                    Log.d("msg", "x:" + event.getX() + " y:" + event.getY());
                    //判断为点击事件
                    if (Math.abs(touchStartX - event.getX()) < CLICK_STEP_LENGTH && Math.abs(touchStartY - event.getY()) < CLICK_STEP_LENGTH) {
                        int clickX = (int) (touchStartX + event.getRawX()) / 2;
                        int clickY = (int) (touchStartY + event.getRawY()) / 2;
                        //判断为有效点击
                        if (clickX >= CLICK_X_MIN && clickX <= (CLICK_X_MIN + CLICK_X_LENGTH) && clickY >= CLICK_Y_MIN && clickY <= (CLICK_Y_MIN + CLICK_Y_LENGTH)) {
                            goToMainActiviti();
                            return false;
                        }
                    }
                    break;
            }
            return StartActivity.super.onTouchEvent(event);
        }
    };


    @Override
    protected void onStart() {
//		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒钟切换一次图片显示
//		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
        super.onStart();
    }

    @Override
    protected void onStop() {
        // 当Activity不可见的时候停止切换
//		scheduledExecutorService.shutdown();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        currentItem = (currentItem - 1) % imageViews.size();
        Log.d("msg", "currentItem = " + currentItem);
        if (currentItem < 0) {
            finish();
        } else {
            viewPager.setCurrentItem(currentItem);
        }
    }

    /**
     * 换行切换任务
     *
     * @author Administrator
     */
//	private class ScrollTask implements Runnable {
//		public void run() {
//			synchronized (viewPager) {
//				currentItem = (currentItem + 1) % imageViews.size();
//				handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
//			}
//		}
//
//	}

    /**
     * 当ViewPager中页面的状态发生改变时调用
     *
     * @author Administrator
     */
    private class MyPageChangeListener implements OnPageChangeListener {
        private int oldPosition = 0;
        private boolean misScrolled;

        /**
         * This method will be invoked when a new page becomes selected.
         * position: Position index of the new selected page.
         */
        public void onPageSelected(int position) {
            currentItem = position;
//			tv_title.setText(titles[position]);
//			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
//			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
            oldPosition = position;
        }

        public void onPageScrollStateChanged(int state) {
//            switch (state) {
//                case ViewPager.SCROLL_STATE_DRAGGING:
//                    misScrolled = false;
//                    break;
//                case ViewPager.SCROLL_STATE_SETTLING:
//                    misScrolled = true;
//                    break;
//                case ViewPager.SCROLL_STATE_IDLE:
//                    if (StartActivity.this.viewPager.getCurrentItem() == StartActivity.this.viewPager.getAdapter().getCount() - 1 && !misScrolled) {
//                        goToMainActiviti();
//                    }
//                    misScrolled = true;
//                    break;
//            }
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    /**
     * 填充ViewPager页面的适配器
     *
     * @author Administrator
     */
    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageResId.length;
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(imageViews.get(arg1));
            return imageViews.get(arg1);
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
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(event);
    }

    private class GuideViewTouch extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            if (currentItem == 2) {
                if (Math.abs(e1.getX() - e2.getX()) > Math.abs(e1.getY()
                        - e2.getY())
                        && (e1.getX() - e2.getX() <= (-flaggingWidth) || e1
                        .getX() - e2.getX() >= flaggingWidth)) {
                    if (e1.getX() - e2.getX() >= flaggingWidth) {
                        goToMainActiviti();
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private void goToMainActiviti() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

//		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        overridePendingTransition(R.anim.scratchcardwin_in, R.anim.scratchcardwin_out2);
//        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goToMainActiviti();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
