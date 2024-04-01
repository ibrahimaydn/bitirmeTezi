package com.ibrahimaydin.firebaseeklesil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class MonoksitCircleView extends View {

    private int monoksit = 0;

    public MonoksitCircleView(Context context) {
        super(context);
    }

    public MonoksitCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MonoksitCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMonoksit(int monoksit) {
        this.monoksit = Math.min(Math.max(monoksit, 0), 1000);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        Paint paint2 = new Paint();
        paint.setAntiAlias(true);
        paint2.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint2.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        paint2.setStrokeWidth(20);
        paint.setColor(Color.BLUE);
        paint2.setColor(Color.GRAY);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY) - 30; // Çemberin yarıçapı
        RectF rectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        canvas.drawArc(rectF, 0, 360, false, paint2);

        float startAngle = 270;
        float sweepAngle = (float) (3.6 * ((float) (monoksit ) / 10)); // 10ppm ile 1000 birimlik aralığı 360 dereceye sığdırma

        canvas.drawArc(rectF, startAngle, sweepAngle, false, paint);

        String text = String.valueOf(monoksit + " ppm");
        paint.setTextSize(50);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        Rect textBounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBounds);
        float textWidth = paint.measureText(text);
        float textHeight = textBounds.height();
        canvas.drawText(text, centerX - textWidth / 2, centerY + textHeight / 2, paint);
    }
}

