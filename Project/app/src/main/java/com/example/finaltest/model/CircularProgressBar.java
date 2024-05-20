package com.example.finaltest.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.finaltest.R;

public class CircularProgressBar extends View {

    private Paint backgroundPaint;
    private Paint progressPaint;
    private RectF rectF;
    private float percentage = 0;
    private int viewWidth;
    private int viewHeight;

    public CircularProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        backgroundPaint = new Paint();
        backgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.blue));
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(20);
        backgroundPaint.setAntiAlias(true);

        progressPaint = new Paint();
        progressPaint.setColor(ContextCompat.getColor(getContext(), R.color.backgroundColor));
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(20);
        progressPaint.setAntiAlias(true);

        rectF = new RectF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
        updateRectF();
    }

    private void updateRectF() {
        float radius = Math.min(viewWidth, viewHeight) / 2f - 20; // 20 is the stroke width
        float centerX = viewWidth / 2f;
        float centerY = viewHeight / 2f;
        rectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rectF, -90, 360, false, backgroundPaint);
        canvas.drawArc(rectF, -90, 360 * percentage / 100, false, progressPaint);
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
        invalidate(); // Redraw the view
    }
}