package com.lejingw.apps.scatchcard;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MainActivity extends TabActivity implements OnCheckedChangeListener {
    private final String TAB_NAME_0 = "tab_0";
    private final String TAB_NAME_1 = "tab_1";
    private final String TAB_NAME_2 = "tab_2";
    private final String TAB_NAME_3 = "tab_3";
    private TabHost tabHost;

//    private Intent mAIntent;
//    private Intent mBIntent;
//    private Intent mCIntent;
//    private Intent mDIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

//        this.mAIntent = new Intent(this, IndexActivity.class);
//        this.mBIntent = new Intent(this, MySratchCardActivity.class);
//        this.mCIntent = new Intent(this, MySratchCardActivity.class);
//        this.mDIntent = new Intent(this, MySratchCardActivity.class);
//
//        ((RadioButton) findViewById(R.id.radio_button0)).setOnCheckedChangeListener(this);
//        ((RadioButton) findViewById(R.id.radio_button1)).setOnCheckedChangeListener(this);
//        ((RadioButton) findViewById(R.id.radio_button2)).setOnCheckedChangeListener(this);
//        ((RadioButton) findViewById(R.id.radio_button3)).setOnCheckedChangeListener(this);
//
//        setupIntent();

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



        //------------------------------
        tabHost = getTabHost();
        final TabWidget tabWidget = tabHost.getTabWidget();
        tabWidget.setStripEnabled(false);// 圆角边线不启用
        //添加n个tab选项卡，定义他们的tab名，指示名，目标屏对应的类
        tabHost.addTab(tabHost.newTabSpec("TAG1").setIndicator("0").setContent(new Intent(this, IndexActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("TAG2").setIndicator("1").setContent(new Intent(this, MySratchCardActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("TAG3").setIndicator("2").setContent(new Intent(this, MySratchCardActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("TAG4").setIndicator("3").setContent(new Intent(this, MySratchCardActivity.class)));
        // 视觉上,用单选按钮替代TabWidget
        RadioGroup main_radio = (RadioGroup) findViewById(R.id.main_radio);
        final RadioButton tab_icon_weixin = (RadioButton) findViewById(R.id.radio_button0);
        final RadioButton tab_icon_address = (RadioButton) findViewById(R.id.radio_button1);
        final RadioButton tab_icon_find = (RadioButton) findViewById(R.id.radio_button2);
        final RadioButton tab_icon_myself = (RadioButton) findViewById(R.id.radio_button3);

        main_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int id) {
                if (id == tab_icon_weixin.getId()) {
                    tabHost.setCurrentTab(0);
                } else if (id == tab_icon_address.getId()) {
                    tabHost.setCurrentTab(1);
                } else if (id == tab_icon_find.getId()) {
                    tabHost.setCurrentTab(2);
                } else if (id == tab_icon_myself.getId()) {
                    tabHost.setCurrentTab(3);
                }
            }
        });

        // 设置当前显示哪一个标签
        tabHost.setCurrentTab(0);
        // 遍历tabWidget每个标签，设置背景图片 无
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
            View vv = tabWidget.getChildAt(i);
            vv.getLayoutParams().height = 45;
            // vv.getLayoutParams().width = 65;
            vv.setBackgroundDrawable(null);
        }
//      findViewById(R.id.tab_icon_brand).setOnClickListener(this);
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

//    private void setupIntent() {
//        this.tabHost = getTabHost();
//
//        this.tabHost.addTab(buildTabSpec(TAB_NAME_0, "", R.drawable.tab_sy, this.mAIntent));
//        this.tabHost.addTab(buildTabSpec(TAB_NAME_1, "", R.drawable.tab_wd, this.mBIntent));
//        this.tabHost.addTab(buildTabSpec(TAB_NAME_2, "", R.drawable.tab_cz, this.mCIntent));
//        this.tabHost.addTab(buildTabSpec(TAB_NAME_3, "", R.drawable.tab_tx, this.mDIntent));
//    }
//
//    private TabHost.TabSpec buildTabSpec(String tag, String resLabel, int resIcon, final Intent content) {
//        return this.tabHost
//                .newTabSpec(tag)
//                .setIndicator(resLabel, getResources().getDrawable(resIcon))
//                .setContent(content);
//    }
}