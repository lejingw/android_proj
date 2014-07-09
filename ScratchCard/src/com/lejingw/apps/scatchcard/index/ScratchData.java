package com.lejingw.apps.scatchcard.index;

import android.content.Context;
import android.util.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ScratchData {
    private String id;
    private int type;
    private String resPicName;
    private String bgPicName;
    private String name;
    private int topPrize;
    private int backRate;
    private int popularityIndex;
    private ScratchCover scratchCover;

    public ScratchData(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getResPicName() {
        return resPicName;
    }

    public void setResPicName(String resPicName) {
        this.resPicName = resPicName;
    }

    public String getBgPicName() {
        return bgPicName;
    }

    public void setBgPicName(String bgPicName) {
        this.bgPicName = bgPicName;
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
    public ScratchCover getScratchCover() {
        return scratchCover;
    }

    public void setScratchCover(ScratchCover scratchCover) {
        this.scratchCover = scratchCover;
    }


    public String getRawPicName() {
        if(null != bgPicName){
            return bgPicName.substring(0, bgPicName.lastIndexOf("."));
        }
        return null;
    }

    /**
     * 参数fileName：为xml文档路径
     */
    public static List<ScratchData> getScratchDataFromXml(Context context) {
        List<ScratchData> scratchDataList = new ArrayList<ScratchData>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        InputStream inputStream = null;
        try {
            //找到xml，并加载文档
            inputStream = context.getResources().getAssets().open("scratch_list.xml");
            Document document = factory.newDocumentBuilder().parse(inputStream);

            Element root = document.getDocumentElement();
            NodeList scratchDataNodeList = root.getElementsByTagName("ScratchData");
            //遍历根节点所有子节点
            for (int i = 0; i < scratchDataNodeList.getLength(); i++) {
                Element scratchDataElement = (Element) (scratchDataNodeList.item(i));
                String id = scratchDataElement.getAttribute("id");
                String type = scratchDataElement.getAttribute("type");
                String name = scratchDataElement.getAttribute("name");
                String resPicName = scratchDataElement.getAttribute("resPicName");
                String bgPicName = scratchDataElement.getAttribute("bgPicName");
                String topPrize = scratchDataElement.getAttribute("topPrize");
                String backRate = scratchDataElement.getAttribute("backRate");
                String popularityIndex = scratchDataElement.getAttribute("popularityIndex");

                Log.d("msg", scratchDataElement.getAttribute("name"));

                int resPicId = context.getResources().getIdentifier(resPicName, "drawable", "com.lejingw.apps.scatchcard");

                ScratchData scratchData = new ScratchData();
                //id, resPicId, name, Integer.parseInt(topPrize), Integer.parseInt(backRate), Integer.parseInt(popularityIndex));
                scratchData.setId(id);
                scratchData.setName(name);
                scratchData.setType(Integer.parseInt(type));
                scratchData.setResPicName("type" + type + "/" + resPicName);//assets目录下
                scratchData.setBgPicName("type" + type + "_" + bgPicName);//res/raw目录下
                scratchData.setTopPrize(Integer.parseInt(topPrize));
                scratchData.setBackRate(Integer.parseInt(backRate));
                scratchData.setPopularityIndex(Integer.parseInt(popularityIndex));

                NodeList scratchCoverNodeList = scratchDataElement.getElementsByTagName("ScratchCover");
                Element scratchCoverElement = (Element) scratchCoverNodeList.item(0);
                String picName = "type" + type + "/" + scratchCoverElement.getAttribute("picName");
                float canvasStartXRate = Float.parseFloat(scratchCoverElement.getAttribute("canvasStartXRate"));
                float canvasStartYRate = Float.parseFloat(scratchCoverElement.getAttribute("canvasStartYRate"));
                int canvasStartX = Integer.parseInt(scratchCoverElement.getAttribute("canvasStartX"));
                int canvasStartY = Integer.parseInt(scratchCoverElement.getAttribute("canvasStartY"));

                ScratchCover scratchCover1 = new ScratchCover(picName, canvasStartXRate, canvasStartYRate, canvasStartX, canvasStartY);
                scratchData.setScratchCover(scratchCover1);

                scratchDataList.add(scratchData);
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
        return scratchDataList;
    }
}