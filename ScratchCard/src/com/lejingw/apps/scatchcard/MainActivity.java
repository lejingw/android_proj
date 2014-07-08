package com.lejingw.apps.scatchcard;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity implements OnCheckedChangeListener {
    private final String TAB_NAME_0 = "tab_0";
    private final String TAB_NAME_1 = "tab_1";
    private final String TAB_NAME_2 = "tab_2";
    private final String TAB_NAME_3 = "tab_3";
    private TabHost tabHost;

    private Intent mAIntent;
    private Intent mBIntent;
    private Intent mCIntent;
    private Intent mDIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        this.mAIntent = new Intent(this, IndexActivity.class);
        this.mBIntent = new Intent(this, MySratchCardActivity.class);
        this.mCIntent = new Intent(this, MySratchCardActivity.class);
        this.mDIntent = new Intent(this, MySratchCardActivity.class);

        ((RadioButton) findViewById(R.id.radio_button0)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button1)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button2)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button3)).setOnCheckedChangeListener(this);

        setupIntent();

//        int count = tabHost.getChildCount();
//        //TabHost中有一个getTabWidget()的方法
//        for (int i = 0; i < count; i++) {
//            View view = tabHost.getTabWidget().getChildTabViewAt(i);
//            view.setEnabled(false);
//            view.getLayoutParams().height = 80;
//            //tabWidget.getChildAt(i);
//            final TextView tv = (TextView) view.findViewById(android.R.id.title);
//            tv.setTextSize(28);
//            tv.setTextColor(this.getResources().getColorStateList(android.R.color.white));
//        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.radio_button0:
                    this.tabHost.setCurrentTabByTag(TAB_NAME_0);
                    break;
                case R.id.radio_button1:
                    this.tabHost.setCurrentTabByTag(TAB_NAME_1);
                    break;
                case R.id.radio_button2:
                    this.tabHost.setCurrentTabByTag(TAB_NAME_2);
                    break;
                case R.id.radio_button3:
                    this.tabHost.setCurrentTabByTag(TAB_NAME_3);
                    break;
            }
        }
    }

    private void setupIntent() {
        this.tabHost = getTabHost();

        this.tabHost.addTab(buildTabSpec(TAB_NAME_0, "", R.drawable.tab_sy, this.mAIntent));
        this.tabHost.addTab(buildTabSpec(TAB_NAME_1, "", R.drawable.tab_wd, this.mBIntent));
        this.tabHost.addTab(buildTabSpec(TAB_NAME_2, "", R.drawable.tab_cz, this.mCIntent));
        this.tabHost.addTab(buildTabSpec(TAB_NAME_3, "", R.drawable.tab_tx, this.mDIntent));
    }

    private TabHost.TabSpec buildTabSpec(String tag, String resLabel, int resIcon, final Intent content) {
        return this.tabHost
                .newTabSpec(tag)
                .setIndicator(resLabel, getResources().getDrawable(resIcon))
                .setContent(content);
    }
}