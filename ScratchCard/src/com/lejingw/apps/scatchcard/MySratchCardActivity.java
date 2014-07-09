package com.lejingw.apps.scatchcard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

public class MySratchCardActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TextView tv = new TextView(this);
        tv.setSingleLine(false);
		tv.setText("开发中...待完善...");
		tv.setGravity(Gravity.CENTER);
		setContentView(tv);
	}

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }
}