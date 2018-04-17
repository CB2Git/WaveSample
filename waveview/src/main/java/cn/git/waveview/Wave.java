package cn.git.waveview;

import android.graphics.Paint;

public class Wave {

    //屏幕中波峰+波谷的个数
    private int mWaveCount = 2;

    //半个波的宽度，以波峰最高点以及波谷最低点为控制点
    private int mHalfWaveWidth;

    //波峰的高度
    private int mWaveHeight = 60;

    //波浪起始位置
    private int mOriginX;

    //波浪起始位置
    private int mOriginY;

    //绘制波浪的画笔
    private Paint mWavePaint = new Paint();


    public void init(int viewWidth) {
        mHalfWaveWidth = viewWidth / mWaveCount / 2;
    }

    public Paint getWavePaint() {
        return mWavePaint;
    }

    public void setWavePaint(Paint wavePaint) {
        mWavePaint = wavePaint;
    }

    public int getWaveCount() {
        return mWaveCount;
    }

    public void setWaveCount(int waveCount) {
        mWaveCount = waveCount;
    }

    public int getHalfWaveWidth() {
        return mHalfWaveWidth;
    }

    public void setHalfWaveWidth(int halfWaveWidth) {
        mHalfWaveWidth = halfWaveWidth;
    }

    public int getWaveHeight() {
        return mWaveHeight;
    }

    public void setWaveHeight(int waveHeight) {
        mWaveHeight = waveHeight;
    }

    public int getOriginX() {
        return mOriginX;
    }

    public void setOriginX(int originX) {
        mOriginX = originX;
    }

    public int getOriginY() {
        return mOriginY;
    }

    public void setOriginY(int originY) {
        mOriginY = originY;
    }
}
