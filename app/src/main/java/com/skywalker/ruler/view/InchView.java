package com.skywalker.ruler.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class InchView extends View {
    private final static int LINE_WIDTH_1 = 20;
    private final static int LINE_WIDTH_2 = 30;
    private final static int LINE_WIDTH_4 = 40;
    private final static int LINE_WIDTH_8 = 50;
    private final static int LINE_WIDTH_16 = 60;
    private final static int TEXT_WIDTH = 80;
    private final static int OFFSET = 10;

    private final Paint linePaint;
    private final Paint textPaint;
    private final float pixelsPerOneSixteenInch;
    private float height;

    public InchView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        pixelsPerOneSixteenInch = getResources().getDisplayMetrics().ydpi / 16.0f;

        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(2);
        linePaint.setColor(Color.BLUE);

        textPaint = new TextPaint();
        textPaint.setColor(Color.RED);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(20);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        height = h;
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        int i = 0;
        float y = height;
        int inches = 0;
        while (y >= 0) {
            y = height - i * pixelsPerOneSixteenInch - OFFSET;

            final int x;
            if (i % 16 == 0) {
                canvas.rotate(-90);
                canvas.drawText(Integer.toString(inches), -y, TEXT_WIDTH, textPaint);
                canvas.rotate(90);
                x = LINE_WIDTH_16;
                inches++;
            } else if (i % 8 == 0) {
                x = LINE_WIDTH_8;
            } else if (i % 4 == 0) {
                x = LINE_WIDTH_4;
            } else if (i % 2 == 0) {
                x = LINE_WIDTH_2;
            } else {
                x = LINE_WIDTH_1;
            }

            canvas.drawLine(0, y, x, y, linePaint);
            i++;
        }
    }
}
