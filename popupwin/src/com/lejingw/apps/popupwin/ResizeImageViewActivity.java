package com.lejingw.apps.popupwin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
//import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by lejingw on 14-7-4.
 */
public class ResizeImageViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.resize_imageview);

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Button btn = (Button) findViewById(R.id.resizeBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("==========================");
                RelativeLayout.LayoutParams mParams = new RelativeLayout.LayoutParams(400, 400);
                imageView .setLayoutParams(mParams);

            }
        });
    }
}
