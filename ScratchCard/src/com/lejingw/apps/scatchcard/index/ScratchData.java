package com.lejingw.apps.scatchcard.index;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.lejingw.apps.scatchcard.R;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ScratchData {
    private String id;
    private int resPicId;
    private String name;
    private int topPrize;
    private int backRate;
    private int popularityIndex;

    public ScratchData(){}

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
            data.add(new ScratchData(ids[i], resPicIds[i], names[i], topPrizes[i], backRates[i], popularityIndexs[i]));
        }
        return data;
    }

    public static void main(String[] args) {
    }

    /**
     * 参数fileName：为xml文档路径
     */
    public static List<ScratchData> getRiversFromXml(Context context, String fileName) {
        List<ScratchData> rivers = new ArrayList<ScratchData>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        InputStream inputStream = null;
        try {
            //找到xml，并加载文档
            inputStream = context.getResources().getAssets().open(fileName);
            Document document = factory.newDocumentBuilder().parse(inputStream);

            Element root = document.getDocumentElement();
            NodeList scratchDataNodeList = root.getElementsByTagName("ScratchData");
            //遍历根节点所有子节点
            for (int i = 0; i < scratchDataNodeList.getLength(); i++) {
                Element scratchDataElement = (Element) (scratchDataNodeList.item(i));
                String id = scratchDataElement.getAttribute("id");
                String name = scratchDataElement.getAttribute("name");
                String resPicName = scratchDataElement.getAttribute("resPicName");
                String topPrize = scratchDataElement.getAttribute("topPrize");
                String backRate = scratchDataElement.getAttribute("backRate");
                String popularityIndex = scratchDataElement.getAttribute("popularityIndex");

                Log.d("msg", scratchDataElement.getAttribute("name"));


                int resPicId = context.getResources().getIdentifier(resPicName, "drawable", null);
                ScratchData scratchData = new ScratchData(id, resPicId, name, Integer.parseInt(topPrize), Integer.parseInt(backRate), Integer.parseInt(popularityIndex));

                NodeList areaNodeList = scratchDataElement.getElementsByTagName("areas");
                for (int j = 0; j < areaNodeList.getLength(); j++) {
                    Element areaElement = (Element) areaNodeList.item(j);
                    Log.d("msg", areaElement.getTextContent());
                }
//                scratchData.setName(riverElement.getAttribute(NAME));
//                river.setIntroduction(introduction.getFirstChild().getNodeValue());

                rivers.add(scratchData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rivers;
    }
}