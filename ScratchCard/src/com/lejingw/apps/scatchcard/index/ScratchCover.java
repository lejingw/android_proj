package com.lejingw.apps.scatchcard.index;

/**
 * Created by lejingw on 14-7-8.
 */
public class ScratchCover {
    private String picName;

    private float canvasStartXRate = 140;
    private float canvasStartYRate = 178;
    private int canvasStartX = 140;
    private int canvasStartY = 178;

    public ScratchCover(String picName, float canvasStartXRate, float canvasStartYRate, int canvasStartX, int canvasStartY) {
        this.picName = picName;
        this.canvasStartXRate = canvasStartXRate;
        this.canvasStartYRate = canvasStartYRate;
        this.canvasStartX = canvasStartX;
        this.canvasStartY = canvasStartY;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public int getCanvasStartX() {
        return canvasStartX;
    }

    public void setCanvasStartX(int canvasStartX) {
        this.canvasStartX = canvasStartX;
    }

    public int getCanvasStartY() {
        return canvasStartY;
    }

    public void setCanvasStartY(int canvasStartY) {
        this.canvasStartY = canvasStartY;
    }

    public float getCanvasStartXRate() {
        return canvasStartXRate;
    }

    public void setCanvasStartXRate(float canvasStartXRate) {
        this.canvasStartXRate = canvasStartXRate;
    }

    public float getCanvasStartYRate() {
        return canvasStartYRate;
    }

    public void setCanvasStartYRate(float canvasStartYRate) {
        this.canvasStartYRate = canvasStartYRate;
    }
}
