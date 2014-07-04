package com.lejingw.apps.scatchcard.index;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.lejingw.apps.scatchcard.IndexActivity;
import com.lejingw.apps.scatchcard.R;

public class LunpanOnTouchListener implements View.OnTouchListener {
    private int[] lunpanIdArr = new int[]{R.drawable.lunpan_0, R.drawable.lunpan_1,
            R.drawable.lunpan_2, R.drawable.lunpan_3, R.drawable.lunpan_4, R.drawable.lunpan_5};

    private IndexActivity indexActivity;

    //中心点坐标-全局
    private float centerPointX = -1;
    private float centerPointY = -1;
    private int RADIUS_MIN_LENGTH = -1;
    private int RADIUS_MAX_LENGTH = -1;

    public LunpanOnTouchListener(IndexActivity indexActivity) {
        this.indexActivity = indexActivity;
    }

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
            indexActivity.setSelectItemIndex(-1);
            return false;
        }

        switch (motionEvent.getAction()) {
            //触摸屏幕时刻
            case MotionEvent.ACTION_DOWN:
                int area = getDegreeArea(centerPointX, centerPointY, clickX, clickY);
                Log.d("area", "touch_area=" + area);
                indexActivity.setSelectItemIndex(area);
                indexActivity.setLunpanImageView(lunpanIdArr[area + 1]);
                break;
            //触摸并移动时刻
            case MotionEvent.ACTION_MOVE:
                break;
            //终止触摸时刻
            case MotionEvent.ACTION_UP:
                indexActivity.setLunpanImageView(lunpanIdArr[0]);
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