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

public class MyActivity extends Activity {
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
                showPopWindow(MyActivity.this, button);
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
                Toast.makeText(MyActivity.this, "You click OK", Toast.LENGTH_SHORT).show();
            }
        });

        Button cancleButton = (Button) vPopWindow.findViewById(R.id.button2);
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss(); //Close the Pop Window
            }
        });
        popWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

    }
}
