package com.lejingw.apps.scatchcard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 仿优酷Android客户端图片左右滑动
 */
public class StartActivity extends Activity {
	private ViewPager viewPager; // android-support-v4中的滑动组件
	private View toMainView;

	private List<ImageView> imageViews; // 滑动的图片集合
	private int[] imageResId; // 图片ID
	private int currentItem = 0; // 当前图片的索引号

	// An ExecutorService that can schedule commands to run after a given delay,
	// or to execute periodically.
//	private ScheduledExecutorService scheduledExecutorService;

	// 切换当前显示的图片
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
		}
	};


	private View.OnClickListener lastImageClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			System.out.println("click=" + currentItem + " x:" + v.getX() + " y:" + v.getY());
		}
	};

	View.OnTouchListener lastImageTouchListener = new View.OnTouchListener() {
		private float touchStartX = 0;
		private float touchStartY = 0;
		private final float CLICK_STEP_LENGTH = 20;
		private final float CLICK_X_MIN = 200;
		private final float CLICK_X_MAX = 300;
		private final float CLICK_Y_MIN = 800;
		private final float CLICK_Y_MAX = 850;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int[] origin = new int[2];
			switch (event.getAction()) {
				//触摸屏幕时刻
				case MotionEvent.ACTION_DOWN:
					touchStartX = event.getX();
					touchStartY = event.getY();
					break;
				//触摸并移动时刻
				case MotionEvent.ACTION_MOVE:
					break;
				//终止触摸时刻
				case MotionEvent.ACTION_UP:
					showXY(event.getX(), event.getY());
					//判断为点击事件
					if (Math.abs(touchStartX - event.getX()) < CLICK_STEP_LENGTH && Math.abs(touchStartY - event.getY()) < CLICK_STEP_LENGTH) {
						int clickX = (int)(touchStartX + event.getX())/2;
						int clickY = (int)(touchStartY + event.getY())/2;
						//判断为有效点击
						if(clickX>=CLICK_X_MIN && clickX<=CLICK_X_MAX && clickY>=CLICK_Y_MIN && clickY<=CLICK_Y_MAX){
							goToMainActiviti();
							return false;
						}
					}
					break;
			}

			return StartActivity.super.onTouchEvent(event);
		}
	};
	private void goToMainActiviti(){
		Log.d("msg", "========goToMainActiviti===========");
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		this.finish();
	}

	private void showXY(float x, float y) {
		Log.d("msg", "touch=" + currentItem + " x:" + x + " y:" + y);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);

		imageResId = new int[]{R.drawable.img_introduce1, R.drawable.img_introduce2, R.drawable.img_introduce3};
		imageViews = new ArrayList<ImageView>();

		// 初始化图片资源
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);

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

	/**
	 * 换行切换任务
	 *
	 * @author Administrator
	 */
	private class ScrollTask implements Runnable {
		public void run() {
			synchronized (viewPager) {
				System.out.println("currentItem: " + currentItem);
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
//			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
//			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
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
