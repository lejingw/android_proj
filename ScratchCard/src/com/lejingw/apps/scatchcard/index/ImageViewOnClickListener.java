package com.lejingw.apps.scatchcard.index;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.lejingw.apps.scatchcard.IndexActivity;
import com.lejingw.apps.scatchcard.R;
import com.lejingw.apps.scatchcard.util.DisplayUtil;
import com.lejingw.apps.scatchcard.util.ImageUtil;

import java.util.List;

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
            listView.setAdapter(new ListViewAdapter2());
            //列表点击事件
            listView.setOnItemClickListener(new ScratchCardClickListener(view));

            popWindow.setAnimationStyle(R.style.popWinInOutAnimation);
            popWindow.setFocusable(true);
            popWindow.update();
            popWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        }

        private final class ListViewAdapter2 extends BaseAdapter {
            private int[] startScoreArr = new int[]{R.id.star_score1, R.id.star_score2, R.id.star_score3, R.id.star_score4, R.id.star_score5};

            private Context context =indexActivity;
            private List<ScratchData> scratchDataList = ScratchData.createTempData();

            @Override
            public int getCount() {
                return scratchDataList.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final View view = LayoutInflater.from(context).inflate(R.layout.index_popwin_item, null, false);
//              LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//              final View view = inflater.inflate(R.layout.index_popwin_item, null, false);

                ScratchData scratch = scratchDataList.get(position);
                //业务图片
                final ImageView scratchPicView = (ImageView) view.findViewById(R.id.scratchPicView);

//                scratchPicView.setImageResource(scratch.getResPicId());
                scratchPicView.setImageBitmap(ImageUtil.readBitMap(context, scratch.getResPicId()));

                final TextView nameTextView = (TextView) view.findViewById(R.id.name);
                nameTextView.setText(scratch.getName());
                final TextView topPrizeTextView = (TextView) view.findViewById(R.id.topPrize);
                topPrizeTextView.setText("最高奖:" + scratch.getTopPrize() + "万");
                final TextView backRateTextView = (TextView) view.findViewById(R.id.backRate);
                backRateTextView.setText("返奖率:" + scratch.getBackRate() + "%");
                //人气指数
                int i = 0;
                for (int j = scratch.getPopularityIndex() / 2; i < j; i++) {
                    ImageView scoreImageView = (ImageView) view.findViewById(startScoreArr[i]);
//                    scoreImageView.setImageResource(R.drawable.bg_star_full);
                    scoreImageView.setImageBitmap(ImageUtil.readBitMap(context, R.drawable.bg_star_full));
                }
                if (scratch.getPopularityIndex() % 2 == 1) {
                    ImageView scoreImageView = (ImageView) view.findViewById(startScoreArr[i]);
//                    scoreImageView.setImageResource(R.drawable.bg_star_half);
                    scoreImageView.setImageBitmap(ImageUtil.readBitMap(context, R.drawable.bg_star_half));
                }
                final ImageView showDetailView = (ImageView) view.findViewById(R.id.showDetailView);
                final View popularityIndexLayout = view.findViewById(R.id.popularityIndexLayout);

                view.findViewById(R.id.showDetailLayout).setOnClickListener(new View.OnClickListener() {
                    private boolean expendFlag = false;
                    private TextView name = nameTextView;
                    private ImageView scratchPic = scratchPicView;
                    private TextView backRate = backRateTextView;
                    private View popularityIndex = popularityIndexLayout;

                    @Override
                    public void onClick(View v) {
                        expendFlag = !expendFlag;
                        //更换展开，合起图片
//                        showDetailView.setImageResource(expendFlag ? R.drawable.collipse11 : R.drawable.expand);
                        showDetailView.setImageBitmap(ImageUtil.readBitMap(context, expendFlag ? R.drawable.collipse11 : R.drawable.expand));

                        Context ctx = showDetailView.getContext();
                        if (expendFlag) {
                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)DisplayUtil.dip2px(ctx, 150), (int) DisplayUtil.dip2px(ctx, 150 * 80 / 100));
                            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                            scratchPic.setLayoutParams(layoutParams);
                        } else {
                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)DisplayUtil.dip2px(ctx, 100), (int)DisplayUtil.dip2px(ctx, 80));
                            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                            scratchPic.setLayoutParams(layoutParams);
                        }

                        backRate.setVisibility(expendFlag ? View.VISIBLE : View.INVISIBLE);
                        popularityIndex.setVisibility(expendFlag ? View.VISIBLE : View.INVISIBLE);
                    }
                });

                //条目点击事件
                //view.setOnClickListener(new BusinessItemClickListener());

                return view;
            }
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