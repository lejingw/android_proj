package com.lejingw.apps.scratchcard;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class MainActivity extends Activity {
    private ViewPager viewPager;
    private int default_bg = R.drawable.a;
    private List<ImageView> imageViews;

    private String[] imageResUrl;
    private List<ImageView> dots;

    private int currentItem = 0;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                List<Bitmap> bmList = (List<Bitmap>) msg.obj;
                for (int i = 0; i < bmList.size(); i++) {
                    Bitmap bitmap = bmList.get(i);
                    ImageView imageView = new ImageView(getApplicationContext());
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    } else {
                        imageView.setImageResource(default_bg);
                    }
                    imageView.setScaleType(ScaleType.CENTER_CROP);
                    imageViews.add(imageView);
                }

                viewPager = (ViewPager) findViewById(R.id.img_viewpager);
                viewPager.setAdapter(new MyPagerAdapter());
                viewPager.setOnPageChangeListener(new MyPageChangeListener());
            }
        };

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        imageViews = new ArrayList<ImageView>();
        new Thread() {
            public void run() {
                List<Bitmap> bitmapList = new ArrayList<Bitmap>();
                Message msg = new Message();
                Bitmap bitmap = null;
                imageResUrl = new String[] {
                        "http://imgt8.bdstatic.com/it/u=2,1200870009&fm=19&gp=0.jpg",
                        "http://imgt3.bdstatic.com/it/u=2483720495,3389680904&fm=21&gp=0.jpg",
                        "http://imgt6.bdstatic.com/it/u=2,936516090&fm=19&gp=0.jpg" };
                for (int i = 0; i < imageResUrl.length; i++) {
                    bitmap = ImageUtils.getBitmap(imageResUrl[i]);
                    bitmapList.add(bitmap);

                }
                msg.what = 0;
                msg.obj = bitmapList;
                handler.sendMessage(msg);
            }

        }.start();

        dots = new ArrayList<ImageView>();
        dots.add((ImageView) findViewById(R.id.v_dot1));
        dots.add((ImageView) findViewById(R.id.v_dot2));
        dots.add((ImageView) findViewById(R.id.v_dot3));
        dots.get(currentItem).setImageResource(R.drawable.orange_btn);
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imageResUrl.length;
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

    private class MyPageChangeListener implements OnPageChangeListener {
        private int oldPosition = 0;
        public void onPageSelected(int position) {
            currentItem = position;
            dots.get(oldPosition).setImageResource(R.drawable.white_btn);
            dots.get(position).setImageResource(R.drawable.orange_btn);
            oldPosition = position;
        }
        public void onPageScrollStateChanged(int arg0) {
        }
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    }
}
