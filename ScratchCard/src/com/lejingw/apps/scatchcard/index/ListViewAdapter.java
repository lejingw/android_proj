package com.lejingw.apps.scatchcard.index;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lejingw.apps.scatchcard.R;
import com.lejingw.apps.scatchcard.util.DisplayUtil;
import com.lejingw.apps.scatchcard.util.ImageUtil;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
            private int[] startScoreArr = new int[]{R.id.star_score1, R.id.star_score2, R.id.star_score3, R.id.star_score4, R.id.star_score5};

            private Context context;
            private  List<ScratchData> aaa;
            private List<ScratchData> scratchDataList = ScratchData.createTempData();

            public ListViewAdapter(Context context){
                this.context = context;
            }

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
                if(null == aaa){
                    aaa = ScratchData.getRiversFromXml(context, "scratch_list.xml");
                }
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
                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) DisplayUtil.dip2px(ctx, 150), (int) DisplayUtil.dip2px(ctx, 150 * 80 / 100));
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