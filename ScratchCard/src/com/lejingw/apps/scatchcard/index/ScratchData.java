package com.lejingw.apps.scatchcard.index;

import com.lejingw.apps.scatchcard.R;

import java.util.ArrayList;
import java.util.List;

public class ScratchData {
    private String id;
    private int resPicId;
    private String name;
    private int topPrize;
    private int backRate;
    private int popularityIndex;

    public ScratchData(String id, int resPicId, String name, int topPrize, int backRate, int popularityIndex) {
        this.id = id;
        this.resPicId = resPicId;
        this.name = name;
        this.topPrize = topPrize;
        this.backRate = backRate;
        this.popularityIndex = popularityIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getResPicId() {
        return resPicId;
    }

    public void setResPicId(int resPicId) {
        this.resPicId = resPicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTopPrize() {
        return topPrize;
    }

    public void setTopPrize(int topPrize) {
        this.topPrize = topPrize;
    }

    public int getBackRate() {
        return backRate;
    }

    public void setBackRate(int backRate) {
        this.backRate = backRate;
    }

    public int getPopularityIndex() {
        return popularityIndex;
    }

    public void setPopularityIndex(int popularityIndex) {
        this.popularityIndex = popularityIndex;
    }

    public static List<ScratchData> createTempData() {
        String[] ids = new String[]{"1", "2", "3", "4", "5", "6", "7"};
        int[] resPicIds = new int[]{R.drawable.scratch1, R.drawable.scratch2, R.drawable.scratch3, R.drawable.scratch4
                , R.drawable.scratch5, R.drawable.scratch6, R.drawable.scratch7};
        String[] names = new String[]{"宝石奇缘", "马到成功", "龙腾盛世", "探险家", "巅峰对决", "花好月圆", "夺宝嘉年华"};
        int[] topPrizes = new int[]{10, 20, 30, 40, 50, 60, 70};
        int[] backRates = new int[]{60, 70, 75, 80, 85, 90, 95};
        int[] popularityIndexs = new int[]{10, 9, 8, 5, 2, 1, 0};

        List<ScratchData> data = new ArrayList<ScratchData>();

        for (int i = 0; i < ids.length; i++) {
            //BusinessData(String id, int resPicId, String name, int topPrize, int backRate, int popularityIndex)
            data.add(new ScratchData(ids[i], resPicIds[i], names[i], topPrizes[i], backRates[i], popularityIndexs[i]));
        }
        return data;
    }
}