package com.lejingw.apps.scatchcard;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

public class MySratchCardActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TextView tv = new TextView(this);
		tv.setText("我的刮刮乐");
		tv.setGravity(Gravity.CENTER);
		setContentView(tv);
	}

}