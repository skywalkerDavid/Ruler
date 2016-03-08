package com.skywalker.ruler.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.skywalker.ruler.HandleMoveEvent;
import com.squareup.otto.Bus;

public class HandleView extends View {
    private final float screenHeight;
    private View secondHandleView;
    private Bus bus;
    private float downPointY;
    private float startPointY;

    public HandleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        screenHeight = getResources().getDisplayMetrics().heightPixels;
    }

    public void setSecondHandleView(View secondHandleView) {
        this.secondHandleView = secondHandleView;
    }

    public void setMessageBus(Bus bus) {
        this.bus = bus;
        bus.register(this);
    }

    //TODO: Double fingers gesture
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_MOVE:
                // TODO: need to add restrictions here to limit handle to move beyond min and max
                setY((int) (startPointY + event.getY() - downPointY));
                startPointY = getY();

                int height = Math.round(Math.abs(getY() - secondHandleView.getY()));
                bus.post(new HandleMoveEvent(height));
                break;
            case MotionEvent.ACTION_DOWN:
                downPointY = event.getY();
                startPointY = getY();
                break;
            default:
                break;
        }
        return true;
    }
}
