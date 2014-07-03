package com.lejingw.apps.scatchcard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class IndexActivity extends Activity {
    private ViewPager viewPager; // android-support-v4中的滑动组件
    private List<ImageView> imageViews; // 滑动的图片集合

    //	private String[] titles; // 图片标题
    private int[] imageResId; // 图片ID
    private List<View> dots; // 图片标题正文的那些点

    private final int[] lunpanIdArr = new int[]{R.drawable.lunpan_0, R.drawable.lunpan_1, R.drawable.lunpan_2, R.drawable.lunpan_3, R.drawable.lunpan_4, R.drawable.lunpan_5};
    private int selectItemIndex = -1;

    //	private TextView tv_title;
    private int currentItem = 0; // 当前图片的索引号

    private ImageView imageView;

    // An ExecutorService that can schedule commands to run after a given delay,
    // or to execute periodically.
    private ScheduledExecutorService scheduledExecutorService;

    // 切换当前显示的图片
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
        }
    };

    private void showPopWindow(Context context, View parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View vPopWindow = inflater.inflate(R.layout.select_item_list, null, false);
        //宽300 高300
//        final PopupWindow popWindow = new PopupWindow(vPopWindow, 300, 300, true);
		final PopupWindow popWindow = new PopupWindow(vPopWindow, AbsListView.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);

		Button okButton = (Button) vPopWindow.findViewById(R.id.button1);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(IndexActivity.this, "You click OK", Toast.LENGTH_SHORT).show();
            }
        });

        Button cancleButton = (Button) vPopWindow.findViewById(R.id.button2);
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss(); //Close the Pop Window
            }
        });
        popWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);

        imageResId = new int[]{R.drawable.img_roll_advs1_s, R.drawable.img_roll_advs2_s, R.drawable.img_roll_advs3_s, R.drawable.img_roll_advs4_s};
//		titles = new String[imageResId.length];
//		titles[0] = "title0";
//		titles[1] = "title1";
//		titles[2] = "title2";
//		titles[3] = "title3";

        imageViews = new ArrayList<ImageView>();

        // 初始化图片资源
        for (int i = 0; i < imageResId.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imageResId[i]);
            imageView.setScaleType(ScaleType.CENTER_CROP);
            imageViews.add(imageView);
        }


        dots = new ArrayList<View>();
        dots.add(findViewById(R.id.v_dot0));
        dots.add(findViewById(R.id.v_dot1));
        dots.add(findViewById(R.id.v_dot2));
        dots.add(findViewById(R.id.v_dot3));

//		tv_title = (TextView) findViewById(R.id.tv_title);
//		tv_title.setText(titles[0]);//

        viewPager = (ViewPager) findViewById(R.id.vp);
        viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
        // 设置一个监听器，当ViewPager中的页面改变时调用
        viewPager.setOnPageChangeListener(new MyPageChangeListener());

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("msg", "==go back");
                Intent intent = new Intent(IndexActivity.this, StartActivity.class);
                startActivity(intent);
                IndexActivity.this.finish();
            }
        });

        imageView = (ImageView) findViewById(R.id.btn_lunpan);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("msg", "==go detail use selectItemIndex=" + selectItemIndex);
                IndexActivity.this.showPopWindow(IndexActivity.this, view);
            }
        });
        imageView.setOnTouchListener(new View.OnTouchListener() {
            private float centerPointX = -1;
            private float centerPointY = -1;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (centerPointX < 0) {
                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    centerPointX = location[0] + view.getWidth() / 2;
                    centerPointY = location[1] + view.getHeight() / 2;
                }

                switch (motionEvent.getAction()) {
                    //触摸屏幕时刻
                    case MotionEvent.ACTION_DOWN:
                        int clickX = (int) motionEvent.getRawX();
                        int clickY = (int) motionEvent.getRawY();
                        int area = getDegreeArea(centerPointX, centerPointY, clickX, clickY);
                        selectItemIndex = area;
                        Log.d("area", "touch_area=" + area);
                        imageView.setImageResource(lunpanIdArr[area + 1]);
                        break;
                    //触摸并移动时刻
                    case MotionEvent.ACTION_MOVE:
                        break;
                    //终止触摸时刻
                    case MotionEvent.ACTION_UP:
                        imageView.setImageResource(lunpanIdArr[0]);
                        break;
                }
                return false;
            }

            private int getDegreeArea(double centerPointX, double centerPointY, double x, double y) {
                int degree = new Double(Math.acos((x - centerPointX) / Math.sqrt(Math.pow(x - centerPointX, 2) + Math.pow(y - centerPointY, 2))) / Math.PI * 180).intValue();

                Log.d("degree", "init_degree=" + degree);
                //在第四象限，或第一象限
                if (y < centerPointY) {
                    degree = 360 - degree;
                }
                //调整，坐标系逆时针旋转90度
                degree += 90;
                if (degree > 360) {
                    degree -= 360;
                }
                Log.d("degree", "final_degree=" + degree);
                return degree / (360 / 5);
            }
        });

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
                //System.out.println("currentItem: " + currentItem);
                currentItem = (currentItem + 1) % imageViews.size();
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
//			tv_title.setText(titles[position]);
            dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
            dots.get(position).setBackgroundResource(R.drawable.dot_focused);
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
}
