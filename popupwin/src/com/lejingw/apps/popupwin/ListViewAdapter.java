package com.lejingw.apps.popupwin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

public class ListViewAdapter extends SimpleAdapter {
    private ImageView imageView;
    private ImageView expandImageView;

    public ListViewAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        boolean flag = null == convertView;
        View view = super.getView(position, convertView, parent);
        if(flag) {
            imageView = (ImageView) view.findViewById(R.id.imageView);
            expandImageView = (ImageView) view.findViewById(R.id.expandImageView);

            expandImageView.setOnClickListener(new View.OnClickListener() {
                private boolean expendFlag = false;

                @Override
                public void onClick(View v) {
                    expendFlag = !expendFlag;
                    //更换展开，合并图片
//                    expandImageView.setImageResource(expendFlag?R.drawable.collipse:R.drawable.expand);
                    System.out.println(expandImageView);
                    System.out.println(v);
                    System.out.println("============================expendFlag=" + expendFlag);
                    expandImageView.setImageResource(R.drawable.collipse11);
//					//点击后变大的位图：
//					Bitmap btm2=Bitmap.createScaledBitmap(btm1, 40, 50, false); //这里你可以自定义它的大小
//					BitmapDrawable bd2=BitmapDrawable(btm2);
//					imageView.setBackgroundDrawable(v.getResources().getDrawable(R.drawable.collipse));

//					expandImageView.setImageBitmap(BitmapFactory.decodeResource(imageView.getResources(), R.drawable.collipse));
//					expandImageView.refreshDrawableState();
//					expandImageView.invalidate();
                    //                imageView.setHei
                    ////                Bitmap bitmapOrg = imageView.getDrawingCache();
                    //                Bitmap bitmapOrg = BitmapFactory.decodeResource(imageView.getResources(), R.drawable.icon48x48_1);
                    //                // 创建操作图片用的matrix对象
                    //                Matrix matrix = new Matrix();
                    //                // 缩放图片动作
                    //                matrix.postScale(bitmapOrg.getWidth(), bitmapOrg.getHeight());
                    //                // 旋转图片动作
                    //                matrix.postRotate(90);
                    //                // 创建新的图片
                    //                Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true);
                    //                //将上面创建的Bitmap转换成Drawable对象，使得其可以使用在 ImageView, ImageButton中
                    //                BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
                    //                imageView.setImageDrawable(bmd);
                    //                imageView.setImageBitmap(resizedBitmap);
                }
            });
        }
        return view;
    }

//    private float scaleWidth = 1;
//    private float scaleHeight = 1;
//    private int id=0;
//    /* 图片缩小的method */
//    private void small(Bitmap bmp)    {
//        int bmpWidth=bmp.getWidth();
//        int bmpHeight=bmp.getHeight();
//
//        /* 设置图片缩小的比例 */
//        double scale = 0.8;
//        /* 计算出这次要缩小的比例 */
//        scaleWidth = (float)(scaleWidth*scale);
//        scaleHeight = (float)(scaleHeight*scale);
//        /* 产生reSize后的Bitmap对象 */
//        Matrix matrix = new Matrix();
//        matrix.postScale(scaleWidth, scaleHeight);
//        Bitmap resizeBmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight, matrix, true);
//
//        if(id==0) {
//            /* 如果是第一次按，就删除原来默认的ImageView */
//            layoutImage.removeView(mImageView);
//        } else {
//            /* 如果不是第一次按，就删除上次放大缩小所产生的ImageView */
//            layoutImage.removeView((ImageView)findViewById(id));
//        }
//
//        /* 产生新的ImageView，放入reSize的Bitmap对象，再放入Layout中 */
//        id++;
//        ImageView imageView = new ImageView(this);
//        imageView.setId(id);
//        imageView.setImageBitmap(resizeBmp);
//        layoutImage.addView(imageView);
//        Log.i(TAG, "imageView.getWidth() = " + imageView.getWidth()
//                + ", imageView.getHeight() = " + imageView.getHeight());
//        setContentView(layout1);
//        /* 因为图片放到最大时放大按钮会disable，所以在缩小时把它重设为enable */
//        mButton02.setEnabled(true);
//        mButton02.setTextColor(Color.MAGENTA);
//    }
//
//    /* 图片放大的method */
//    private void big(Bitmap bmp) {
//        int bmpWidth=bmp.getWidth();
//        int bmpHeight=bmp.getHeight();
//
//        /* 设置图片放大的比例 */
//        double scale=1.25;
//        /* 计算这次要放大的比例 */
//        scaleWidth=(float)(scaleWidth*scale);
//        scaleHeight=(float)(scaleHeight*scale);
//        /* 产生reSize后的Bitmap对象 */
//        Matrix matrix = new Matrix();
//        matrix.postScale(scaleWidth, scaleHeight);
//        Bitmap resizeBmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight, matrix, true);
//
//        if(id==0) {
//            /* 如果是第一次按，就删除原来设置的ImageView */
//            layoutImage.removeView(mImageView);
//        } else {
//            /* 如果不是第一次按，就删除上次放大缩小所产生的ImageView */
//            layoutImage.removeView((ImageView)findViewById(id));
//        }
//
//        /* 产生新的ImageView，放入reSize的Bitmap对象，再放入Layout中 */
//        id++;
//        ImageView imageView = new ImageView(this);
//        imageView.setId(id);
//        imageView.setImageBitmap(resizeBmp);
//        layoutImage.addView(imageView);
//        setContentView(layout1);
//        /* 如果再放大会超过屏幕大小，就把Button disable */
//        if( scaleWidth * scale * bmpWidth > bmpWidth * 3 ||
//                scaleHeight * scale * bmpHeight > bmpWidth * 3 ||
//                scaleWidth * scale * bmpWidth > displayWidth * 5 ||
//                scaleHeight * scale * bmpHeight > displayHeight * 5) {
//            mButton02.setEnabled(false);
//            mButton02.setTextColor(Color.GRAY);
//        } else {
//            mButton02.setEnabled(true);
//            mButton02.setTextColor(Color.MAGENTA);
//        }
//    }
}
