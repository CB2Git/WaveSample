package cn.git.waveview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

public class MultiWaveView extends View {

    private Path mWavePath = new Path();

    private List<Wave> mWaves = new ArrayList<>();

    private ValueAnimator mValueAnimator;

    private LinearInterpolator mLinearInterpolator = new LinearInterpolator();

    private int mOffset;

    public MultiWaveView(Context context) {
        this(context, null);
    }

    public MultiWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {

        //这里有透明度是让波浪有层次感的关键！！！
        int startColor = ColorUtils.setAlphaComponent(0xff1372CF, (int) (0.3 * 255));
        int closeColor = ColorUtils.setAlphaComponent(0xFF40B5FF, (int) (0.5 * 255));
        LinearGradient linearGradient = new LinearGradient(0, 0, getMeasuredWidth(), getMeasuredWidth(), startColor, closeColor, Shader.TileMode.CLAMP);

        Wave wave1 = new Wave();
        wave1.setWaveCount(2);
        //这里让第一个波浪与第二个波浪产生间隔，也是产生波浪层次感的关键
        wave1.setOriginX(200);
        Paint wave1WavePaint = wave1.getWavePaint();
        wave1WavePaint.setStyle(Paint.Style.FILL);
        wave1WavePaint.setAntiAlias(true);
        wave1WavePaint.setDither(true);
        wave1WavePaint.setShader(linearGradient);

        Wave wave2 = new Wave();
        wave2.setWaveCount(2);
        wave2.setWaveHeight(50);

        Paint wave2WavePaint = wave2.getWavePaint();
        wave2WavePaint.setStyle(Paint.Style.FILL);
        wave2WavePaint.setAntiAlias(true);
        wave2WavePaint.setDither(true);
        wave2WavePaint.setShader(linearGradient);

        mWaves.add(wave1);
        mWaves.add(wave2);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        for (Wave item : mWaves) {
            item.init(getMeasuredWidth());
        }
        mValueAnimator = ValueAnimator.ofInt(0, mWaves.get(0).getHalfWaveWidth() * 4);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(mLinearInterpolator);
        //这里使用的固定速度，如果想更加的拟真，可以让速度为变化的
        mValueAnimator.setDuration(5000);
        mValueAnimator.start();
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Wave item : mWaves) {
            drawWave(canvas, item);
        }
    }

    private void drawWave(Canvas canvas, Wave item) {
        mWavePath.reset();
        mWavePath.moveTo(-item.getHalfWaveWidth() * 6 + mOffset + item.getOriginX(), item.getWaveHeight() + item.getOriginY());
        for (int i = 0; i < item.getWaveCount() + 6; i++) {
            //以波峰最高点以及波谷最低点为控制点
            if (i % 2 == 0) {
                mWavePath.rQuadTo(item.getHalfWaveWidth(), -item.getWaveHeight(), item.getHalfWaveWidth() * 2, 0);
            } else {
                mWavePath.rQuadTo(item.getHalfWaveWidth(), item.getWaveHeight(), item.getHalfWaveWidth() * 2, 0);
            }
        }
        mWavePath.lineTo(getMeasuredWidth(), getMeasuredHeight());
        mWavePath.lineTo(-item.getOriginY(), getMeasuredHeight());
        mWavePath.close();
        canvas.drawPath(mWavePath, item.getWavePaint());
    }
}
