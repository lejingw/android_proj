package com.lejingw.apps.scatchcard.index;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.lejingw.apps.scatchcard.R;
import org.w3c.dom.Text;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private int[] startScoreArr = new int[]{R.id.star_score1, R.id.star_score2, R.id.star_score3, R.id.star_score4, R.id.star_score5};

    private List<ScratchData> scratchDataList;
    private Context context;

    public ListViewAdapter(Context context, List<ScratchData> scratchDataList) {
        super();
        this.context = context;
        this.scratchDataList = scratchDataList;
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
        final View view = LayoutInflater.from(context).inflate(R.layout.index_popwin_item, null, false);
//      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//      final View view = inflater.inflate(R.layout.index_popwin_item, null, false);

        ScratchData scratch = scratchDataList.get(position);
        //业务图片
        final ImageView scratchPicView = (ImageView) view.findViewById(R.id.scratchPicView);
        scratchPicView.setImageResource(scratch.getResPicId());
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
            scoreImageView.setImageResource(R.drawable.bg_star_full);
        }
        if (scratch.getPopularityIndex() % 2 == 1) {
            ImageView scoreImageView = (ImageView) view.findViewById(startScoreArr[i]);
            scoreImageView.setImageResource(R.drawable.bg_star_half);
        }
        final ImageView showDetailView = (ImageView) view.findViewById(R.id.showDetailView);
        final View popularityIndexLayout = view.findViewById(R.id.popularityIndexLayout);

        showDetailView.setOnClickListener(new View.OnClickListener() {
            private boolean expendFlag = false;
            private TextView name = nameTextView;
            private ImageView scratchPic = scratchPicView;
            private TextView backRate = backRateTextView;
            private View popularityIndex = popularityIndexLayout;

            @Override
            public void onClick(View v) {
                expendFlag = !expendFlag;
                //更换展开，合起图片
                ((ImageView) v).setImageResource(expendFlag ? R.drawable.collipse11 : R.drawable.expand);

                if (expendFlag) {
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(200, 200*140/120);
                    scratchPic.setLayoutParams(layoutParams);
                } else {
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(140, 120);
//                  android:layout_alignParentLeft="true"
//                  android:layout_centerVertical="true"
//                  layoutParams.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
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