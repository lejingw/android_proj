package com.lejingw.apps.scatchcard.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.lejingw.apps.scatchcard.MainActivity;
import com.lejingw.apps.scatchcard.R;
import com.lejingw.apps.scatchcard.StartActivity;
import com.lejingw.apps.scatchcard.StartActivity2;
import com.lejingw.apps.scatchcard.index.LunpanOnTouchListener;

public class Start3Activity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.start_introduce3);

        ImageView imageView = (ImageView) findViewById(R.id.startIntroduceImageView);

        imageView.setOnClickListener(lastImageClickListener);
        imageView.setOnTouchListener(lastImageTouchListener);
	}

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(this, Start2Activity.class);
//        startActivity(intent);
//        this.finish();
//    }


    private View.OnClickListener lastImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("msg", "click=" + " x:" + v.getX() + " y:" + v.getY());
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
            if(CLICK_Y_MIN<0){
                DisplayMetrics dm  = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                CLICK_X_LENGTH = dm.widthPixels / 3;
                CLICK_X_MIN = dm.widthPixels / 3;

                CLICK_Y_LENGTH = CLICK_X_LENGTH / 2;
                CLICK_Y_MIN = dm.heightPixels - CLICK_Y_LENGTH;

                Log.d("msg", "touch=" + " x:" + dm.widthPixels + " y:" + CLICK_Y_MIN);
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
                    Log.d("msg", "touch=" + " x:" + event.getX() + " y:" + event.getY());
                    //判断为点击事件
                    if (Math.abs(touchStartX - event.getX()) < CLICK_STEP_LENGTH && Math.abs(touchStartY - event.getY()) < CLICK_STEP_LENGTH) {
                        int clickX = (int)(touchStartX + event.getRawX())/2;
                        int clickY = (int)(touchStartY + event.getRawY())/2;
                        //判断为有效点击
                        if(clickX>=CLICK_X_MIN && clickX<=(CLICK_X_MIN+CLICK_X_LENGTH) && clickY>=CLICK_Y_MIN && clickY<=(CLICK_Y_MIN+CLICK_Y_LENGTH)){
                            goToMainActiviti();
                            return false;
                        }
                    }
                    break;
            }
            return true;
        }
    };

    private void goToMainActiviti(){
        overridePendingTransition(R.anim.popwin_in, R.anim.popwin_out);
//		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
//        overridePendingTransition(R.anim.scratchcardwin_in, R.anim.scratchcardwin_out);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}