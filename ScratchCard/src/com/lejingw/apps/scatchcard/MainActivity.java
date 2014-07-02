package com.lejingw.apps.scatchcard;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TabHost;

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
        this.mCIntent = new Intent(this, PushMoneyActivity.class);
        this.mDIntent = new Intent(this, PullMoneyActivity.class);

        ((RadioButton) findViewById(R.id.radio_button0)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button1)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button2)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button3)).setOnCheckedChangeListener(this);

        setupIntent();
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

        this.tabHost.addTab(buildTabSpec(TAB_NAME_0, R.string.indexPageRadio, R.drawable.icon_0_n, this.mAIntent));
        this.tabHost.addTab(buildTabSpec(TAB_NAME_1, R.string.mySratchCardRadio, R.drawable.icon_1_n, this.mBIntent));
        this.tabHost.addTab(buildTabSpec(TAB_NAME_2, R.string.pushMoneyRadio, R.drawable.icon_2_n, this.mCIntent));
        this.tabHost.addTab(buildTabSpec(TAB_NAME_3, R.string.pullMoneyRadio, R.drawable.icon_3_n, this.mDIntent));
    }

    private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon, final Intent content) {
        return this.tabHost
                .newTabSpec(tag)
                .setIndicator(getString(resLabel), getResources().getDrawable(resIcon))
                .setContent(content);
    }
}