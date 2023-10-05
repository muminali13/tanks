package com.muminali13.tanks.panel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.muminali13.tanks.GameLoop;
import com.muminali13.tanks.R;

public class DebugOverlay {

    private static final boolean showDebugScreen = true;

    private Paint paint;

    private GameLoop gameLoop;

    public DebugOverlay(Context context, GameLoop gameLoop) {
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.white));
        paint.setTextSize(14f);

        this.gameLoop = gameLoop;
    }

    public void draw(Canvas canvas) {
        if (!showDebugScreen) return;

        canvas.drawText("UPS: " + gameLoop.getAverageUPS(), 20, 25, paint);
        canvas.drawText("FPS: " + gameLoop.getAverageFPS(), 20, 80, paint);
    }

}
