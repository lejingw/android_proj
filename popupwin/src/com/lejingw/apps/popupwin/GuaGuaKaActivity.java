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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GuaGuaKaActivity extends Activity {
//	int screenWidth = 0;
//	int screenHeight = 0;
//	int screenWidth = 330;
//	int screenHeight = 504;
    private int imageViewWidth = -1, imageViewHeight = -1;
    private int bgWidth, bgHeight;
    private float sx, sy;


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

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_stone_main);
            //下句无效，不能有效的支持图片拉伸
//        setImageBitmap(bitmap);
            Log.d("msg", bitmap.getWidth() + "---------------" + bitmap.getHeight());
            bgWidth = bitmap.getWidth();
            bgHeight = bitmap.getHeight();

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

        }
        private void initCover(){

            Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.img_stone_scratch);

            int width = (int)(sx * bitmap2.getWidth());
            int height = (int)(sy * bitmap2.getHeight());

            bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            mCanvas = new Canvas(bitmap);
            mCanvas.drawColor(Color.GRAY);


			Rect dst = new Rect(0, 0, width, height) ;
			mCanvas.drawBitmap(bitmap2, null, dst, null);
		}

		int startX = 140;
		int startY = 208;

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
            if (imageViewWidth < 0) {
                synchronized (this) {
                    imageViewWidth = canvas.getWidth();
                    imageViewHeight = canvas.getHeight();
                    sx = (float)imageViewWidth / bgWidth;
                    sy = (float)imageViewHeight / bgHeight;
                    initCover();
                }
            }
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