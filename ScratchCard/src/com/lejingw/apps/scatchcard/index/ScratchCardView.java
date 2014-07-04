package com.lejingw.apps.scatchcard.index;

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
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.lejingw.apps.scatchcard.R;
import com.lejingw.apps.scatchcard.util.DisplayUtil;

public class ScratchCardView extends ImageView {
    private final Context context;
    private final float scale;
    //    private int startX = 140;
//    private int startY = 193;
//    private int screenWidth = 326;
//    private int screenHeight = 469;

    /****根据限速密度定义　坐标，宽，高****/
    private float startX = 93.333336f;
    private float startY = 128.66667f;
    private float screenWidth = 217.33333f;
    private float screenHeight = 312.66666f;


//    private int x1 = -140;
//    private int y1 = -200;
//    private int width = 478;
//    private int height = 794;
    private float x1 = -93.333336f;
    private float y1 = -133.33333f;
    private float width = 318.66666f;
    private float height = 529.3333f;

    private Path mPath = new Path();
    private Paint mPaint = null;
    private Bitmap bitmap = null;
    private Canvas mCanvas = null;

    public ScratchCardView(Context context) {
        super(context);
        this.context = context;
        this.scale = context.getResources().getDisplayMetrics().density;

        setScaleType(ScaleType.FIT_XY);
        setImageResource(R.drawable.img_stone_main);
        init();

//        System.out.println("========================");
//        System.out.println("15 ====>" + DisplayUtil.px2dip2(context, 15) + "==========>"  + DisplayUtil.dip2px2(context, 5.0f));
//        System.out.println("140 ====>" + DisplayUtil.px2dip2(context, 140) + "==========>"  + DisplayUtil.dip2px2(context, 93.333336f));
//        System.out.println("193 ====>" + DisplayUtil.px2dip2(context, 193) + "==========>"  + DisplayUtil.dip2px2(context, 128.66667f));
//        System.out.println("326 ====>" + DisplayUtil.px2dip2(context, 326) + "==========>"  + DisplayUtil.dip2px2(context, 217.33333f));
//        System.out.println("469 ====>" + DisplayUtil.px2dip2(context, 469) + "==========>" + DisplayUtil.dip2px2(context, 312.66666f));
//        System.out.println("200 ====>" + DisplayUtil.px2dip2(context, 200) + "==========>" + DisplayUtil.dip2px2(context, 133.33333f));
//        System.out.println("478 ====>" + DisplayUtil.px2dip2(context, 478) + "==========>" + DisplayUtil.dip2px2(context, 318.66666f));
//        System.out.println("794 ====>" + DisplayUtil.px2dip2(context, 794) + "==========>" + DisplayUtil.dip2px2(context, 529.3333f));
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(10 * scale);
        mPaint.setStrokeCap(Cap.ROUND);
        mPaint.setStrokeJoin(Join.ROUND);
        mPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        mPaint.setAlpha(0);

//        bitmap = Bitmap.createBitmap(screenWidth, screenHeight, Config.ARGB_8888);
        bitmap = Bitmap.createBitmap(DisplayUtil.dip2px(context, screenWidth), DisplayUtil.dip2px(context, screenHeight), Config.ARGB_8888);
        mCanvas = new Canvas(bitmap);
        mCanvas.drawColor(Color.GRAY);

//        Rect dst = new Rect(x1, y1, x1 + width, y1 + height);
        Rect dst = new Rect(DisplayUtil.dip2px(context, x1), DisplayUtil.dip2px(context, y1),
                DisplayUtil.dip2px(context, x1 + width), DisplayUtil.dip2px(context, y1 + height));
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.img_stone_scratch);
        mCanvas.drawBitmap(bitmap2, null, dst, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas.drawPath(mPath, mPaint);
//        canvas.drawBitmap(bitmap, startX, startY, null);
        canvas.drawBitmap(bitmap, DisplayUtil.dip2px(context, startX), DisplayUtil.dip2px(context, startY), null);
    }

    int x = 0;
    int y = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
//        int currX = (int) event.getX() - startX;
//        int currY = (int) event.getY() - startY;
        int currX = (int) event.getX() - DisplayUtil.dip2px(context, startX);
        int currY = (int) event.getY() - DisplayUtil.dip2px(context, startY);
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
