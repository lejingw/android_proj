package com.lejingw.apps.popupwin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

public class PopWinActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Button button = (Button) findViewById(R.id.showWinBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopWindow(PopWinActivity.this, button);
            }
        });
    }


    private void showPopWindow(Context context, View parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View vPopWindow = inflater.inflate(R.layout.mypop_window, null, false);
        //宽300 高300
        final PopupWindow popWindow = new PopupWindow(vPopWindow, 300, 300, true);
        Button okButton = (Button) vPopWindow.findViewById(R.id.button1);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PopWinActivity.this, "You click OK", Toast.LENGTH_SHORT).show();
            }
        });

        Button cancleButton = (Button) vPopWindow.findViewById(R.id.button2);
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss(); //Close the Pop Window
            }
        });



        //PopupWindow window = new PopupWindow(v, 500,260);

        //设置整个popupwindow的样式。
        //window.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corners_pop));
        //使窗口里面的空间显示其相应的效果，比较点击button时背景颜色改变。
        //如果为false点击相关的空间表面上没有反应，但事件是可以监听到的。
        //listview的话就没有了作用。
        popWindow.setAnimationStyle(R.style.AnimationPreview);
        popWindow.setFocusable(true);
        popWindow.update();
//        popWindow.showAtLocation(parent, Gravity.CENTER_VERTICAL, 0, 0);
        popWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }
}
