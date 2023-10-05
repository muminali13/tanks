package com.muminali13.tanks.panel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.muminali13.tanks.R;

public class GameOver {

    private String text;
    private Paint paint;

//    private Context context;
    public GameOver(Context context) {
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.red));
        paint.setTextSize(64f);

        text = "Game Over";
    }

    public void draw(Canvas canvas) {

        canvas.drawText(text, 800, 200, paint);

    }
}
