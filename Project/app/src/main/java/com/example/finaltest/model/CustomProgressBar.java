package com.example.finaltest.model;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.finaltest.R;

public class CustomProgressBar extends View {
    private Paint paint = new Paint();
    private int energyLevel = 0;

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Lấy kích thước của view
        int width = getWidth();
        int height = getHeight();

        // Đặt màu nền
        paint.setColor(ContextCompat.getColor(getContext(), R.color.backgroundColor));
        canvas.drawRect(0, 0, width, height, paint);

        // Đặt màu cho năng lượng
        paint.setColor(ContextCompat.getColor(getContext(), R.color.blue));
        int energyWidth = (int) (width * (energyLevel / 100.0));
        canvas.drawRect(0, 0, energyWidth, height, paint);
    }

    public void setEnergyLevel(int energyLevel) {
        this.energyLevel = energyLevel;
        invalidate(); // Gọi invalidate để vẽ lại view
    }
}