package com.lejingw.apps.scatchcard.index;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.*;
import android.widget.*;
import com.lejingw.apps.scatchcard.IndexActivity;
import com.lejingw.apps.scatchcard.R;

public class ImageViewOnClickListener implements View.OnClickListener {
        private IndexActivity indexActivity;

        public  ImageViewOnClickListener(IndexActivity indexActivity){
            this.indexActivity = indexActivity;
        }

        private PopupWindow popWindow;
        @Override
        public void onClick(View view) {
            if(indexActivity.getSelectItemIndex()<0){
                return ;
            }

            LayoutInflater inflater = (LayoutInflater) indexActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View popWinView = inflater.inflate(R.layout.index_popwin, null, false);
            /**
             * 为了响应返回实体键按钮：
             * 1、focusable设置为true
             * 2、popWindow.setBackgroundDrawable(new BitmapDrawable());
             * 3、View.setOnKeyListener
             */
            popWindow = new PopupWindow(popWinView, AbsListView.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            popWindow.setBackgroundDrawable(new BitmapDrawable());
            popWinView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                    if (arg1 == KeyEvent.KEYCODE_BACK) {
                        if (popWindow != null) {
                            popWindow.dismiss();
                            return true;
                        }
                    }
                    return false;
                }
            });

            popWinView.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popWindow.dismiss();//Close the Pop Window
                }
            });

            ListView listView = (ListView) popWinView.findViewById(R.id.listView);
            //自定义Adapter适配器将数据绑定到item显示控件上
            //实现列表的显示
            listView.setAdapter(new ListViewAdapter(indexActivity));
            //列表点击事件
            listView.setOnItemClickListener(new ScratchCardClickListener(view));

            popWindow.setAnimationStyle(R.style.popWinInOutAnimation);
            popWindow.setFocusable(true);
            popWindow.update();
            popWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        }

        //获取点击事件
        private final class ScratchCardClickListener implements AdapterView.OnItemClickListener {
            private View parentView;

            public ScratchCardClickListener(View view) {
                this.parentView = view;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//              LayoutInflater inflater = (LayoutInflater) IndexActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//              final View popWinView = inflater.inflate(R.layout.index_scratchcard, null, false);

                final View popWinView = LayoutInflater.from(indexActivity).inflate(R.layout.index_scratchcard, null, true);

                final PopupWindow popWindow = new PopupWindow(popWinView, AbsListView.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);
                popWindow.setBackgroundDrawable(new BitmapDrawable());
                popWinView.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                        if (arg1 == KeyEvent.KEYCODE_BACK) {
                            if (popWindow != null) {
                                popWindow.dismiss();
                                return true;
                            }
                        }
                        return false;
                    }
                });
                popWinView.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popWindow.dismiss();
                    }
                });

                popWindow.setAnimationStyle(R.style.scratchcardInOutAnimation);
                popWindow.setFocusable(true);
                popWindow.update();
                popWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);

                LinearLayout scratchcardLayout = (LinearLayout) popWinView.findViewById(R.id.scratchcardLayout);
                scratchcardLayout.addView(new ScratchCardView(scratchcardLayout.getContext()));
            }
        }
    }