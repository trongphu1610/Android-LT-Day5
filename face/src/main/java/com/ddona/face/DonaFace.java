package com.ddona.face;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class DonaFace extends View {
    private final int DEFAULT_SIZE = 100;
    private final int DEFAULT_EYES_COLOR = Color.BLACK;
    private final int DEFAULT_MOUTH_COLOR = Color.BLACK;
    private int radius = DEFAULT_SIZE;
    private PointF center;
    private int eyeColor = DEFAULT_EYES_COLOR;
    private int mouthColor = DEFAULT_MOUTH_COLOR;
    private Paint eyesPaint;
    private Paint mouthPaint;
    private Paint facePaint;
    private RectF mouthRect;

    public DonaFace(Context context) {
        super(context);
    }

    public DonaFace(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DonaFace);
        radius = typedArray.getDimensionPixelSize(R.styleable.DonaFace_ddona_radius, DEFAULT_SIZE);
        eyeColor = typedArray.getColor(R.styleable.DonaFace_ddona_eyes_color, DEFAULT_EYES_COLOR);
        mouthColor = typedArray.getColor(R.styleable.DonaFace_ddona_mouth_color, DEFAULT_MOUTH_COLOR);
        typedArray.recycle();
        init(context);
    }

    private void init(Context context) {
        center = new PointF();
        eyesPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        eyesPaint.setAntiAlias(true);
        eyesPaint.setColor(mouthColor);
        eyesPaint.setStyle(Paint.Style.STROKE);
        eyesPaint.setStrokeWidth(16 * getResources().getDisplayMetrics().density);

        mouthPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mouthPaint.setColor(mouthColor);
        mouthPaint.setStyle(Paint.Style.STROKE);
        mouthPaint.setStrokeCap(Paint.Cap.ROUND);
        mouthPaint.setStrokeWidth(16 * getResources().getDisplayMetrics().density);

        facePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        facePaint.setStyle(Paint.Style.FILL);
        facePaint.setColor(eyeColor);
        mouthRect = new RectF();
    }

    public DonaFace(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DonaFace(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int width, height;
        if (wMode == MeasureSpec.EXACTLY) {
            width = wSize;
        } else if (wMode == MeasureSpec.AT_MOST) {
            width = Math.min(DEFAULT_SIZE * 2, wSize);
        } else {
            width = DEFAULT_SIZE * 2;
        }
        if (hMode == MeasureSpec.EXACTLY) {
            height = hSize;
        } else if (hMode == MeasureSpec.AT_MOST) {
            height = Math.min(DEFAULT_SIZE * 2, hSize);
        } else {
            height = DEFAULT_SIZE * 2;
        }
        int size = Math.min(width, height);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        center.x = getWidth() / 2;
        center.y = getHeight() / 2;

        Log.d("doanpt", "w=" + getWidth() + " h=" + getHeight());
        radius = getWidth() / 2;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(center.x, center.y, radius, facePaint);
        float eyeRadius = radius / 5f;
        float delta = radius / 3f;
        canvas.drawCircle(center.x - delta, center.y - delta, eyeRadius, eyesPaint);
        canvas.drawCircle(center.x + delta, center.y - delta, eyeRadius, eyesPaint);

        float mouthDelta = radius/5f;
        mouthRect.set(mouthDelta, mouthDelta, radius * 2 - mouthDelta, radius * 2 - mouthDelta);
        canvas.drawArc(mouthRect, 45f, 90, false, mouthPaint);
    }
}
