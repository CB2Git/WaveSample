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

public class WaveView extends View {

    private Paint mWavePaint = new Paint();

    private Path mWavePath = new Path();

    //屏幕中波峰+波谷的个数
    private int mWaveCount = 2;

    //半个波的宽度，以波峰最高点以及波谷最低点为控制点
    private int mHalfWaveWidth;

    //波峰的高度
    private int mWaveHeight = 60;

    //波浪每个单位时间移动的距离
    private int mOffset = 0;

    private int mOriginX;

    private int mOriginY;

    private ValueAnimator mValueAnimator;

    public WaveView(Context context) {
        super(context);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void initView() {

        //半个波峰的宽度
        mHalfWaveWidth = getMeasuredWidth() / mWaveCount / 2;
        mOriginX = 120;
        mOriginY = 300;
        mWavePaint.setColor(0xff93C5EF);
        mWavePaint.setStyle(Paint.Style.FILL);
        mWavePaint.setAntiAlias(true);
        mWavePaint.setDither(true);
        mWavePaint.setShader(new LinearGradient(0, 0, getMeasuredWidth(), getMeasuredWidth(), 0xff1372CF, 0xFF40B5FF, Shader.TileMode.CLAMP));
        mValueAnimator = ValueAnimator.ofInt(0, mHalfWaveWidth * 4);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(sizeWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(sizeHeight, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWavePath.reset();
        mWavePath.moveTo(-mHalfWaveWidth * 6 + mOffset + mOriginX, mWaveHeight + mOriginY);
        for (int i = 0; i < mWaveCount + 6; i++) {
            //以波峰最高点以及波谷最低点为控制点
            if (i % 2 == 0) {
                mWavePath.rQuadTo(mHalfWaveWidth, -mWaveHeight, mHalfWaveWidth * 2, 0);
            } else {
                mWavePath.rQuadTo(mHalfWaveWidth, mWaveHeight, mHalfWaveWidth * 2, 0);
            }
        }
        mWavePath.lineTo(getMeasuredWidth(), getMeasuredHeight());
        mWavePath.lineTo(-mOriginX, getMeasuredHeight());
        mWavePath.close();
        canvas.drawPath(mWavePath, mWavePaint);
    }
}
