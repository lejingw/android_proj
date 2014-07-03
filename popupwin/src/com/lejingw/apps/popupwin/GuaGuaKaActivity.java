package com.lejingw.apps.popupwin;

import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class GuaGuaKaActivity extends Activity {
//	int screenWidth = 0;
//	int screenHeight = 0;
	int screenWidth = 330;
	int screenHeight = 504;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		DisplayMetrics dm  = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//		screenWidth = dm.widthPixels;
//		screenHeight = dm.heightPixels;

		setContentView(new GuaGuaKa(this));
	}

	class GuaGuaKa extends View {
		private Path mPath = new Path();
		private Paint mPaint = null;
		private Bitmap bitmap = null;
		private Canvas mCanvas = null;

		public GuaGuaKa(Context context) {
			super(context);
			setBackgroundDrawable(getResources().getDrawable(R.drawable.img_stone_main));
			init(context);
		}

		private void init(Context context) {

			mPaint = new Paint();
			mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
			mPaint.setAntiAlias(true);
			mPaint.setDither(true);
			mPaint.setStyle(Style.STROKE);
			mPaint.setStrokeWidth(15);
			mPaint.setStrokeCap(Cap.ROUND);
			mPaint.setStrokeJoin(Join.ROUND);
			mPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
			mPaint.setAlpha(0);

			bitmap = Bitmap.createBitmap(screenWidth, screenHeight, Config.ARGB_8888);
			mCanvas = new Canvas(bitmap);
			mCanvas.drawColor(Color.GRAY);

			int width = 480;
			int height = 850;
			int x1 = -140;
			int y1 = -214;
			Rect dst = new Rect(x1, y1, x1+width, y1+height) ;
			Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.img_stone_scratch);
			mCanvas.drawBitmap(bitmap2, null, dst, null);
		}

		int startX = 140;
		int startY = 208;

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			mCanvas.drawPath(mPath, mPaint);
			canvas.drawBitmap(bitmap, startX, startY, null);
		}

		int x = 0;
		int y = 0;

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			int action = event.getAction();
			int currX = (int) event.getX() - startX;
			int currY = (int) event.getY() - startY;
			switch (action) {
				case MotionEvent.ACTION_DOWN: {
					mPath.reset();
					x = currX;
					y = currY;
					mPath.moveTo(x, y);
				}
				break;
				case MotionEvent.ACTION_MOVE: {
					mPath.quadTo(x, y, currX, currY);
					x = currX;
					y = currY;
					postInvalidate();
				}
				break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL: {
					mPath.reset();
				}
				break;
			}
			return true;
		}
	}
}