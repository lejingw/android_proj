package com.lejingw.apps.scatchcard.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import com.lejingw.apps.scatchcard.R;
import com.lejingw.apps.scatchcard.StartActivity;
import com.lejingw.apps.scatchcard.StartActivity2;

public class Start2Activity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.img_introduce2);
		setContentView(imageView);
	}

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(this, Start1Activity.class);
//        startActivity(intent);
//        this.finish();
//    }
}