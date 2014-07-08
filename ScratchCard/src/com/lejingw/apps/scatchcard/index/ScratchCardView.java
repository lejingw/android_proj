package com.lejingw.apps.scatchcard.index;

import android.content.Context;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.lejingw.apps.scatchcard.R;
import com.lejingw.apps.scatchcard.util.DisplayUtil;
import com.lejingw.apps.scatchcard.util.ImageUtil;

public class ScratchCardView extends ImageView {
    private final Context context;
    private final float scale;

    private int imageViewWidth;
    private int imageViewHeight;

    private float canvasStartXRate;
    private float canvasStartYRate;
    //测试机器MI1S,480*854分辨率
//    private int canvasStartX;
//    private int canvasStartY;

    private Path drawPath = new Path();
    private Paint drawPaint = null;
    private Bitmap drawBitmap = null;
    private Canvas drawCanvas = null;

    public ScratchCardView(Context context) {
        super(context);
        this.context = context;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.scale = displayMetrics.density;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, DisplayUtil.dip2px(context, 40));
        setLayoutParams(lp);
        setScaleType(ScaleType.FIT_XY);

        setImageResource(R.drawable.img_stone_main);
//        setImageBitmap(ImageUtil.readBitMap(context, R.drawable.img_stone_main));

        init();
    }

    private void init() {
        drawPaint = new Paint();
        drawPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        drawPaint.setAntiAlias(true);
        drawPaint.setDither(true);
        drawPaint.setStyle(Style.STROKE);
        drawPaint.setStrokeWidth(10 * scale);
        drawPaint.setStrokeCap(Cap.ROUND);
        drawPaint.setStrokeJoin(Join.ROUND);
        drawPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        drawPaint.setAlpha(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == drawCanvas) {
            synchronized (this) {
                imageViewWidth = canvas.getWidth();
                imageViewHeight = canvas.getHeight();
                initCanvas();
            }
        }
        drawCanvas.drawPath(drawPath, drawPaint);
//        canvas.drawBitmap(drawBitmap, canvasStartX, canvasStartY, null);
        canvas.drawBitmap(drawBitmap, canvasStartXRate * imageViewWidth, canvasStartYRate * imageViewHeight, null);
    }

    private void initCanvas() {
        if (null == drawCanvas) {
            canvasStartXRate = 0.2916666666666667f;
            canvasStartYRate = 0.24250681198910082f;
            float canvasWidthRate = 0.6791666666666667f;
            float canvasHeightRate = 0.5912806539509536f;
//            canvasStartX = 140;
//            canvasStartY = 178;
//            int canvasWidth = 326;
//            int canvasHeight = 434;

//            drawBitmap = Bitmap.createBitmap(canvasWidth, canvasHeight, Config.ARGB_8888);
            drawBitmap = Bitmap.createBitmap((int)(canvasWidthRate * imageViewWidth), (int)(canvasHeightRate * imageViewHeight), Config.ARGB_8888);
            drawCanvas = new Canvas(drawBitmap);
            drawCanvas.drawColor(Color.GRAY);

            float dstLeftRate = -0.2916666666666667f;
            float dstTopRate = -0.24795640326975477f;
            float dstRightRate = 0.7041666666666667f;
            float dstBottomRate = 0.7438692098092643f;
//            int dstLeft = -140;
//            int dstTop = -182;
//            int dstRight = 338;
//            int dstBottom = 546;

//            System.out.println("====>canvasStartX/imageViewWidth=" + (canvasStartX * 1.0 / imageViewWidth));
//            System.out.println("====>canvasStartY/imageViewWidth=" + (canvasStartY * 1.0 / imageViewHeight));
//            System.out.println("====>canvasWidth/imageViewWidth=" + (canvasWidth * 1.0 / imageViewWidth));
//            System.out.println("====>canvasHeight/imageViewWidth=" + (canvasHeight * 1.0 / imageViewHeight));
//            System.out.println("====>dstLeft/imageViewWidth=" + (dstLeft * 1.0 / imageViewWidth));
//            System.out.println("====>dstTop/imageViewWidth=" + (dstTop * 1.0 / imageViewHeight));
//            System.out.println("====>dstRight/imageViewWidth=" + (dstRight * 1.0 / imageViewWidth));
//            System.out.println("====>dstBottom/imageViewWidth=" + (dstBottom * 1.0 / imageViewHeight));

            Rect dst = new Rect((int)(dstLeftRate * imageViewWidth), (int)(dstTopRate * imageViewHeight),
                        (int)(dstRightRate * imageViewWidth), (int)(dstBottomRate * imageViewHeight));
//            Rect dst = new Rect(dstLeft, dstTop, dstRight, dstBottom);
            Bitmap coverBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_stone_scratch);
            drawCanvas.drawBitmap(coverBitmap, null, dst, null);
        }
    }

    int preClickX = 0;
    int preClickY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
//        int currClickX = (int) event.getX() - canvasStartX;
//        int currClickY = (int) (int) event.getY() - canvasStartY;
        int currClickX = (int) event.getX() - (int)(canvasStartXRate * imageViewWidth);
        int currClickY = (int) event.getY() - (int)(canvasStartYRate * imageViewHeight);
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                drawPath.reset();
                preClickX = currClickX;
                preClickY = currClickY;
                drawPath.moveTo(preClickX, preClickY);
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                drawPath.quadTo(preClickX, preClickY, currClickX, currClickY);
                postInvalidate();
                preClickX = currClickX;
                preClickY = currClickY;
            }
            break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                drawPath.reset();
            }
            break;
        }
        return true;
    }
}
