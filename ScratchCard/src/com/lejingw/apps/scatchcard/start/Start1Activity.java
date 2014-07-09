package com.lejingw.apps.scatchcard.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import com.lejingw.apps.scatchcard.R;
import com.lejingw.apps.scatchcard.StartActivity;
import com.lejingw.apps.scatchcard.StartActivity2;

public class Start1Activity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.img_introduce1);
		setContentView(imageView);
	}

//    @Override
//    public void onBackPressed() {
//        this.finish();
//    }
}